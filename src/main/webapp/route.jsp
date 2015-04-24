<%@ page language="java" pageEncoding="utf-8" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>步行导航</title>
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<meta http-equiv="Content-Type" content="text/html;charset=gb2312" />
	
	<style type="text/css">  
		body, html,#container{width: 100%;height: 100%;overflow: hidden;margin:0;}
	 </style>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=E3yG57aNtrz2PoKm68pE2fiu"></script>
  </head>
  <%
  String p1 = request.getParameter("p1");
  String p2 = request.getParameter("p2");
  
   %>
  <body>
   	<div id="container"></div>
  </body> 
   <script type="text/javascript">
	//创建起点，终点的经纬度坐标点
	var p1 = new BMap.Point(<%=p1%>);
	var p2 = new BMap.Point(<%=p2%>);


	var center=new BMap.Point((p1.lng+p2.lng)/2,(p1.lat,p2.lat)/2);
	//普通地图展示
	var mapOption = {
	        mapType: BMAP_NORMAL_MAP,
	        maxZoom: 18,
	        drawMargin:0,
	        enableFulltimeSpotClick: true,
	        enableHighResolution:true
	    }
	var map = new BMap.Map("container", mapOption);
	map.centerAndZoom(center, 17);
	map.addTileLayer(new BMap.PanoramaCoverageLayer());
	map.addControl(new BMap.NavigationControl({anchor:BMAP_ANCHOR_BOTTOM_RIGHT,type:BMAP_NAVIGATION_CONTROL_ZOOM}));
	//步行导航检索
	var walking = new BMap.WalkingRoute(map, {renderOptions:{map: map, autoViewport: true}});
	walking.search(p1, p2);
</script>
  
</html>
