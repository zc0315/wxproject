package com.jy.test;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.jy.sql.SQLBean;

public class MySqlTest {
		public static void main(String[] args){
			SQLBean dao = new SQLBean();
			
			/*String sql = "insert into user_location(open_id,lng,lat,bd09_lng,bd09_lat,label) values(?,?,?,?,?,?)";
			
			int res=dao.update(sql, new Object[]{"gh_3ac1d99a9c46","dddd","ddddd","eeeee","adefed","你好"});
			dao.closeResource();
			if(res >0 ){
				System.out.println("执行成功");
			}else {
				System.out.println("执行失败");
				
			}*/
			
			String sql ="select * from user_location where open_id = ?";
			ResultSet result = dao.query(sql, new Object[]{"gh_3ac1d9"});
			
			try {
				if(result.next()){
					System.out.println(result.getString(1));

					System.out.println(result.getString(2));

					System.out.println(result.getString(3));

					System.out.println(result.getString(4));
					
					sql = "insert into user_location(open_id,lng,lat,bd09_lng,bd09_lat,label) values(?,?,?,?,?,?)";
					
					int res=dao.update(sql, new Object[]{"gh_3ac1d9ddd46","dddd","ddddd","eeeee","adefed","你好"});
					dao.closeResource();
					if(res >0 ){
						System.out.println("执行成功");
					}else {
						System.out.println("执行失败");
						
					}
					
				}else{
					System.out.println("没有查到");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
}
