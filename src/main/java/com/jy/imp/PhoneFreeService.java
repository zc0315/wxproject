package com.jy.imp;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class PhoneFreeService {
	
	
	//检查电话号码
	public static String checkPhoneNum(String urlpath){
		BufferedReader br = null;
		String result = null;
		StringBuffer strbuffer = new StringBuffer();
	
		try {
			URL url = new URL(urlpath);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.connect();
			// 将返回的输入流转换成字符串  
			InputStream is = connection.getInputStream();
			br = new BufferedReader(new InputStreamReader(is,"utf-8"));
			String strtemp = "";
			while((strtemp = br.readLine()) != null){
				strbuffer.append(strtemp);
			}
			
			br.close();
			result = strbuffer.toString();
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
			
		return result;
	}
	
	public static void main(String[] args) {
		String url ="http://op.juhe.cn/ofpay/mobile/telquery?cardnum=30&phoneno=18395606290&key=605939dc82ceb62ff4fc4bb82159f347";
		String result = checkPhoneNum(url);
		System.out.println(result);
	}
	
}
