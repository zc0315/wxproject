package com.jy.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jy.imp.CoreService;

/*
 * 核心处理servlet
 * 
 * */
public class TestServlet extends HttpServlet {

	private static final long serialVersionUID = 5516811022321534569L;
	

	/**
	 * 确认请求来自微信服务�? 
	 * */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//微信加密签名
		String signature  = req.getParameter("signature");
		//时间�?
		String timestamp = req.getParameter("timestamp");
		//随机�?
		String nonce = req.getParameter("nonce");
		
		//随机字符�?
		String echostr = req.getParameter("echostr");
		
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		// 通过�?验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败  
		if(SignUtil.checkSignature(signature, timestamp, nonce)){
			out.print(echostr);
			//resp.sendRedirect(echostr);
			System.out.println(signature);
		}
		out.close();
		out = null;
	}

	/** 
     * 处理微信服务器发来的消息 
     */  
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// 将请求�?�响应的编码均设置为UTF-8（防止中文乱码）  
        req.setCharacterEncoding("UTF-8");  
        resp.setCharacterEncoding("UTF-8");  
        System.out.println("消息来了");
        // 调用核心业务类接收消息�?�处理消�?  
        String respMessage = CoreService.processRequest(req);  
        // 响应消息  
        
        PrintWriter out = resp.getWriter(); 
    	out.print(respMessage); 
    	out.close(); 
        System.out.println("消息响应");
        System.out.println(respMessage); 
	}
}
