package com.jy.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jy.imp.PhoneFreeService;

public class PhoneFreeServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
		String action = req.getParameter("action");
		String phoneno = req.getParameter("phoneno");
		String cardnum = req.getParameter("cardnum");
		System.out.println("action:"+action+"phoneno:"+phoneno+"cnum:"+cardnum);
		resp.setContentType("text/json;charset=UTF-8");
		String result = null;
		PrintWriter out = resp.getWriter();
		if(action.equals("check")){
			String url ="http://op.juhe.cn/ofpay/mobile/telquery?cardnum="+cardnum+"&phoneno="+phoneno+"&key=605939dc82ceb62ff4fc4bb82159f347";
			
			System.out.println("url  "+url);
			result = PhoneFreeService.checkPhoneNum(url);
		}else if(action.equals("charge")){
			
		}
		System.out.println(result);
		out.print(result);
		out.close();
	}

}
