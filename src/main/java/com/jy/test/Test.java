package com.jy.test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 解析 json 数据
 * */

public class Test {
	public static void main(String[] args) {
		
	}
	
	public static void test(){
		
		/*String json = "{\"name\":\"reiz\"}";
		JSONObject jsonObj = JSONObject.fromObject(json);
		String name = jsonObj.getString("name");

		jsonObj.put("initial", name.substring(0, 1).toUpperCase());

		String[] likes = new String[] { "JavaScript", "Skiing", "Apple Pie" };
		jsonObj.put("likes", likes);

		形成json数据
		Map<String, String> ingredients = new HashMap<String, String>();
		ingredients.put("apples", "3kg");
		ingredients.put("sugar", "1kg");
		ingredients.put("pastry", "2.4kg");
		ingredients.put("bestEaten", "outdoors");
		jsonObj.put("ingredients",ingredients);

		System.out.println(jsonObj);*/
		String urllink = "http://api.map.baidu.com/telematics/v3/weather?location=%E5%8C%97%E4%BA%AC&output=json&ak=5slgyqGDENN7Sy7pw29IUvrZ";
		try {
			// 获取HttpURLConnection连接对象
            URL url = new URL(urllink);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            // 设置连接属性
            httpConn.setConnectTimeout(3000);
            httpConn.setDoInput(true);
            httpConn.setRequestMethod("GET");
 
            // 获取相应码
            int respCode = httpConn.getResponseCode();
            
            String json = null;
            
            InputStream in = httpConn.getInputStream();
            
            if (respCode == 200)
            {
                json =  ConvertStream2Json(httpConn.getInputStream());
            }
			
            System.out.println(json);
            
			JSONObject jsonObj = JSONObject.fromObject(json);
			
			String resultState = (String)jsonObj.get("status");
			
			System.out.println("status:"+resultState);
			
			JSONArray result = (JSONArray)jsonObj.get("results");
			
			for(int i =0; i<result.size(); i++){
				JSONObject content = result.getJSONObject(i);
				
				System.out.println(content.get("currentCity"));
				
				JSONArray index = (JSONArray)content.get("index");
				
				if(index != null){
					for(int j= 0; j<index.size(); j++){
						JSONObject ind = index.getJSONObject(j);
						
						System.out.println(ind.get("title"));
					}
				}
			}
	
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String ConvertStream2Json(InputStream in){
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		byte[] buffer = new byte[1024];
		int len =0;
		try {
			while((len = in.read(buffer, 0, buffer.length))!= -1){
				out.write(buffer, 0, len);
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		String jsonString = null;
		try {
			jsonString = new String(out.toByteArray(),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		}
		return jsonString;
	}
	
	
}
