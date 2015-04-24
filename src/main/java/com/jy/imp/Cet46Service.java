package com.jy.imp;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.jy.tools.MyTools;


public class Cet46Service {
	public static String queryCet(String zkzh,String xm){
		String requestURL = "http://www.chsi.com.cn/cet/query?zkzh="+zkzh+"&xm="+MyTools.urlUtf8Encode(xm); 
		
		System.out.println(requestURL);
		
		String result = HttpRequest(requestURL);
		
		return result;
		
	}
	
	public static String HttpRequest(String requestURL){
		String host = "www.chsi.com.cn";
		StringBuffer buffer = new StringBuffer();
		try {
			URL url = new URL(requestURL);
			HttpURLConnection con =(HttpURLConnection)url.openConnection();
			con.setRequestProperty("Host", host); // 通过地址访问主机
			con.setRequestProperty("Referer", "http://www.chsi.com.cn/cet/");
			con.setDoInput(true);
			con.setRequestMethod("GET");
			con.connect();
			
			//将返回的输入流转换成字符串
			InputStream inputStream = con.getInputStream();
			InputStreamReader isr = new InputStreamReader(inputStream,"utf-8");
			BufferedReader br = new BufferedReader(isr);
			
			String str = null;
			
			while((str = br.readLine()) != null){
				buffer.append(str);
			}
			
			br.close();
			isr.close();
			inputStream.close();
			
			inputStream = null;
			con.disconnect();
		
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return buffer.toString();
	}
	
	public static void main(String []args){
	String str = queryCet("440860132108527", "刘鸿志");
	int startIndex = str.indexOf("学校：</th>		<td>");
	int length = "学校：</th>		<td>".length();
	int endIndex = str.indexOf("</td>", startIndex);	
	//cetscore.setSchool(str.substring(startIndex+length,endIndex));
	
	str = str.substring(endIndex,endIndex+500);
	
	System.out.println(str);
		
	}
	
}
