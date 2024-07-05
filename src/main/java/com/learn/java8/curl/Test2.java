package com.learn.java8.curl;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Test2 {
    public static void main(String[] args) throws Exception {

        ProcessBuilder processBuilder = new ProcessBuilder("top");
        Process process = processBuilder.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.trim().split("\\s+");
            String processId = parts[0];
            String cpuUsage = parts[8];
            System.out.println("进程ID: " + processId + ", CPU占用率: " + cpuUsage);
        }
        reader.close();

        int exitCode = process.waitFor();
        System.out.println("Exited with error code: " + exitCode);
    }
}
