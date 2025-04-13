package com.amore.springboot.explore.utils;

import com.alibaba.fastjson.JSONArray;
import com.amore.springboot.explore.bean.App;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    public static List<String> listFiles(String path) {
        List<String> list = new ArrayList<>();
        File directory = new File(path);
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                String fileName = file.getName();
                list.add(fileName);
            }
        }
        return list;
    }


    public static String readDataFromResource(String path) {

        try (InputStream input = FileUtils.class.getClassLoader().getResourceAsStream(path)) {
            if (input == null) {
                System.out.println("Sorry, unable to find example.properties");
                return "";
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(input, Charset.forName("utf-8")));
            StringBuffer sb = new StringBuffer();
            String data = "";
            while ((data = reader.readLine()) != null){
                sb.append(data);
            }
            return sb.toString();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }
    public static void main(String[] args) {
        String s = readDataFromResource("app.json");
        List<App> apps = JSONArray.parseArray(s, App.class);
        apps.forEach(app->{
            System.out.println(app.getAppActivity());
            System.out.println(app.getAppPackageName());
            System.out.println(app.getAppName());
        });
    }
}
