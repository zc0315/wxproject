package com.jy.imp;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
/**
 * 
 * 1、打开主机
 * 2、获取省份id
 * 3、获取城市id
 * 4、获取天气
 */

/*测试类*/
public class WeatherUtil {
	
    private static String SERVICES_HOST = "www.webxml.com.cn";// 主机
	
	private static String WEATHER_SERVICES_URL = "http://webservice.webxml.com.cn/WebServices/WeatherWS.asmx/";
	// 获得中国省份、直辖市、地区和与之对应的ID
	private static String PROVINCE_CODE_URL = WEATHER_SERVICES_URL+ "getRegionProvince";
	// 获得支持的城市/地区名称和与之对应的ID
	//输入参数：theRegionCode = 省市、国家ID或名称，返回数据：一维字符串数组。
	private static String CITY_CODE_URL = WEATHER_SERVICES_URL+ "getSupportCityString?theRegionCode=";
	// 获取天气
	private static String WEATHER_QUERY_URL = WEATHER_SERVICES_URL+ "getWeather?theUserID=&theCityCode=";

	/**
	 * 1、打开服务器主机
	 * @param url
	 * @return
	 */
	public static InputStream getSoapInputStream(String url){
		InputStream inputStream = null;
		
		try {
			URL urlObj = new URL(url);
			URLConnection urlConn = urlObj.openConnection(); // 打开连接
			urlConn.setRequestProperty("Host", SERVICES_HOST); // 通过地址访问主机
			urlConn.connect();
			inputStream = urlConn.getInputStream();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}
		return inputStream;
	}
	
	/**
	 * 获取省份编码
	 * @param provinceName  省份的汉字 
	 * @return 
	 */
	public static int getProvinceCode(String provinceName){
		
		Document document;
		int provinceCode = 0;  // 省份的编码
		
		// 得到 DOM 解析器的工厂实例
		DocumentBuilderFactory documentBF = DocumentBuilderFactory.newInstance();
		documentBF.setNamespaceAware(true);
		
		try {
			DocumentBuilder documentB = documentBF.newDocumentBuilder();// 从DOM工厂获得DOM解析器
			// 把要解析的xml 文档转化为输入流，以便dom解析器解析它
			InputStream inputSteam = getSoapInputStream(PROVINCE_CODE_URL);
			// 解析xml文档的输入流，得到一个document
			document = documentB.parse(inputSteam);
			
			NodeList nodeList = document.getElementsByTagName("string"); // 解析xml文件里的标签
			
			int len = nodeList.getLength();
			
			for(int i=0;i<len;i++){
				Node n = nodeList.item(i);
				String result = n.getFirstChild().getNodeValue();
				String[] address = result.split(",");
				String pName = address[0];
				String pCode = address[1];
				if(provinceName.equals(pName)){
					provinceCode = Integer.parseInt(pCode);
				}
			}
			inputSteam.close();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return provinceCode;// 省份的id
	}
	
	/**
	 * 获取市级 编码
	 * @param provinceCode  省份的编码
	 * @param cityName  城市中文名称
	 * @return  城市的编号
	 */
	public static int getCityCode(int provinceCode,String cityName){
		int cityCode = 0;
		
		Document doc;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputStream js = getSoapInputStream(CITY_CODE_URL+provinceCode);
			doc = db.parse(js);
			
			NodeList nl = doc.getElementsByTagName("string");
			int len = nl.getLength();
			for(int i=0;i<len;i++){
				Node n = nl.item(i);
				String result = n.getFirstChild().getNodeValue();
				String[] address = result.split(",");
				String cName =  address[0];
				String cCode = address[1];
				
				if(cName.equalsIgnoreCase(cityName)){
					cityCode  = Integer.parseInt(cCode);
				}
			}
			js.close();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return cityCode;
	}
	
	/**
	 * 获取天气
	 * @param cityCode
	 * @return
	 */
	public static List<String> getWeather(int cityCode){
		
		List<String> weatherList = new ArrayList<String>();
		
		Document document;
		DocumentBuilderFactory documentBF = DocumentBuilderFactory.newInstance();
		documentBF.setNamespaceAware(true);
		
		try {
			DocumentBuilder db = documentBF.newDocumentBuilder();
			
			InputStream inputStream = getSoapInputStream(WEATHER_QUERY_URL+cityCode);
			document = db.parse(inputStream);
			
			NodeList nl = document.getElementsByTagName("string");
			int len = nl.getLength();
			for(int i=0;i<len;i++){
				Node n = nl.item(i);
				String weather = n.getFirstChild().getNodeValue();
				weatherList.add(weather);
			}
			inputStream.close();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return weatherList;
	}
	
	public static void main(String[] args){
		/*// 获取省份id
		int provinceCode = getProvinceCode("河北");
		System.out.println("省份id:"+provinceCode);
		// 获取城市id
		int cityCode = getCityCode(provinceCode,"石家庄");
		System.out.println("城市id:"+cityCode);
		
		
		List<String> list = getWeather(cityCode);
		for(String s : list){
			System.out.println(s);
		}*/

		
		try {
			System.out.println(URLEncoder.encode("天气深圳", "utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
