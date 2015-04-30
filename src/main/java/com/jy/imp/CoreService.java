package com.jy.imp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.DocumentException;

import com.jy.bean.common.BaiduPlace;
import com.jy.bean.common.UserLocation;
import com.jy.bean.resp.Article;
import com.jy.bean.resp.ArticleMessage;
import com.jy.bean.resp.Music;
import com.jy.bean.resp.MusicMessage;
import  com.jy.bean.resp.TextMessage;
import com.jy.tools.MessageUtil;


public class CoreService {
	private static String serviceNO = "";
	
	public static String  processRequest(HttpServletRequest request){
		
		Map<String, String> map = new HashMap<String, String>();
		//默认返回的文本消息内�?
		String respContent = "请求处理异常，请稍�?�尝试！";
		String reqContent= null;
		try {
			//接受的XML解析成map
			map = MessageUtil.parseXML(request);
			
			//发�?�方账号（open_id�?
			String fromUserName = map.get("FromUserName");
			//公众账号
			String toUserName = map.get("ToUserName");
			//消息类型
			String msgType = map.get("MsgType");
			
			System.out.println(msgType);
			if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)){
					reqContent = map.get("Content");
					System.out.println("reqContent:"+reqContent);
					if(reqContent.equals("101")){
						respContent = "请按下面格式输入查询：\n------------------------------\n格式：天气@地区\n例如：天气@石家庄\n\n------------------------------\n回复“@”显示帮助菜单";
						respContent = torespTextMessage(fromUserName,toUserName,respContent);
					}else if(reqContent.equals("102")){
						respContent = getMainMenu();
						respContent = torespTextMessage(fromUserName,toUserName,respContent);
					}else if(reqContent.equals("103")){
						
						UserLocation userLastLocation = LBSService.getLastUserLocation(fromUserName);
						
						if(userLastLocation == null || userLastLocation.getOpenId() == null){
							respContent = "请按下面步骤发送您的地理位置：\n------------------------------\n"+
							"发送地理位置\n"+
							"点击窗口底部的“+”按钮，选择“位置”，点“发送”"+
							"\n\n------------------------------\n回复“@”显示帮助菜单";
						}else {
							respContent = "您上一次是在"+userLastLocation.getLabel()+"使用，如果您的地理位置变化，则需要发送位置信息。\n"+
							"点击窗口底部的“+”按钮，选择“位置”，点“发送”\n"+"地理位置没有变化：您可以输入搜索关键字获取周边信息了，例如：\n"+
							"    附近@ATM\n"+"附近@厕所\n"+
							"\n\n------------------------------\n回复“@”显示帮助菜单";
						}
						respContent = torespTextMessage(fromUserName,toUserName,respContent);
					}else if(reqContent.equals("104")){
						serviceNO = "104";
						respContent = "请按下面格式输入点歌：\n------------------------------\n格式1：点歌@歌名\n格式2：点歌@歌名,歌手姓名\n例如1：点歌@存在\n例如2：点歌@存在,汪峰\n\n------------------------------\n回复@显示帮助菜单";
						respContent = torespTextMessage(fromUserName,toUserName,respContent);
					}else if(reqContent.equals("105")){
						respContent = getUsage();
						respContent = torespTextMessage(fromUserName,toUserName,respContent);
					}else if(reqContent.equals("106")){
						Article art = new Article();
						art.setTitle("石家庄经济学院街景");
						art.setDescription("查看方法\n①搜索并关注公众号“周冲小号”\n②回复“110”");
						art.setPicUrl("http://zcwx.oschina.mopaas.com/images/xuexiaologo.jpg");
						art.setUrl("http://zcwx.oschina.mopaas.com/TXMap.jsp");
						List<Article> content = new ArrayList<Article>();
						content.add(art);
						respContent = torespArticleMessage(fromUserName, toUserName, content);
					}else if(reqContent.equals("107")){
						Article art = new Article();
						art.setTitle("全国大学英语四六级考试成绩查询");
						art.setDescription("查询方法\n①搜索并关注公众号“周冲小号”\n②回复“107”");
						art.setPicUrl("http://zcwx.oschina.mopaas.com/images/logo.jpg");
						art.setUrl("http://zcwx.oschina.mopaas.com/cet46.html");
						List<Article> content = new ArrayList<Article>();
						content.add(art);
						respContent = torespArticleMessage(fromUserName, toUserName, content);
					}else if(reqContent.equals("108")){
						Article art = new Article();
						art.setTitle("2048小游戏");
						art.setDescription("小游戏\n①搜索并关注公众号“周冲小号”\n②回复“108”");
						art.setPicUrl("http://zcwx.oschina.mopaas.com/images/2048.jpg");
						art.setUrl("http://zcwx.oschina.mopaas.com/2048.html");
						List<Article> content = new ArrayList<Article>();
						content.add(art);
						respContent = torespArticleMessage(fromUserName, toUserName, content);
						
						
						
						/*art.setDescription("充值方法\n①搜索并关注公众号“周冲小号”\n②回复“108”");
						art.setPicUrl("http://zcwx.oschina.mopaas.com/images/hfcz.jpg");
						art.setUrl("http://zcwx.oschina.mopaas.com/phonefreecharge.html");
						List<Article> content = new ArrayList<Article>();
						content.add(art);
						respContent = torespArticleMessage(fromUserName, toUserName, content);*/
					}else if(reqContent.equals("@")){
						respContent = getMainMenu();
						respContent = torespTextMessage(fromUserName,toUserName,respContent);
					}else{                                         //非功能编号处理
						
						if(serviceNO.equals("104")){
							System.out.println("是104");
						}
						
						//判断是否是查询天气
						if(reqContent.startsWith("天气@")){
							String cityName = reqContent.replace("天气@", "").trim();
							if(!cityName.equals("")){
								System.out.println(cityName);
								respContent = WeatherQuery.weatherQuery(cityName);
								System.out.println(respContent);
								respContent = torespTextMessage(fromUserName,toUserName,respContent);
							}
						}else if(reqContent.startsWith("点歌@")){  //判断是否是点歌功能
							String s[] = reqContent.split("@");
							System.out.println("格式解析："+s[0]+"内容"+s[1]);
							if(s[1] != null ){
								s[1].trim();
								String musicName = null;
								String musicAuthor = null;
								
								//点歌内容解析
								if(s[1].contains(",")){
									String[] musicStr = s[1].split(",");
									musicName = musicStr[0];
									musicAuthor = musicStr[1];
								}else if(s[1].contains("，")){
									String[] musicStr = s[1].split("，");
									musicName = musicStr[0];
									musicAuthor = musicStr[1];
								}else{ 
									musicName = s[1];
								}
								System.out.println("歌名："+musicName);
								System.out.println("歌手："+musicAuthor);
							
								
								Music content = null;
								
								content = MusicService.musicService(musicName,musicAuthor);
		

								if(content != null){
									
									respContent += torespMusicMessage(fromUserName, toUserName, content);
									
								}else{
									respContent ="对不起，您找的歌曲没有查到！";
									respContent = torespTextMessage(fromUserName,toUserName,respContent);
								}	
							}else {
								//没有点歌内容
								respContent ="对不起，您没有输入歌名！";
								respContent = torespTextMessage(fromUserName,toUserName,respContent);
							}
						}else if(reqContent.startsWith("附近@")){          //判断是不是附近搜索服务
							String keyWord = reqContent.replace("附近@", "").trim();
							System.out.println("touser"+toUserName);
							System.out.println("fromuser"+fromUserName);
							UserLocation userLastLocation = LBSService.getLastUserLocation(fromUserName);
							System.out.println(keyWord);
							if(null != userLastLocation){
								System.out.println(userLastLocation.getBd09_lat());
								List<BaiduPlace> placeList = LBSService.searchPlace(keyWord, userLastLocation.getBd09_lng(), userLastLocation.getBd09_lat());
								
								if(null == placeList || placeList.size() == 0){
									respContent = String.format("/难过，您发送的位置附近未搜索到%s信息！", keyWord);
									respContent = torespTextMessage(fromUserName, toUserName, respContent);
								}else{
									List<Article> articleList = LBSService.makeArticleList(placeList, userLastLocation.getBd09_lng(), userLastLocation.getBd09_lat());
									
									//回复图文消息
									respContent = torespArticleMessage(fromUserName, toUserName, articleList);
								}
								
							}else{
								//没有用户的地理位置
							}
							
							
						}else{
							respContent="不知道你在说什么，请回复“@”显示帮助菜单";
							respContent = torespTextMessage(fromUserName,toUserName,respContent);
						}
					}
				
			}else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)){
					respContent = "这是图片消息";
					// 回复文本消息  
		            TextMessage textMessage = new TextMessage();  
		            textMessage.setToUserName(fromUserName);  
		            textMessage.setFromUserName(toUserName);  
		            textMessage.setCreateTime(new Date().getTime());  
		            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT); 
					// 取得图片地址  
	                String picUrl = map.get("PicUrl");  
	                // 人脸检测  
	                String detectResult = FaceService.detect(picUrl);  
	                textMessage.setContent(detectResult);  
	                respContent = MessageUtil.textMessageToXml(textMessage); 
					
					
			}else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)){
					respContent = "这是音频消息";
					respContent = torespTextMessage(fromUserName,toUserName,respContent);
			}else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)){                      //地理位置信息
					respContent = "";
					String local_x = map.get("Location_X");
					String local_y = map.get("Location_Y");
					String local_info = map.get("Label");
					
					
					UserLocation userLocation = BaiduMapUtil.convertCoord(local_y,local_x);
					if(userLocation != null){
						userLocation.setLng(local_y);
						userLocation.setLat(local_x);
						userLocation.setOpenId(fromUserName);
						userLocation.setLabel(local_info);
						if(LBSService.saveUserPlace(userLocation)){
							StringBuffer buffer = new StringBuffer();
							buffer.append("[愉快]").append("成功接收您的位置！").append("\n\n");
							buffer.append("您可以输入搜索关键字获取周边信息了，例如：").append("\n");
							buffer.append("    附近@ATM").append("\n");
							buffer.append("    附近@厕所").append("\n");
							buffer.append("\n------------------------------\n回复“@”显示帮助菜单");
							respContent = buffer.toString();
						}else{
							//位置保存失败
							System.out.println("位置保存失败");
						}
					}else{
						//
						System.out.println("位置转换失败");
					}
					
					respContent = torespTextMessage(fromUserName,toUserName,respContent);
			}else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)){
				respContent = "这是链接消息";
				respContent = torespTextMessage(fromUserName,toUserName,respContent);
			}else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)){

				String eventType = map.get("Event");
				System.out.println(eventType);
				if(eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)){
					//订阅事件
					String qhType = map.get("EventKey");
					System.out.println(qhType);
					
					if(!qhType.equals("")){
						//扫描二维码关�?
						respContent = "欢迎通过扫描二维码关注我的微信公共账号！您的关注，是我继续的动力，点个赞！/:,@f" +"\n\n--------------------------------\n"+"请回复“@”显示帮助菜单。";
					}else{
						//点击关注按钮关注
						respContent = "欢迎关注我的微信公共账号！您的关注，是我继续的动力，点个赞！/:,@f"+"\n\n--------------------------------\n"+"请回复“@”显示帮助菜单";
					}
					respContent = torespTextMessage(fromUserName,toUserName,respContent);
				}else if(eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)){
					//取消订阅
				}else if(eventType.equals(MessageUtil.EVENT_TYPE_CLICK)){
					//自定义菜单点击事�?
				}else if(eventType.equals(MessageUtil.EVENT_TYPE_SCAN)){
					//用户已关注时的事件推�?
				}else if(eventType.equals(MessageUtil.EVENT_TYPE_VIEW)){
					//点击菜单跳转链接时的事件推�?? 
				}
			}	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return respContent;
	}
	
	public static String torespTextMessage(String fromUserName,String toUserName,String content){
		TextMessage textMessage = new TextMessage();
		textMessage.setToUserName(fromUserName);
		textMessage.setFromUserName(toUserName);
		textMessage.setCreateTime(new Date().getTime());
		textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
		textMessage.setFuncFlag(0);
		textMessage.setContent(content);
		String resp =  MessageUtil.textMessageToXml(textMessage);
		return resp;
	}
	
	public static String torespImageMessage(String fromUserName,String toUserName,String content){
		TextMessage textMessage = new TextMessage();
		textMessage.setToUserName(fromUserName);
		textMessage.setFromUserName(toUserName);
		textMessage.setCreateTime(new Date().getTime());
		textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
		textMessage.setFuncFlag(0);
		textMessage.setContent(content);
		String resp =  MessageUtil.textMessageToXml(textMessage);
		return resp;
	}
	
	public static String torespVoiceMessage(String fromUserName,String toUserName,String content){
		TextMessage textMessage = new TextMessage();
		textMessage.setToUserName(fromUserName);
		textMessage.setFromUserName(toUserName);
		textMessage.setCreateTime(new Date().getTime());
		textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
		textMessage.setFuncFlag(0);
		textMessage.setContent(content);
		String resp =  MessageUtil.textMessageToXml(textMessage);
		return resp;
	}
	
	public static String torespVideoMessage(String fromUserName,String toUserName,String content){
		TextMessage textMessage = new TextMessage();
		textMessage.setToUserName(fromUserName);
		textMessage.setFromUserName(toUserName);
		textMessage.setCreateTime(new Date().getTime());
		textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
		textMessage.setFuncFlag(0);
		textMessage.setContent(content);
		String resp =  MessageUtil.textMessageToXml(textMessage);
		return resp;
	}
	
	public static String torespArticleMessage(String fromUserName,String toUserName,List<Article> content){
		ArticleMessage articleMessage = new ArticleMessage();
		articleMessage.setToUserName(fromUserName);
		articleMessage.setFromUserName(toUserName);
		articleMessage.setCreateTime(new Date().getTime());
		articleMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
		articleMessage.setFuncFlag(0);
		articleMessage.setArticleCount(content.size());
		articleMessage.setArticles(content);
		String resp =  MessageUtil.articleMessageToXml(articleMessage);
		return resp;
	}
	
	public static String torespMusicMessage(String fromUserName,String toUserName,Music content){
		MusicMessage musicMessage = new MusicMessage();
		musicMessage.setToUserName(fromUserName);
		musicMessage.setFromUserName(toUserName);
		musicMessage.setCreateTime(new Date().getTime());
		musicMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_MUSIC);
		musicMessage.setFuncFlag(0);
		musicMessage.setMusic(content);
		String resp =  MessageUtil.musicMessageToXml(musicMessage);
		return resp;
	}
	
	/** 
     * 人脸检测帮助菜单 
     */  
    public static String getUsage() {  
        StringBuffer buffer = new StringBuffer();  
        buffer.append("人脸检测使用指南").append("\n\n");  
        buffer.append("发送一张清晰的照片，就能帮你分析出种族、年龄、性别等信息").append("\n");  
        buffer.append("快来试试你是不是长得太着急");  
        return buffer.toString();  
    }  
	
	/*
	 * 主菜单
	 * */
	public static String getMainMenu() {  
	    StringBuffer buffer = new StringBuffer();  
	    buffer.append("您好,我是周冲小号代理,请回复数字选择服务：").append("\n");
	    buffer.append("------------------------------\n");
	    buffer.append("101  天气查询").append("\n");  
	    buffer.append("102  公交查询").append("\n");  
	    buffer.append("103  周边搜索").append("\n");  
	    buffer.append("104  歌曲点播").append("\n");  
	    buffer.append("105  人脸识别").append("\n");   
	    buffer.append("106  街景地图").append("\n");
	    buffer.append("107  四六级查询").append("\n");
	    buffer.append("108  小游戏").append("\n");
	    buffer.append("\n------------------------------\n");
	    buffer.append("回复“@”显示此帮助菜单");  
	    return buffer.toString();  
	}  
}
