package com.learn.java8.word2Pdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.*;
import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Project: dddd-flaw-patch
 * File Created at 2022-11-01 10:22:10:22
 * {@link}
 *
 * @author <a href="mailto:dddd@mail.com">zhangsan</a>
 * @version 1.0.0
 * @Type TextPdfUtils2.java
 * @Description
 * @date 2022/11/1 10:22
 */
@Slf4j
public class TextPdfUtils {
    /**
     * 标题配置
     */
    private static final int TITLE_SIZE = 40;
    private static final float TITLE_X = 156;
    private static final float TITLE_Y = 537;
    private static final BaseFont TITLE_FONT = FontType.FONT_HWSC_VF;

    /**
     * 内容配置
     */
    private static final int INFO_SIZE = 18;
    private static final float INFO_X = 110;
    private static final float INFO_Y = 500;
    private static final BaseFont INFO_FONT = FontType.FONT_ALI;
    //private static final BaseFont INFO_FONT = FontType.FONT_SIYUAN_SONG ;

    /**
     * 落款配置
     */
    private static final int LOAN_SIZE = 18;
    private static final float LOAN_X = 200;
    private static final float LOAN_Y = 210;
    private static final BaseFont LOAN_FONT = FontType.FONT_ALI;

    public static void main(String[] args) {
        first();
        //waterMark();
    }

    private static void waterMark() {
        waterMark("C:\\Users\\test\\Desktop\\1682306720781.pdf","C:\\Users\\test\\Desktop\\1682306720781-waterMark.pdf","bbb-CN",20);
    }
    private static void first() {
        Map<String, Object> dataMap = new HashMap<>();
        Map<String, Object> infoMap = new LinkedHashMap<>();
        infoMap.put("证书编号", "bbb-CA-dddd-2022-000002");
        infoMap.put("漏洞编号", "bbb-dddd-2022157681");
        infoMap.put("漏洞类型", "通用型-权限许可和访问控制问题-中危");
        //infoMap.put("贡  献  者", "张三");
        infoMap.put("贡献单位", "测试单位");
        infoMap.put("收录时间", "2022年10月12日");
        dataMap.put("info", infoMap);
        Map<String, Object> loanMap = new LinkedHashMap<>();
        loanMap.put("loan2", "测试平台");
        //loanMap.put("loan-date", "2021-12-11");
        dataMap.put("load", loanMap);
        // userType: 1：企业用户，2：个人用户
        writeTemplate("D:\\test\\template-new.pdf", "D:\\test\\result.pdf", dataMap, 1);
    }

    /**
     * 写入PDF模板
     *
     * @param inputFilePath  源文件路径
     * @param outputFilePath 输出文件路径
     * @param dataMap        数据集：主要包含三大块：标题（title）、*信息（info）、落款（loan），其中标记为*的区块为
     *                       必填项，每个区块的key值对应区块的英文名.
     */
    @SneakyThrows
    public static void writeTemplate(String inputFilePath, String outputFilePath, Map<String, Object> dataMap, Integer userType) {
        //fixme: 行过长，没有做换行处理
        if (!checkDir(outputFilePath)) {
            throw new Exception("创建输出文件路径失败！");
        }
        @Cleanup
        PdfReader reader = new PdfReader(inputFilePath);  // 读取PDF模板
        @Cleanup
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(outputFilePath)); // 以PDF模板创建新的PDF
        PdfContentByte content = stamper.getOverContent(1); // 获取PDF页面
        float[] pageSize = getPageSize(reader);
        float width = pageSize[0];
        float height = pageSize[1];
        log.info("PDF -> 开始写入PDF -> 页面信息: 宽: {}, 高: {}", width, height);
        content.beginText();

        float x = TITLE_X;
        float y = TITLE_Y;

        // ********************* 标题 *********************
        //        content.setColorFill(CustomBaseColor.TITLE_COLOR);
        //        content.setFontAndSize(TITLE_FONT, TITLE_SIZE);
        //        log.debug("PDF -> x: {}, y: {}, key: {}, value: {}", x, y, "title", dataMap.get("title"));
        //        content.showTextAligned(Element.ALIGN_LEFT, dataMap.get("title").toString(), x, y, 0);

        // ********************* 内容 *********************
        x = INFO_X;
        y = INFO_Y;
        content.setColorFill(CustomBaseColor.NEW_COLOR);
        content.setFontAndSize(INFO_FONT, INFO_SIZE);
        Map<String, Object> infoMap;
        try {
            infoMap = (Map<String, Object>) dataMap.get("info");
        } catch (Exception e) {
            throw new Exception("数据格式有误，生成PDF失败！");
        }
        if (infoMap == null || infoMap.size() == 0) {
            throw new Exception("PDF主体内容不能为空");
        }
        Map<String, Integer> staticMap = new LinkedHashMap<>();
        int flawTypeLineCount = 1;
        int companyLineCount = 1;
        int flawFinderCount = 0;
        log.info("infoMap :" + infoMap);
        for (Map.Entry<String, Object> entry : infoMap.entrySet()) {
            // 自动换行支持
            String text = entry.getValue().toString();
            String textNew = text.replaceAll(" ", "");
            int strRegexType = Check.strRegexType(textNew);
            int split = 19;//裁剪长度，纯中文
            if ("漏洞类型".equals(entry.getKey())) {
                split = 14;
            } else if ("贡献单位".equals(entry.getKey())) {
                split = 14;
            } else if ("贡  献  者".equals(entry.getKey())) {
                split = 14;
            }
            //0-未知 1-纯中文 2-含有英文 3-含有数字 4-含有英文+数字
            if (2 == strRegexType) {
                split = 30;
            } else if (3 == strRegexType) {
                split = 14;
            } else if (4 == strRegexType) {
                split = 30;
            }
            int textLength = text.length();
            int line;
            if (textLength % split == 0) {
                line = (textLength / split);//>=0
            } else {
                line = (textLength / split) + 1;//>=1
            }
            // 主要针对漏洞类型和贡献单位
            if (entry.getKey().contains("漏洞类型")) {
                flawTypeLineCount = line;
            } else if (entry.getKey().contains("贡献单位")) {
                companyLineCount = line;
            } else if (entry.getKey().contains("贡  献  者")) {
                flawFinderCount = line;
            }
            for (int i = 0; i < line; i++) {
                //float vx = x;
                int begin = i * split;
                int end = (i + 1) * split;
                if (end > textLength) {
                    end = textLength;
                }
                String value = text.substring(begin, end);
                log.info("size: {}, line: {}, i: {}, begin: {}, end: {}, value: {}", textLength, line, i, begin, end, value);
                if (i == 0) {
                    staticMap.put(entry.getKey() + "：" + value, i);
                } else {
                    staticMap.put(entry.getKey() + value, i);
                }
            }
        }
        float vx = x;
        log.info("staticMap : " + staticMap);
        if (1 == userType) {
            // 企业用户
            log.info("flawTypeLineCount: " + flawTypeLineCount + " flawFinderCount: " + flawFinderCount + " companyLineCount: " + companyLineCount);
        } else {
            // 个人用户,只有漏洞类型可能多行,无贡献单位,贡献者是真实姓名或者登录用户名
            log.info("flawTypeLineCount: " + flawTypeLineCount);
        }
        x = getX(content, x, y, staticMap, flawTypeLineCount, companyLineCount, flawFinderCount, vx, userType);
        // ********************* 落款 *********************
        y = 150;
        content.setColorFill(CustomBaseColor.NEW_COLOR);
        content.setFontAndSize(LOAN_FONT, LOAN_SIZE);
        Map<String, Object> loadMap = (Map<String, Object>) dataMap.get("load");
        int index = 0;
        float xp = 0;
        for (Map.Entry<String, Object> entry : loadMap.entrySet()) {
            int textLength = LOAN_SIZE * entry.getValue().toString().length();
            if (index == 0) {
                xp = width - (textLength + 170);
            }
            if (index == 1) {
                xp = width - (textLength + 170);
            }
            if (index == 2) {
                xp = width - (textLength + 90);
            }
            content.showTextAligned(Element.ALIGN_LEFT, entry.getValue().toString(), 105, y = (float) (y - (LOAN_SIZE)), 0);
            log.info("PDF -> x: {}, y: {}, key: {}, value: {}", x, y, entry.getKey(), entry.getValue());
            index++;
        }

        content.endText();
        log.info("PDF -> 写入PDF完成，文件保存在：{}", outputFilePath);
    }

    private static float getX(PdfContentByte content, float x, float y, Map<String, Integer> staticMap, int flawTypeLineCount, int companyLineCount, int flawFinderCount, float vx, Integer userType) {
        // 根据userType区分个人用户和企业用户，个人用户无贡献单位数据
        if (1 == userType) {
            if (flawTypeLineCount == 1 && companyLineCount == 1) {
                // 漏洞类型和贡献单位都是一行
                for (String writeValue : staticMap.keySet()) {
                    int line = staticMap.get(writeValue);
                    if (line == 0) {
                        if (writeValue.contains("证书编号")) {
                            // 0是挨着白边
                            // 蓝边左右分别是60 - 530
                            y -= 30;
                            x = 120;
                        } else {
                            if (flawFinderCount == 0) {
                                y -= 68;
                            } else if (flawFinderCount == 1) {
                                y -= 56;
                            } else {
                                y -= 52;
                            }
                            x = 130;
                        }
                        content.showTextAligned(Element.ALIGN_LEFT, writeValue, x, y, 0);
                        log.info("PDF -> x: {}, y: {}, value: {}", x, y, writeValue);
                    } else if (line == 1 || line == 2) {
                        y -= 25;
                        vx += 110;
                        if (writeValue.contains("漏洞类型")) {
                            writeValue = writeValue.substring(4);
                        } else if (writeValue.contains("贡献单位")) {
                            writeValue = writeValue.substring(4);
                        } else if (writeValue.contains("贡  献  者")) {
                            writeValue = writeValue.substring(7);
                        }
                        content.showTextAligned(Element.ALIGN_LEFT, writeValue, vx, y, 0);
                        log.info("PDF -> x: {}, y: {}, value: {}", vx, y, writeValue);
                        vx = 110;
                    }
                }
            } else if (flawTypeLineCount == 1 && companyLineCount == 2) {
                // 漏洞类型一行,贡献单位两行
                for (String writeValue : staticMap.keySet()) {
                    int line = staticMap.get(writeValue);
                    // line对应要写到pdf文件行数,一行对应值0,1对应第二行
                    // 除了漏洞类型和贡献单位外,其他内容都只有一行,也就是line=0
                    if (line == 0) {
                        if (writeValue.contains("证书编号")) {
                            // 坐标x:0是挨着白边,蓝边左右分别是60 - 530
                            y -= 30;// 证书编号和头部图案的间距
                            x = 120;
                        } else {
                            if (flawFinderCount == 0) {
                                y -= 62;
                            } else if (flawFinderCount == 1) {
                                y -= 52;
                            } else {
                                y -= 48;
                            }
                            x = 130;
                        }
                        content.showTextAligned(Element.ALIGN_LEFT, writeValue, x, y, 0);
                        log.info("PDF -> x: {}, y: {}, value: {}", x, y, writeValue);
                    } else if (line == 1 || line == 2) {
                        y -= 25;
                        vx += 110;
                        if (writeValue.contains("漏洞类型")) {
                            writeValue = writeValue.substring(4);
                        } else if (writeValue.contains("贡献单位")) {
                            writeValue = writeValue.substring(4);
                        } else if (writeValue.contains("贡  献  者")) {
                            writeValue = writeValue.substring(7);
                        }
                        content.showTextAligned(Element.ALIGN_LEFT, writeValue, vx, y, 0);
                        log.info("PDF -> x: {}, y: {}, value: {}", vx, y, writeValue);
                        vx = 110;
                    }
                }
            } else if (flawTypeLineCount == 1 && companyLineCount == 3) {
                for (String writeValue : staticMap.keySet()) {
                    int line = staticMap.get(writeValue);
                    if (line == 0) {
                        if (writeValue.contains("证书编号")) {
                            // 0是挨着白边
                            // 蓝边左右分别是60 - 530
                            y -= 30;
                            x = 120;
                        } else {
                            if (flawFinderCount == 0) {
                                y -= 58;
                            } else if (flawFinderCount == 1) {
                                y -= 48;
                            } else {
                                y -= 44;
                            }
                            x = 130;
                        }
                        content.showTextAligned(Element.ALIGN_LEFT, writeValue, x, y, 0);
                        log.info("PDF -> x: {}, y: {}, value: {}", x, y, writeValue);
                    } else if (line == 1 || line == 2) {
                        y -= 25;
                        vx += 110;
                        if (writeValue.contains("漏洞类型")) {
                            writeValue = writeValue.substring(4);
                        } else if (writeValue.contains("贡献单位")) {
                            writeValue = writeValue.substring(4);
                        } else if (writeValue.contains("贡  献  者")) {
                            writeValue = writeValue.substring(7);
                        }
                        content.showTextAligned(Element.ALIGN_LEFT, writeValue, vx, y, 0);
                        log.info("PDF -> x: {}, y: {}, value: {}", vx, y, writeValue);
                        vx = 110;
                    }
                }
            } else if (flawTypeLineCount == 2 && companyLineCount == 1) {
                for (String writeValue : staticMap.keySet()) {
                    int line = staticMap.get(writeValue);
                    if (line == 0) {
                        if (writeValue.contains("证书编号")) {
                            // 0是挨着白边
                            // 蓝边左右分别是60 - 530
                            y -= 30;
                            x = 120;
                        } else {
                            if (flawFinderCount == 0) {
                                y -= 62;
                            } else if (flawFinderCount == 1) {
                                y -= 52;
                            } else {
                                y -= 48;
                            }
                            x = 130;
                        }
                        content.showTextAligned(Element.ALIGN_LEFT, writeValue, x, y, 0);
                        log.info("PDF -> x: {}, y: {}, value: {}", x, y, writeValue);
                    } else if (line == 1 || line == 2) {
                        y -= 25;
                        vx += 110;
                        if (writeValue.contains("漏洞类型")) {
                            writeValue = writeValue.substring(4);
                        } else if (writeValue.contains("贡献单位")) {
                            writeValue = writeValue.substring(4);
                        } else if (writeValue.contains("贡  献  者")) {
                            writeValue = writeValue.substring(7);
                        }
                        content.showTextAligned(Element.ALIGN_LEFT, writeValue, vx, y, 0);
                        log.info("PDF -> x: {}, y: {}, value: {}", vx, y, writeValue);
                        vx = 110;
                    }
                }
            } else if (flawTypeLineCount == 2 && companyLineCount == 2) {
                for (String writeValue : staticMap.keySet()) {
                    int line = staticMap.get(writeValue);
                    if (line == 0) {
                        if (writeValue.contains("证书编号")) {
                            // 0是挨着白边
                            // 蓝边左右分别是60 - 530
                            y -= 30;
                            x = 120;
                        } else {
                            if (flawFinderCount == 0) {
                                y -= 58;
                            } else if (flawFinderCount == 1) {
                                y -= 48;
                            } else {
                                y -= 44;
                            }
                            x = 130;
                        }
                        content.showTextAligned(Element.ALIGN_LEFT, writeValue, x, y, 0);
                        log.info("PDF -> x: {}, y: {}, value: {}", x, y, writeValue);
                    } else if (line == 1 || line == 2) {
                        y -= 25;
                        vx += 110;
                        if (writeValue.contains("漏洞类型")) {
                            writeValue = writeValue.substring(4);
                        } else if (writeValue.contains("贡献单位")) {
                            writeValue = writeValue.substring(4);
                        } else if (writeValue.contains("贡  献  者")) {
                            writeValue = writeValue.substring(7);
                        }
                        content.showTextAligned(Element.ALIGN_LEFT, writeValue, vx, y, 0);
                        log.info("PDF -> x: {}, y: {}, value: {}", vx, y, writeValue);
                        vx = 110;
                    }
                }
            } else if (flawTypeLineCount == 2 && companyLineCount == 3) {
                for (String writeValue : staticMap.keySet()) {
                    int line = staticMap.get(writeValue);
                    if (line == 0) {
                        if (writeValue.contains("证书编号")) {
                            // 0是挨着白边
                            // 蓝边左右分别是60 - 530
                            y -= 30;
                            x = 120;
                        } else {
                            if (flawFinderCount == 0) {
                                y -= 52;
                            } else if (flawFinderCount == 1) {
                                y -= 43;
                            } else {
                                y -= 39;
                            }
                            x = 130;
                        }
                        content.showTextAligned(Element.ALIGN_LEFT, writeValue, x, y, 0);
                        log.info("PDF -> x: {}, y: {}, value: {}", x, y, writeValue);
                    } else if (line == 1 || line == 2) {
                        y -= 26;
                        vx += 110;
                        if (writeValue.contains("漏洞类型")) {
                            writeValue = writeValue.substring(4);
                        } else if (writeValue.contains("贡献单位")) {
                            writeValue = writeValue.substring(4);
                        } else if (writeValue.contains("贡  献  者")) {
                            writeValue = writeValue.substring(7);
                        }
                        content.showTextAligned(Element.ALIGN_LEFT, writeValue, vx, y, 0);
                        log.info("PDF -> x: {}, y: {}, value: {}", vx, y, writeValue);
                        vx = 110;
                    }
                }
            }
            return x;
        } else {
            // 个人用户无贡献单位
            if (flawTypeLineCount == 1) {
                for (String writeValue : staticMap.keySet()) {
                    if (writeValue.contains("证书编号")) {
                        // 0是挨着白边
                        // 蓝边左右分别是60 - 530
                        y -= 30;
                        x = 120;
                    } else {
                        y -= 68;
                        x = 130;
                    }
                    content.showTextAligned(Element.ALIGN_LEFT, writeValue, x, y, 0);
                    log.info("PDF -> x: {}, y: {}, value: {}", x, y, writeValue);
                }
            } else if (flawTypeLineCount == 2) {
                for (String writeValue : staticMap.keySet()) {
                    int line = staticMap.get(writeValue);
                    if (line == 0) {
                        if (writeValue.contains("证书编号")) {
                            // 0是挨着白边
                            // 蓝边左右分别是60 - 530
                            y -= 30;
                            x = 120;
                        } else {
                            y -= 63;
                            x = 130;
                        }
                        content.showTextAligned(Element.ALIGN_LEFT, writeValue, x, y, 0);
                        log.info("PDF -> x: {}, y: {}, value: {}", x, y, writeValue);
                    } else if (line == 1 || line == 2) {
                        y -= 25;
                        vx += 110;
                        content.showTextAligned(Element.ALIGN_LEFT, writeValue, vx, y, 0);
                        log.info("PDF -> x: {}, y: {}, value: {}", vx, y, writeValue);
                        vx = 110;
                    }
                }
            }
            return x;
        }
    }

    /**
     * 检查文件目录是否存在，不存在则创建
     *
     * @param path 文件地址
     * @return boolean
     */
    private static boolean checkDir(String path) {
        File file = new File(path);
        String dir = file.getParent();
        File dirFile = new File(dir);
        if (dirFile.exists()) {
            return true;
        } else {
            return dirFile.mkdirs();
        }
    }

    /**
     * 检查文件是否存在
     *
     * @param path 文件地址
     * @return boolean
     */
    public static boolean checkFile(String path) {
        File file = new File(path);
        return file.exists();
    }

    /**
     * 打水印
     *
     * @param inputFilePath  输入路径
     * @param outputFilePath 输出路径
     * @param text           水印文本
     * @param fontSize       文本字体大小
     */
    @SneakyThrows
    public static void waterMark(String inputFilePath, String outputFilePath, String text, int fontSize) {
        @Cleanup
        PdfReader reader = new PdfReader(inputFilePath);
        @Cleanup
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(outputFilePath));
        float rotation = 25;

        for (int i = 1; i <= reader.getNumberOfPages(); i++) {
            PdfContentByte content = stamper.getOverContent(i);

            content.saveState();
            PdfGState gState = new PdfGState();
            gState.setFillOpacity(1.f);  // 设置透明度
            content.setGState(gState);
            content.restoreState();

            content.beginText();
            content.setFontAndSize(FontType.FONT_HWSC_VF, fontSize);
            content.setColorFill(BaseColor.GRAY);

            //第一行
            content.showTextAligned(Element.ALIGN_CENTER, text, 0, 700, rotation);
            content.showTextAligned(Element.ALIGN_CENTER, text, 300, 840, rotation);

            //第二行
            content.showTextAligned(Element.ALIGN_CENTER, text, 200, 560, rotation);
            content.showTextAligned(Element.ALIGN_CENTER, text, 500, 700, rotation);

            //第三行
            content.showTextAligned(Element.ALIGN_CENTER, text, 50, 280, rotation);
            content.showTextAligned(Element.ALIGN_CENTER, text, 350, 420, rotation);
            content.showTextAligned(Element.ALIGN_CENTER, text, 650, 560, rotation);

            //第四行
            content.showTextAligned(Element.ALIGN_CENTER, text, 210, 140, rotation);
            content.showTextAligned(Element.ALIGN_CENTER, text, 560, 300, rotation);
            content.showTextAligned(Element.ALIGN_CENTER, text, 810, 420, rotation);

            //第五行
            content.showTextAligned(Element.ALIGN_CENTER, text, 190, -20, rotation);
            content.showTextAligned(Element.ALIGN_CENTER, text, 590, 120, rotation);

            content.endText();
            content.setLineWidth(1f);
            content.stroke();
        }
        log.debug("PDF -> PDF打水印完成，文件保存在：{}", outputFilePath);
    }

    /**
     * 获取页面大小
     *
     * @param reader PDF文件流
     * @return 长宽数组 float[宽,高]
     */
    private static float[] getPageSize(PdfReader reader) {
        PdfDictionary pdfDictionary = reader.getPageN(1); // 获取PDF属性
        PdfArray pa = (PdfArray) pdfDictionary.get(new PdfName("MediaBox"));
        String[] paInfos = pa.toString().replaceAll("[\\[\\]]", "").split(",");
        float width = Float.parseFloat(paInfos[2]);
        float height = Float.parseFloat(paInfos[3]);
        return new float[]{width, height};
    }

    /**
     * 字体类
     *
     * @author XiaoCheng Wen
     * @date 2019/5/21 10:00
     */
    public static class FontType {
        /**
         * 黑体
         *//*
        public static final String SIM_HEI_PATH = "fonts/simhei.ttf";
        public static final BaseFont FONT_HEI = new FontType().getFont(SIM_HEI_PATH, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        *//**
         * 楷体
         *//*
        public static final String SIM_KAI_PATH = "fonts/simkai.ttf";
        public static final BaseFont FONT_KAI = new FontType().getFont(SIM_KAI_PATH, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        *//**
         * 华文宋体
         *//*
        public static final String STR_SUM_PATH = "fonts/simsun.ttf";
        public static final BaseFont FONT_SONG = new FontType().getFont(STR_SUM_PATH, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);*/
        /**
         * 宋体
         */
        public static final String STR_SONG_PATH = "STSong-Light";
        //public static final BaseFont FONT_SONG = new FontType().getFont(STR_SONG_PATH, "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);

        public static final String MICRO_YAHEI_BBOLD = "fonts/微软雅黑Bbold.ttf";
        public static final BaseFont FONT_MICRO_YAHEI_BBOLD = new FontType().getFont(MICRO_YAHEI_BBOLD, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

        public static final String HWSC_VF = "fonts/SourceHanSansHWSC-VF.ttf";
        public static final BaseFont FONT_HWSC_VF = new FontType().getFont(HWSC_VF, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

        public static final String HWTC_VF = "fonts/SourceHanSansHWTC-VF.ttf";
        public static final BaseFont FONT_HWTC_VF = new FontType().getFont(HWTC_VF, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

        //public static final String MSYH = "fonts/MSYH.TTC";
        //public static final BaseFont FONT_MSYH = new FontType().getFont(MSYH, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

        public static final BaseFont FONT_STSONGSTD = new FontType().getFont("STSongStd-Light,Bold", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);

        public static final String ALI = "fonts/Alibaba-PuHuiTi-Medium.ttf";
        public static final BaseFont FONT_ALI = new FontType().getFont(ALI, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

        public static final String SIYUAN_SONG = "fonts/思源宋体.ttf,Bold";
        public static final BaseFont FONT_SIYUAN_SONG = new FontType().getFont(SIYUAN_SONG, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);


        @SneakyThrows
        public BaseFont getFont(String name, String encoding, boolean embedded) {
            return BaseFont.createFont(name, encoding, embedded);
        }
    }

    /**
     * 颜色类
     *
     * @author XiaoCheng Wen
     * @date 2019/5/21 10:00
     */
    static class CustomBaseColor extends BaseColor {

        public static final BaseColor TITLE_COLOR = new BaseColor(130, 104, 47);
        public static final BaseColor INFO_COLOR = new BaseColor(33, 74, 136);
        public static final BaseColor LOAN_COLOR = new BaseColor(19, 42, 76);
        public static final BaseColor NEW_COLOR = new BaseColor(0, 60, 128);
        public static final BaseColor BLACK = new BaseColor(0, 0, 0);

        public CustomBaseColor(int red, int green, int blue) {
            super(red, green, blue);
        }

    }

}
