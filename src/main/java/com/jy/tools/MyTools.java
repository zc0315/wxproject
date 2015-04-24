package com.jy.tools;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class MyTools {
	private String appId = "wx0d86f609ca6c5553";
	private String appsecret = "2292af8e37544bdbaed36b2f726f9bcb";
	
	/** 
	 * 计算采用utf-8编码方式时字符串所占字节数 
	 *  
	 * @param content 
	 * @return 
	 */  
	public static int getByteSize(String content) {  
	    int size = 0;  
	    if (null != content) {  
	        try {  
	            // 汉字采用utf-8编码时占3个字节  
	            size = content.getBytes("utf-8").length;  
	        } catch (UnsupportedEncodingException e) {  
	            e.printStackTrace();  
	        }  
	    }  
	    return size;  
	}  
	
	public static String urlUtf8Encode(String param){
		String encode = null;
		try {
			 encode = URLEncoder.encode(param,"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return encode;
	}
	
	public static String urlGb2312Encode(String param){
		String encode = null;
		try {
			 encode = URLEncoder.encode(param,"gb2312");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return encode;
	}
	
}
