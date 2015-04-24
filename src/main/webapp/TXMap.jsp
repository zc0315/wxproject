<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html> 
<html> 
<head> 
<title>街景与地图连动插件</title> 
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<meta http-equiv="Content-Type" content="text/html;charset=gb2312" />
<script charset="utf-8" src="http://map.qq.com/api/js?v=2.exp&key=OZMBZ-G5NHW-SB6RA-OIBLC-NFDSE-YJF3G"></script> 
<!--引入插件--> 
<script src="http://open.map.qq.com/plugin/v2/PanoramaOverview/PanoramaOverview-min.js"></script> 
<style type="text/css">
	*{margin:0;padding:0;}
</style>
</head> 
<body> 
<!--以下div街景-->
<div id="panoCon" style="height:300px;overflow: hidden;"></div> 
<!--以下div显示地图-->
<div id="overViewCon" style="height:200px;overflow: hidden;"></div> 
</body> 
<script> 
	//创建街景
	var panorama = new soso.maps.Panorama('panoCon', { 
	 pano:'11011014130424123253500',pov:{heading:180,pitch:0},zoom:1}); 
	//创建插件实例 
	var ovc=document.getElementById('overViewCon'); //地图容器（与街景连动的地图）
	var overview = new soso.maps.PanoOverview(ovc,{ 
	panorama:panorama 
	}); 
	 
	//以下方法可获取连动地图的Map实例
	//获取后，可根据自己需要进行后续逻辑开发，如：在地图上标注、添加infowindow 或 覆盖物等
	var map=overview.getMap();
	 
</script> 
</html>
  
