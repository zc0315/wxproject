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
		body, html{width: 100%;height: 100%;overflow: hidden;margin:0;}
		#panorama {width:800px;height:50%;overflow: hidden;border:1px solid red;}
		#container {height:50%;overflow: hidden;}
	 </style>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=E3yG57aNtrz2PoKm68pE2fiu"></script>
  </head>
  <%
  //String p1 = request.getParameter("p1");
  //String p2 = request.getParameter("p2");
  String p1 = "114.556387,38.024482";
  String p2 = "114.554736,38.021297";
   %>
  <body>
  	<div id="panorama"></div>
   	<div id="container"></div>
  </body> 
   <script type="text/javascript">
	//创建起点，终点的经纬度坐标点
	var p1 = new BMap.Point(<%=p1%>);
	var p2 = new BMap.Point(<%=p2%>);

	//全景图展示
	var panorama = new BMap.Panorama('panorama');
	panorama.setPosition(p1); //根据经纬度坐标展示全景图
	//panorama.setPosition(new BMap.Point(120.320032, 31.589666)); //根据经纬度坐标展示全景图
	panorama.setPov({heading: -40, pitch: 6});
	
	panorama.addEventListener('position_changed', function(e){ //全景图位置改变后，普通地图中心点也随之改变
	    var pos = panorama.getPosition();
	    map.setCenter(new BMap.Point(pos.lng, pos.lat));
	    marker.setPosition(pos);
	});
	
	
	//普通地图展示
	var mapOption = {
	        mapType: BMAP_NORMAL_MAP,
	        maxZoom: 18,
	        drawMargin:0,
	        enableFulltimeSpotClick: true,
	        enableHighResolution:true
	    }
	var map = new BMap.Map("container", mapOption);
	var testpoint = new BMap.Point(120.320032, 31.589666);
	map.centerAndZoom(p1, 18);
	map.addTileLayer(new BMap.PanoramaCoverageLayer());
	var marker=new BMap.Marker(testpoint);
	marker.enableDragging();
	map.addOverlay(marker);  
	marker.addEventListener('dragend',function(e){
	    panorama.setPosition(e.point); //拖动marker后，全景图位置也随着改变
	    panorama.setPov({heading: -40, pitch: 6});}
	);

	
	//alert("11111");
	//创建地图、设置中心坐标和默认缩放级别
	//var map = new BMap.Map("container");

	map.addControl(new BMap.NavigationControl({anchor:BMAP_ANCHOR_BOTTOM_RIGHT,type:BMAP_NAVIGATION_CONTROL_ZOOM}));
	
	//步行导航检索
	var walking = new BMap.WalkingRoute(map, {renderOptions:{map: map, autoViewport: true}});
	walking.search(p1, p2);
</script>
  
</html>
