package com.jy.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jy.bean.common.cetScoreBean;
import com.jy.imp.Cet46Service;

public class QueryCet46Servlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String zkzh = req.getParameter("zkzh");
		String name = req.getParameter("xm");
		System.out.println("准考证号："+zkzh+"姓名："+name);
		String str = Cet46Service.queryCet(zkzh, name);
		cetScoreBean cetscore =null;
		System.out.println(str);
		int startIndex = str.indexOf("学校：</th>		<td>");
		if(startIndex > 0){
			cetscore = new cetScoreBean();
			int length = "学校：</th>		<td>".length();
			int endIndex = str.indexOf("</td>", startIndex);	
			cetscore.setSchool(str.substring(startIndex+length,endIndex));
			
			str = str.substring(endIndex,endIndex+500);
			
			//System.out.println(str);
			
			 startIndex = str.indexOf("考试类别：</th>		<td>");
			 length = "考试类别：</th>		<td>".length();
		     endIndex = str.indexOf("</td>", startIndex);
		     cetscore.setCetType(str.substring(startIndex+length,endIndex));
		     
			 startIndex = str.indexOf("class=\"colorRed\">");
			 length = "class=\"colorRed\">".length();
		     endIndex = str.indexOf("</span>", startIndex);
		     cetscore.setScore(Float.parseFloat(str.substring(startIndex+length,endIndex)));
			 
		     startIndex = str.indexOf("听力：</span>");
			 length = "听力：</span>".length();
		     endIndex = str.indexOf("<br />", startIndex);
		     cetscore.setListen(Float.parseFloat(str.substring(startIndex+length,endIndex)));
		     
		     startIndex = str.indexOf("阅读：</span>");
			 length = "阅读：</span>".length();
		     endIndex = str.indexOf("<br />", startIndex);
		     cetscore.setReading(Float.parseFloat(str.substring(startIndex+length,endIndex)));
		     
		     startIndex = str.indexOf("写作与翻译：</span>");
			 length = "写作与翻译：</span>".length();
		     endIndex = str.indexOf("</td>", startIndex);
		     cetscore.setWrite_translation(Float.parseFloat(str.substring(startIndex+length,endIndex)));
		     
			 cetscore.setZkzh(zkzh);
			 cetscore.setXm(name);

		}
	     HttpSession session = req.getSession();
	     
	     session.setAttribute("cetResult", cetscore);
	     
	     resp.sendRedirect("cetResult.jsp");
	     
	}

}
