package com.jy.imp;

import java.net.URLEncoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jy.bean.common.BaiduPlace;
import com.jy.bean.common.UserLocation;
import com.jy.bean.resp.Article;
import com.jy.sql.SQLBean;
import com.jy.tools.MyTools;

public class LBSService {
	
	public static List<BaiduPlace> searchPlace(String query,String lng,String lat){
		//拼装请求地址
		String requestUrl = "http://api.map.baidu.com/place/v2/search?&query="+MyTools.urlUtf8Encode(query)+"&location="+lat+","+lng+"&radius=2000&output=xml&scope=2&page_size=10&page_num=0&ak=E3yG57aNtrz2PoKm68pE2fiu";
		//http://api.map.baidu.com/place/v2/search?&query=%E9%93%B6%E8%A1%8C&location=39.915,116.404&radius=2000&output=xml&ak=E4805d16520de693a3fe707cdc962045

		
		System.out.println(requestUrl);
		
		//调用Place API 圆形区域检索
		String respXML = BaiduMapUtil.HttpRequest(requestUrl);

		//解析返回的XML
		List<BaiduPlace> placeList = BaiduMapUtil.parsePlaceXml(respXML);
		
		return placeList;	
	}
	
	
	public static boolean saveUserPlace(UserLocation userLocation){
		SQLBean dao = new SQLBean();
		
		String sql = "select * from user_location where open_id = ?";
		
		ResultSet result = dao.query(sql, new Object[]{userLocation.getOpenId()});
		int res =0;
		try {
			if(result.next()){
			
				sql = "update user_location set lng=?,lat=?,bd09_lng=?,bd09_lat=?,label=? where open_id = ?";
				
				res = dao.update(sql, new Object[]{userLocation.getLng(),userLocation.getLat(),userLocation.getBd09_lng(),userLocation.getBd09_lat(),userLocation.getLabel(),userLocation.getOpenId()});


			}else{
				
				sql = "insert into user_location(open_id,lng,lat,bd09_lng,bd09_lat,label) values(?,?,?,?,?,?)";
				
				res = dao.update(sql, new Object[]{userLocation.getOpenId(),userLocation.getLng(),userLocation.getLat(),userLocation.getBd09_lng(),userLocation.getBd09_lat(),userLocation.getLabel()});
				
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dao.closeResource();
		if(res > 0){
			return true;
		}else{
			return false;
		}	
	}
	public static UserLocation getLastUserLocation(String open_id){
		UserLocation userlast = null;
		
		SQLBean dao = new SQLBean();
		
		String sql = "select * from user_location where open_id = ?";
		
		ResultSet result = dao.query(sql, new Object[]{open_id});
		
		try {
			if(result.next()){
				userlast = new UserLocation();
				userlast.setOpenId(result.getString(2));
				userlast.setLng(result.getString(3));
				userlast.setLat(result.getString(4));
				userlast.setBd09_lng(result.getString(5));
				userlast.setBd09_lat(result.getString(6));
				userlast.setLabel(result.getString(7));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return userlast;	
	}
	
	public static List<Article> makeArticleList(List<BaiduPlace> placeList,String bd09_lng,String bd09_lat){
		String basePath = "http://zcwx.oschina.mopaas.com/";
		List<Article> list = new ArrayList<Article>();
		BaiduPlace place = null;
		
		for(int i=0; i<placeList.size(); i++){
			place = placeList.get(i);
			
			Article article = new Article();
			
			article.setTitle(place.getName()+"\n距离约"+place.getDistance()+"米");
			article.setUrl(String.format(basePath+"route.jsp?p1=%s,%s&p2=%s,%s",bd09_lng,bd09_lat,place.getLng(),place.getLat()));
			
			//将首条图文的图片设置为大图
			
			if(i == 0){
				article.setPicUrl(basePath+"images/tu1.jpg");
			}else{
				article.setPicUrl(basePath+"images/baidubj.png");
			}
			list.add(article);
		}
		return list;
	}
	
}
