package com.amore.springboot.explore.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Processor {
    public static String runSync(String command){
        StringBuffer result = new StringBuffer();
        try {

            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line).append("\n");
            }

            // 等待命令执行完成
            int exitCode = process.waitFor();
            System.out.println("Exited with error code : " + exitCode);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    public static void run(String command){
        try {
            new Thread(() -> {
                Process process = null;
                try {
                    process = Runtime.getRuntime().exec(command);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                int exitCode = 0;
                try {
                    exitCode = process.waitFor();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Exited with error code: " + exitCode);

            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
