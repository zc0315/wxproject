package com.jy.servlet;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class SignUtil {
	//申请时填入的token�?
	private static String token = "zc123";
	/**
	 * 1. 将token、timestamp、nonce三个参数进行字典序排�?
        2. 将三个参数字符串拼接成一个字符串进行sha1加密
        3. �?发�?�获得加密后的字符串可与signature对比，标识该请求来源于微�?
	 */
	 
	public static boolean checkSignature(String signature,String timestamp,String nonce){
		
		// 防范长密文攻�?
		if (signature == null || signature.length() > 128 
				|| timestamp == null || timestamp.length() > 128
				|| nonce == null || nonce.length() > 128) {
			return false;
		}
		ArrayList<String> tmp = new ArrayList<String>();
		tmp.add(token);
		tmp.add(timestamp);
		tmp.add(nonce);
		//将token,timestamp,nonce三个参数进行字典排序
		Collections.sort(tmp);
		StringBuilder content = new StringBuilder();
	    
		for(int i=0; i<tmp.size();  i++){
	    	  content.append(tmp.get(i));
	    }
	     
		System.out.println("content "+content);
		MessageDigest md = null;
       
		String tmpStr = null;
       
		try {
			md = MessageDigest.getInstance("SHA-1");
       
			//将三个参数字符串拼接成一个字符串进行sha1加密
			byte[] digest = md.digest(content.toString().getBytes());
       
			tmpStr = byteToStr(digest);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("组合�?"+tmpStr);
		content = null;
		// 将sha1加密后的字符串可与signature对比，标识该请求来源于微�? 
		return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
	}
	 
	 /*
	  * 将字节数组转换为十六进制字符�?
	  * */
	 private static String byteToStr(byte[] byteArray){
		 String strDigest = "";
		 for(int i=0; i<byteArray.length; i++){
			 strDigest +=byteToHexStr(byteArray[i]);
		 }
		 
		 return strDigest;
	 }
	 
	 /*
	  * 将字节转换为十六进制字符�?
	  * */
	 private static String byteToHexStr(byte bhs){
		 char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };  
	        
	     char[] tempArr = new char[2];
	        
	     tempArr[0] = Digit[(bhs >>>  4) & 0x0f];
	     tempArr[1] = Digit[bhs & 0x0f];
	        
	     String s = new String(tempArr);
	        
	     return s;
	 }
}
