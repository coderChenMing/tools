package com.learn.java8.curl;


import com.jcraft.jsch.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class LinuxStateForShell {

    public static final String CPU_MEM_SHELL = "top -b -n 1";
    public static final String FILES_SHELL = "df -hl";
    public static final String[] COMMANDS = {CPU_MEM_SHELL, FILES_SHELL};
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private static Session session;

    /**
     * 连接到指定的HOST
     *
     * @return isConnect
     * @throws JSchException JSchException
     */
    private static boolean connect(String user, String passwd, String host) {
        JSch jsch = new JSch();
        try {
            session = jsch.getSession(user, host, 22);
            session.setPassword(passwd);

            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);

            session.connect();
        } catch (JSchException e) {
            e.printStackTrace();
            System.out.println("connect error !");
            return false;
        }
        return true;
    }

    /**
     * 远程连接Linux 服务器 执行相关的命令
     *
     * @param commands 执行的脚本
     * @param user     远程连接的用户名
     * @param passwd   远程连接的密码
     * @param host     远程连接的主机IP
     * @return 最终命令返回信息
     */
    public static Map<String, String> runDistanceShell(String[] commands, String user, String passwd, String host) {
        if (!connect(user, passwd, host)) {
            return null;
        }
        Map<String, String> map = new HashMap<>();
        StringBuilder stringBuffer;

        BufferedReader reader = null;
        Channel channel = null;
        try {
            for (String command : commands) {
                stringBuffer = new StringBuilder();
                channel = session.openChannel("exec");
                ((ChannelExec) channel).setCommand(command);

                channel.setInputStream(null);
                ((ChannelExec) channel).setErrStream(System.err);

                channel.connect();
                InputStream in = channel.getInputStream();
                reader = new BufferedReader(new InputStreamReader(in));
                String buf;
                while ((buf = reader.readLine()) != null) {

                    //舍弃PID 进程信息
                    if (buf.contains("PID")) {
                        break;
                    }
                    stringBuffer.append(buf.trim()).append(LINE_SEPARATOR);
                }
                //每个命令存储自己返回数据-用于后续对返回数据进行处理
                map.put(command, stringBuffer.toString());
            }
        } catch (IOException | JSchException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (channel != null) {
                channel.disconnect();
            }
            session.disconnect();
        }
        return map;
    }


    /**
     * 直接在本地执行 shell
     *
     * @param commands 执行的脚本
     * @return 执行结果信息
     */
    public static Map<String, String> runLocalShell(String[] commands) {
        Runtime runtime = Runtime.getRuntime();

        Map<String, String> map = new HashMap<>();
        StringBuilder stringBuffer;

        BufferedReader reader;
        Process process;
        for (String command : commands) {
            stringBuffer = new StringBuilder();
            try {
                process = runtime.exec(command);
                InputStream inputStream = process.getInputStream();
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String buf;
                while ((buf = reader.readLine()) != null) {
                    //舍弃PID 进程信息
                    if (buf.contains("PID")) {
                        break;
                    }
                    stringBuffer.append(buf.trim()).append(LINE_SEPARATOR);
                }

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            //每个命令存储自己返回数据-用于后续对返回数据进行处理
            map.put(command, stringBuffer.toString());
        }
        return map;
    }

    /**
     * 处理 shell 返回的信息
     * <p>
     * 具体处理过程以服务器返回数据格式为准
     * 不同的Linux 版本返回信息格式不同
     *
     * @param result shell 返回的信息
     * @return 最终处理后的信息
     */
    private static Map<String,Map<String,Object>> disposeResultMessage(Map<String, String> result) {
        Map<String, Map<String, Object>> map = new LinkedHashMap<>();

        for (String command : COMMANDS) {
            String commandResult = result.get(command);
            if (null == commandResult) continue;

            if (command.equals(CPU_MEM_SHELL)) {
                String[] strings = commandResult.split(LINE_SEPARATOR);
                //将返回结果按换行符分割
                for (String line : strings) {
                    line = line.toUpperCase();//转大写处理

                    // %Cpu(s):  6.6 us,  2.6 sy,  0.0 ni, 89.6 id,  0.8 wa,  0.0 hi,  0.5 si,  0.0 st
                    if (line.contains("CPU")) {
                        Map<String, Object> cpuMap = new HashMap<>();
                        try {
                            String[] cpuMetrics = line.split(":")[1].split(",");
                            for (String cpuMetric : cpuMetrics) {
                                String[] metrics = cpuMetric.trim().split(" ");
                                cpuMap.put(metrics[1], metrics[0]);
                            }
                            map.put("cpu", cpuMap);
                        } catch (Exception e) {
                            throw new RuntimeException("解析top cpu返回值错误");
                        }
                        //处理内存 Mem:  66100704k total, 65323404k used,   777300k free,    89940k buffers
                        // MiB Mem :  15988.5 total,    341.2 free,  10336.0 used,   5311.3 buff/cache
                    } else if (line.contains("MIB MEM")){
                        Map<String, Object> memMap = new HashMap<>();
                        try {
                            String[] memMetrics = line.split(":")[1].split(",");
                            for (String memMetric : memMetrics) {
                                String[] metrics = memMetric.trim().split(" ");
                                memMap.put(metrics[1],new BigDecimal(metrics[0]).divide(BigDecimal.valueOf(1024),2,BigDecimal.ROUND_HALF_DOWN)+"G");
                            }
                            map.put("mem", memMap);
                        } catch (Exception e) {
                            throw new RuntimeException("解析top memory返回值错误");
                        }

                    }else if (line.contains("KIB MEM")){
                        Map<String, Object> memMap = new HashMap<>();
                        try {
                            String[] memMetrics = line.split(":")[1].split(",");
                            for (String memMetric : memMetrics) {
                                String[] metrics = memMetric.trim().split(" ");
                                memMap.put(metrics[1],new BigDecimal(metrics[0]).divide(BigDecimal.valueOf(1024),2,BigDecimal.ROUND_HALF_DOWN).divide(BigDecimal.valueOf(1024),2,BigDecimal.ROUND_HALF_DOWN)+"G");
                            }
                            map.put("mem", memMap);
                        } catch (Exception e) {
                            throw new RuntimeException("解析top memory返回值错误");
                        }
                    }
                }
            } else if (command.equals(FILES_SHELL)) {
                //处理系统磁盘状态
                try {
                    Map<String, Object> diskMap = disposeFilesSystem(commandResult);
                    map.put("disk", diskMap);
                } catch (Exception e) {
                }
            }
        }

        //return buffer.toString();
        return map;
    }

    //处理系统磁盘状态

    /**
     * Filesystem            Size  Used Avail Use% Mounted on
     * /dev/sda3             442G  327G   93G  78% /
     * tmpfs                  32G     0   32G   0% /dev/shm
     * /dev/sda1             788M   60M  689M   8% /boot
     * /dev/md0              1.9T  483G  1.4T  26% /ezsonar
     *
     * @param commandResult 处理系统磁盘状态shell执行结果
     * @return 处理后的结果
     */
    private static Map<String,Object> disposeFilesSystem(String commandResult) {
        String[] strings = commandResult.split(LINE_SEPARATOR);
        Map<String, Object> diskMap = new HashMap<>();
        // final String PATTERN_TEMPLATE = "([a-zA-Z0-9%_/]*)\\s";
        int size = 0;
        int used = 0;
        for (int i = 0; i < strings.length - 1; i++) {
            if (i == 0) continue;

            int temp = 0;
            for (String s : strings[i].split("\\b")) {
                if (temp == 0) {
                    temp++;
                    continue;
                }
                if (!s.trim().isEmpty()) {
                    if (temp == 1) {
                        size += disposeUnit(s);
                        temp++;
                    } else {
                        used += disposeUnit(s);
                        temp = 0;
                    }
                }
            }
        }
        diskMap.put("size", size+"G");
        diskMap.put("used", used+"G");
        diskMap.put("idle", (size-used)+"G");
        return diskMap;
    }

    /**
     * 处理单位转换
     * K/KB/M/T 最终转换为G 处理
     *
     * @param s 带单位的数据字符串
     * @return 以G 为单位处理后的数值
     */
    private static int disposeUnit(String s) {

        try {
            s = s.toUpperCase();
            String lastIndex = s.substring(s.length() - 1);
            String num = s.substring(0, s.length() - 1);
            int parseInt = Integer.parseInt(num);
            if (lastIndex.equals("G")) {
                return parseInt;
            } else if (lastIndex.equals("T")) {
                return parseInt * 1024;
            } else if (lastIndex.equals("M")) {
                return parseInt / 1024;
            } else if (lastIndex.equals("K") || lastIndex.equals("KB")) {
                return parseInt / (1024 * 1024);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
        return 0;
    }


    public static void main(String[] args) {
        Map<String, String> result = runDistanceShell(COMMANDS, "root", "123456", "10.4.123.33");
        System.out.println(disposeResultMessage(result));
    }

}

