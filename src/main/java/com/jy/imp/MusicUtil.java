package com.jy.imp;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.jy.bean.resp.Music;

public class MusicUtil {

	public static ArrayList<Music> musicService(String musicName,String musicAuthor){
		 // 百度音乐搜索地址  
       String requestUrl = "http://box.zhangmen.baidu.com/x?op=12&count=1&title={TITLE}$${AUTHOR}$$$$";  
		//String requestUrl = "http://music.soso.com/music.cgi?ty=getsongurls&w={TITLE}&pl={AUTHOR};";
		
		// 对音乐名称、作者进URL编码  
       requestUrl = requestUrl.replace("{TITLE}", urlEncodeUTF8(musicName)); 
       if(musicAuthor != null){
       	requestUrl = requestUrl.replace("{AUTHOR}", urlEncodeUTF8(musicAuthor));  
       }else{
       	requestUrl = requestUrl.replace("{AUTHOR}", "");  
       }
       // 处理名称、作者中间的空格  
       requestUrl = requestUrl.replaceAll("\\+", "%20");  
 
       // 查询并获取返回结果  
       InputStream inputStream = httpRequest(requestUrl);  
       // 从返回结果中解析出Music  
       ArrayList<Music> musicList = parseMusic(inputStream);  
 
       for(int i=0; i<musicList.size(); i++){
           // 如果music不为null，设置标题和描述  
       	musicList.get(i).setTitle(musicName);  
           // 如果作者不为""，将描述设置为作者  
           if (musicAuthor != null)  
           	musicList.get(i).setDescription(musicAuthor);  
           else  
           	musicList.get(i).setDescription("来自腾讯音乐");  
       }
       return musicList;  
	}
	
	/** 
    * UTF-8编码 
    *  
    * @param source 
    * @return 
    */  
   private static String urlEncodeUTF8(String source) {  
       String result = source;  
       try {  
           result = java.net.URLEncoder.encode(source, "UTF-8");  
       } catch (UnsupportedEncodingException e) {  
           e.printStackTrace();  
       }  
       return result;  
   }  
 
   /** 
    * 发送http请求取得返回的输入流 
    *  
    * @param requestUrl 请求地址 
    * @return InputStream 
    */  
   private static InputStream httpRequest(String requestUrl) {  
       InputStream inputStream = null;  
       try {  
           URL url = new URL(requestUrl);  
           HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();  
           httpUrlConn.setDoInput(true);  
           httpUrlConn.setRequestMethod("GET");  
           httpUrlConn.connect();  
           // 获得返回的输入流  
           inputStream = httpUrlConn.getInputStream();  
       } catch (Exception e) {  
           e.printStackTrace();  
       }  
       return inputStream;  
   }  
 

   /** 
    * 解析音乐参数 
    *  
    * @param inputStream 百度音乐搜索API返回的输入流 
    * @return Music 
    */  
   @SuppressWarnings("unchecked")  
   private static ArrayList<Music> parseMusic(InputStream inputStream) {  
       ArrayList<Music> musicList = new ArrayList<Music>();;  
       try {  
           // 使用dom4j解析xml字符串  
           SAXReader reader = new SAXReader();  
           Document document = reader.read(inputStream);  
           // 得到xml根元素  
           Element root = document.getRootElement();  
           // count表示搜索到的歌曲数  
           String count = root.element("count").getText();  
           System.out.println("count:"+count);
           // 当搜索到的歌曲数大于0时  
           if (!"0".equals(count)) {  
               // 普通品质  
               List<Element> urlList = root.elements("url");  
               List<String> urlStrList = new ArrayList<String>();
               // 高品质  
               List<Element> durlList = root.elements("durl");  
               List<String> durlStrList = new ArrayList<String>();
               for(int i=0; i<urlList.size(); i++){
                   // 普通品质的encode、decode  
                   String urlEncode = urlList.get(0).element("encode").getText();  
                   String urlDecode = urlList.get(0).element("decode").getText();  
                   // 普通品质音乐的URL  
                   String url = urlEncode.substring(0, urlEncode.lastIndexOf("/") + 1) + urlDecode;  
                   if (-1 != urlDecode.lastIndexOf("&"))  
                       url = urlEncode.substring(0, urlEncode.lastIndexOf("/") + 1) + urlDecode.substring(0, urlDecode.lastIndexOf("&"));  
                   urlStrList.add(url);
               }
               // 判断高品质节点是否存在  
               //高品质存在
               for(int i=0; i<durlList.size(); i++){
               	String durl = null;
               	Element durlElement = durlList.get(0).element("encode");  
               	if (null != durlElement) {  
               		// 高品质的encode、decode  
               		String durlEncode = durlList.get(0).element("encode").getText();  
               		String durlDecode = durlList.get(0).element("decode").getText();  
               		// 高品质音乐的URL  
               		durl = durlEncode.substring(0, durlEncode.lastIndexOf("/") + 1) + durlDecode;  
               		if (-1 != durlDecode.lastIndexOf("&"))  
               			durl = durlEncode.substring(0, durlEncode.lastIndexOf("/") + 1) + durlDecode.substring(0, durlDecode.lastIndexOf("&"));  
               		durlStrList.add(durl);
               	}  
               }	
               
               // 默认情况下，高音质音乐的URL 等于 普通品质音乐的URL  
               for(int i=0; i<urlStrList.size() || i<durlStrList.size() ; i++){
               	Music music = new Music();
               	// 设置普通品质音乐链接  
                   music.setMusicUrl(urlStrList.get(i));  
                   // 设置高品质音乐链接  
                   if(durlStrList.get(i).equals("")){
                   	music.setHQMusicUrl(urlStrList.get(i));
                   }else{
                   	music.setHQMusicUrl(durlStrList.get(i));
                   }
                   musicList.add(music);    
               }
           }  
       } catch (Exception e) {  
           e.printStackTrace();  
       }  
       return musicList;  
   }  
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 ArrayList<Music> music = MusicUtil.musicService("相信自己", "零点乐队");  
	        for(int i=0; i<music.size(); i++){
	        	System.out.println("音乐名称：" + music.get(i).getTitle());  
	            System.out.println("音乐描述：" + music.get(i).getDescription());  
	            System.out.println("普通品质链接：" + music.get(i).getMusicUrl());  
	            System.out.println("高品质链接：" + music.get(i).getHQMusicUrl());  
	        }       
	}

}
