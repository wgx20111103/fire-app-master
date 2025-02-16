package io.renren.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class test10 {
    public static void main(String[] args) throws IOException {
        String result = null;
        for (int i = 0; i < 500000; i++) {
            try {
                Thread.sleep(100); //设置暂停的时间 5 秒
                URL url = new URL("https://blog.51cto.com/u_14304894/4640371");
                System.out.println(url);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("POST");
                conn.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/536.6 (KHTML, like Gecko) Chrome/20.0.1096.1 Safari/536.6");
                conn.setRequestProperty("content-type","application/x-www-form-urlencoded;charset=UTF-8");
                conn.setDoOutput(true);
                if (conn.getResponseCode() == 302) {
                    System.out.println(302);
                }
                if (conn.getResponseCode() == 200) {
                    System.out.println(200);
                }
                System.out.println();

                BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
                StringBuffer sb = new StringBuffer();
                String s = "";
                while ((s = rd.readLine()) != null) {
                    sb.append(s);
                }
                if (sb.length() == 0) {
                    sb.append("[]");
                }
                result = sb.toString();
                System.out.println(result);
                rd.close();
                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }
}