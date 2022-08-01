package com.turing.universe.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 * @author ChenOT
 * @Date 2022/7/30
 */
public class FileUtils {
    /**
     * 写文件，指定文件路径
     * 以字符流的方式
     *
     * @param path
     * @param content
     */
    public static void writeToTxt(String path, String content) {
        File file = new File(path);
        FileWriter fw = null;
        BufferedWriter writer = null;
        try {
            fw = new FileWriter(file, true);
            writer = new BufferedWriter(fw);
            writer.write(content);
            writer.newLine();
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
                fw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
