package com.jy.imp;



import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.jy.sql.SQLBean;
import com.jy.tools.MyTools;

/**
 * 解析xml文档，包括本地文档和url
 * @author cyxl
 * @version 1.0 2012-05-24
 * @since 1.0
 *
 */
public class WeatherQuery {

	InputStream inStream;
	Element root;

		public WeatherQuery(URL url) {
			InputStream inStream = null;
			try {
				inStream = url.openStream();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			if (inStream != null) {
				this.inStream = inStream;
				DocumentBuilderFactory domfac = DocumentBuilderFactory
						.newInstance();
				try {
					DocumentBuilder domBuilder = domfac.newDocumentBuilder();
					Document doc = domBuilder.parse(inStream);
					root = doc.getDocumentElement();
					
					inStream.close();
				} catch (ParserConfigurationException e) {
					e.printStackTrace();
				} catch (SAXException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}

		/**
		 * 
		 * @param nodes
		 * @return 单个节点多个值以分号分隔
		 */
		public Map<String, String> getValue(String[] nodes) {
			if (inStream == null || root==null) {
				return null;
			}
			Map<String, String> map = new HashMap<String, String>();
			// 初始化每个节点的值为null
			for (int i = 0; i < nodes.length; i++) {
				map.put(nodes[i], null);
			}

			// 遍历第一节点
			NodeList topNodes = root.getChildNodes();
			if (topNodes != null) {
				for (int i = 0; i < topNodes.getLength(); i++) {
					Node book = topNodes.item(i);
					if (book.getNodeType() == Node.ELEMENT_NODE) {
						for (int j = 0; j < nodes.length; j++) {
							for (Node node = book.getFirstChild(); node != null; node = node
									.getNextSibling()) {
								if (node.getNodeType() == Node.ELEMENT_NODE) {
									if (node.getNodeName().equals(nodes[j])) {
										//String val=node.getFirstChild().getNodeValue();
										String val = node.getTextContent();
										System.out.println(nodes[j] + ":" + val);
										// 如果原来已经有值则以分号分隔
										String temp = map.get(nodes[j]);
										if (temp != null && !temp.equals("")) {
											temp = temp + ";" + val;
										} else {
											temp = val;
										}
										map.put(nodes[j], temp);
									}
								}
							}
						}
					}
				}
			}
			return map;
		}
		
	public static String weatherQuery(String cityName){
		String link = "http://php.weather.sina.com.cn/xml.php?city=";
		String endStr = "&password=DJOYnieT8234jlsK&day=0";
		URL url = null;
		String citycode = "";
		try {
			citycode = URLEncoder.encode(cityName, "gb2312");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		link +=citycode+endStr;
		try {
			url = new URL(link);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WeatherQuery parser = new WeatherQuery(url);
		String[] nodes = {"status1","temperature1","temperature2",""};
		Map<String, String> map = parser.getValue(nodes);
		
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(cityName+"天气："+map.get(nodes[0])).append("\n");
		buffer.append("最高温度："+map.get(nodes[1])+"℃").append("\n");
		buffer.append("最低气温："+map.get(nodes[2])+"℃").append("\n");

		return buffer.toString();
	}
		
		
		
		
	public static void main(String[] args) {
		String link = "http://php.weather.sina.com.cn/xml.php?city=";
		String endStr = "&password=DJOYnieT8234jlsK&day=0";
		String cityName = null;
		try {
			cityName = URLEncoder.encode("上海", "gb2312");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		URL url;
		link+=cityName + endStr;
		try {
			url = new URL(link);
			System.out.println(url);
			// InputStream inStream= url.openStream();
			// InputStream inStream=new FileInputStream(new File("test.xml"));
			WeatherQuery parser = new WeatherQuery(url);
			String[] nodes = {"status1","temperature1","temperature2"};
			Map<String, String> map = parser.getValue(nodes);
			System.out.println(map.get(nodes[0]));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
}
