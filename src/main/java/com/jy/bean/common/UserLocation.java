package com.jy.bean.common;

public class UserLocation {
	//编号
	private int id;
	//openid
	private String openId;
	
	//用户的经度
	private String lng;
	
	//用户的维度
	private String lat;
	
	//转换后的经度
	private String bd09_lng;
	//转换后的维度
	private String bd09_lat;
	
	//地理位置信息
	private String label;
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getBd09_lng() {
		return bd09_lng;
	}
	public void setBd09_lng(String bd09_lng) {
		this.bd09_lng = bd09_lng;
	}
	public String getBd09_lat() {
		return bd09_lat;
	}
	public void setBd09_lat(String bd09_lat) {
		this.bd09_lat = bd09_lat;
	}
	
}
