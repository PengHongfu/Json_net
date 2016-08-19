package com.peng.json_net;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("开始");
        new Thread() {
            @Override
            public void run() {
                try {
                    //注意填写自己的路径
                    String path = "http://192.168.1.44:8080/get_data3.json";
                    URL url = new URL(path);
                    System.out.println(path);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(5000);
                    if (conn.getResponseCode() == 200) {
                        System.out.println("连接成功");
                        InputStream in = conn.getInputStream();
                        InputStreamReader isr = new InputStreamReader(in);
                        //InputStreamReader isr = new InputStreamReader(getAssets().open("get_data.json"),"UTF-8");
                        BufferedReader br = new BufferedReader(isr);
                        String line;
                        //StringBuilder 缓存区 StringBuffer
                        StringBuilder builder = new StringBuilder();
                        while ((line = br.readLine()) != null) {
                            builder.append(line);
                        }
                        br.close();
                        isr.close();
                        JSONObject root = new JSONObject(builder.toString());
                        System.out.println("name= " + root.getString("name") +
                                " age= " + root.getInt("age") +
                                " address= " + root.getString("address"));
                        JSONArray array = root.getJSONArray("languages");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject lan = array.getJSONObject(i);
                            System.out.println("-----------------");
                            System.out.println("id= " + lan.getInt("id"));
                            System.out.println("name= " + lan.getString("name"));
                            System.out.println("ide= " + lan.getString("ide"));
                        }
                    } else {
                        System.out.println("连接失败");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();


    }

}
