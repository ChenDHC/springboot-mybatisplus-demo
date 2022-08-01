package com.turing.universe.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * @author ChenOT
 * @Date 2022/7/30
 */
public class FileUtils {
    private static final String ASK_CHAT_URL = "http://47.94.53.111/ask_chat?message=%s";

    /**
     * httpget
     *
     * @param url
     * @return
     */
    public static String httpGet(String url) {
        try {
            URL getUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) getUrl
                    .openConnection();

            connection.setRequestProperty("Content-type", "application/json; charset=utf-8");
            connection.setConnectTimeout(100000);
            connection.setReadTimeout(300000);
            connection.connect();

            // 取得输入流，并使用Reader读取
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(), "utf-8"));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            // 断开连接
            connection.disconnect();
            return sb.toString();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    public static boolean isAsk(String content) {
        try {
            String re1 = FileUtils.httpGet(String.format(ASK_CHAT_URL, URLEncoder.encode(content, "utf-8")));
            return re1.equals("1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        }
    }

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

    public static String replaceStartEndBiaodian(String content) {
        content = content.replaceAll("[\\pP\\p{Punct}]+$", "");
        content = content.replaceAll("^[\\pP\\p{Punct}]+", "");
        return content;
    }
}
