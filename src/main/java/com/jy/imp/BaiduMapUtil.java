package com.jy.imp;


import it.sauronsoftware.base64.Base64;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.jy.bean.common.BaiduPlace;
import com.jy.bean.common.UserLocation;



public class BaiduMapUtil {
	
	public static String HttpRequest(String requestURL){
		StringBuffer buffer = new StringBuffer();
		try {
			URL url = new URL(requestURL);
			HttpURLConnection con =(HttpURLConnection)url.openConnection();
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
	/*
	 * 根据地图返回的XML中解析出地址信息
	 */
	
	@SuppressWarnings({ "unchecked" })
	public static List<BaiduPlace> parsePlaceXml(String xml){
		List<BaiduPlace> bdPlaceList = null;
		System.out.println(xml);
		
		//解析XML文件的第二种方法
		try {
			Document document = DocumentHelper.parseText(xml);
			
			//得到XML的根元素
			Element root = document.getRootElement();
			
			//从根元素获得<results>
			Element rootElement = root.element("results");
			
			List<Element> resultList = rootElement.elements();
			
			if(resultList.size() > 0){
				bdPlaceList = new ArrayList<BaiduPlace>();
				for(Element e:resultList){
					List<Element> resultElement = e.elements();
					Map<String, Element> map =new HashMap<String, Element>();;
					for(Element ep:resultElement){	
						map.put(ep.getName(), ep);
					}
					
				
					BaiduPlace bdPlace = new BaiduPlace();
					
					bdPlace.setAddress(map.get("address").getText());
					bdPlace.setName(map.get("name").getText());
					bdPlace.setLng(map.get("location").element("lng").getText());
					bdPlace.setLat(map.get("location").element("lat").getText());
					
					//当telephone元素存在时获取电话号码
					
					if(null != map.get("telephone")){
						bdPlace.setTelephone(map.get("telephone").getText());
					}
					
					if(null != map.get("detail_info")){
						Element ed =  map.get("detail_info").element("distance");
						if(null != ed){
							bdPlace.setDistance(Integer.parseInt(ed.getText()));
						}
						bdPlaceList.add(bdPlace);
					}
				}
				//按距离由近及远排序
				Collections.sort(bdPlaceList);
			}
			
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bdPlaceList;
	}
	
	/**
	 * 
	 * 将微信定位的坐标转换成百度坐标（GCJ-02  -> Baidu）
	 * 
	 * */ 
	public static UserLocation convertCoord(String lng,String lat){
		//百度坐标转换接口
		String convertUrl = "http://api.map.baidu.com/ag/coord/convert?from=2&to=4&x={x}&y={y}";
		convertUrl = convertUrl.replace("{x}", lng);
		convertUrl = convertUrl.replace("{y}", lat);
		
		UserLocation userLocation = new UserLocation();
		
		String jsonCoord = HttpRequest(convertUrl);
		
		JSONObject jsonObject = JSONObject.fromObject(jsonCoord);
		
		//对转换后的坐标进行Base64解码
		
		userLocation.setBd09_lng(Base64.decode(jsonObject.getString("x"),"utf-8"));
		userLocation.setBd09_lat(Base64.decode(jsonObject.getString("y"),"utf-8"));
		
		return userLocation;	
	}
	
}
