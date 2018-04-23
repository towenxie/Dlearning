package org.towen.deeplearning.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    public static List<String> getMessage(String filePath) {
        List<String> contents = new ArrayList<String>();
        try {
            File file = new File(filePath);
            if (file.isFile() && file.exists()) {
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    contents.add(lineTxt);
                }
                read.close();
            } else {
                System.out.println("cannot found!");
            }
        } catch (Exception e) {
            System.out.println("read error!");
            e.printStackTrace();
        }

        return contents;
    }

    public static List<String> readDataByLineNumber(File sourceFile, int beginNumber, int endNumber) throws IOException {
        List<String> ipContents = new ArrayList<String>();
        FileReader in = new FileReader(sourceFile);
        LineNumberReader reader = new LineNumberReader(in);
        String lineTxt = "";
        int lines = 0;
        while (lineTxt != null) {
            lines++;
            lineTxt = reader.readLine();
            if (lines >= beginNumber && endNumber >= lines) {
                ipContents.add(lineTxt);
            } else if (endNumber < lines) {
                break;
            }
        }
        reader.close();
        in.close();
        return ipContents;
    }

    public static int getTotalLines(File file) throws IOException {
        InputStreamReader read = new InputStreamReader(new FileInputStream(file), "UTF-8");
        LineNumberReader reader = new LineNumberReader(read);
        String s = reader.readLine();
        int lines = 0;
        while (s != null) {
            lines++;
            s = reader.readLine();
        }
        reader.close();
        read.close();
        return lines;
    }

    public static String[] analysisLine(String lineStr) {
        List<String> dataArray = new ArrayList<String>();
        String[] models = lineStr.split(" ");
        for (String model : models) {
            if (!model.isEmpty()) {
                dataArray.add(model);
            }
        }
        return dataArray.toArray(new String[dataArray.size()]);
    }
}
