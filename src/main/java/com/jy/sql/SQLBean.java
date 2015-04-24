package com.jy.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLBean {
	
	
	private Connection conn = null;
	private ResultSet resSet = null;
	private PreparedStatement pstmt = null;
	
	/*****1. 填写数据库相关信息(请查找数据库管理页面)*****/
	String databaseName = "ddc44d8dcba4345ccbe4daa44b346371e"; 

	String host = "10.4.12.173";
	String port = "3306";
	String username = "ueKgvniEGi5gR";//用户名;
	String password = "pksa9safEfmyG";//密码
	String driverName = "com.mysql.jdbc.Driver";
	String dbUrl = "jdbc:mysql://";
	String serverName = host + ":" + port + "/";
	String url = dbUrl + serverName + databaseName;
	 
	/******2. 接着连接并选择数据库名为databaseName的服务器******/
	
	public SQLBean(){
		try {
			Class.forName(driverName);
			this.conn = DriverManager.getConnection(url,username, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ResultSet query(String sql){
		try {
			pstmt = conn.prepareStatement(sql);
			resSet = pstmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			this.closeResource();
		}
		return resSet;
	}
	
	public ResultSet query(String sql,Object... param){
		try {
			pstmt = conn.prepareStatement(sql);
			for(int i=0; i < param.length; i++){
				pstmt.setObject(i+1, param[i]);
			}
			resSet = pstmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			this.closeResource();
		}
		return resSet;
	}
	
	public int update(String sql){
		int res =0;
		try {
			pstmt = conn.prepareStatement(sql);
			res = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			this.closeResource();
		}
		return res;
	}
	
	public int update(String sql,Object... param){
		int res=0;
		try {
			pstmt = conn.prepareStatement(sql);
			for(int i=0; i<param.length; i++){
				pstmt.setObject(i+1, param[i]);
			}
			res = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.closeResource();
		}
		return res;	
	}
	
	public void closeResource(){
		try {
			if(resSet != null){resSet.close();}
			if(pstmt != null){pstmt.close();}
			if(this.conn != null){this.conn.close();}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
