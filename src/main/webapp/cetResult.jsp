<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page language="java" import="com.jy.bean.common.cetScoreBean" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!Doctype html>
<html xmlns=http://www.w3.org/1999/xhtml>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>查询结果</title>
		<meta name="viewport" content="width=device-width,height=device-height,inital-scale=1.0,maximum-scale=1.0,user-scalable=no;">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<meta name="format-detection" content="telephone=no">
		<script type="text/javascript" src="js/jquery.min.js"></script>
		<script type="text/javascript" src="js/main.js"></script>
		
		<style type="text/css">
			*{margin:0px;padding:0px;}
			body{background-color: #ECECEC;font-family: Microsoft YaHei,Helvitica,Verdana,Tohoma,Arial,san-serif;
    			margin: 0;overflow-x: hidden;color:rgba(0,0,0,0.9);font-size:14px;}
			.logo{width:100%;}
			.result{height:80%;border:1px solid #C6C6C6;border-radius:5px;
					background-color:white;margin:5px 5px 0px 5px;}
			.result .title{color: #373B3E;text-align: left;font-weight:normal; font-size: 16px;line-height: 20px;
    			padding: 10px 0 10px 10px;border-bottom:1px solid #C6C6C6;background-color:rgba(232,232,232,0.8);}
			.result table td{text-align:center;}
			#mcover{position:fixed; top:0; left:0;width:100%;height:100%;
				background:rgba(0,0,0,0.7);display:none;z-index:2000;}
			#mcover img {position: fixed;right: 18px;top:5px;width:260px;height:180px;z-index:20001;}
			#mess_share{height:30px;margin:5px 5px 0px 5px;}
			/*分享share*/
			#share_1{float:left;width:49%; display: block;}
			#share_2{float:right;width:49%; display: block;}
			#mess_share img{width:22px;height:22px;vertical-align: top;border: 0;}
		</style>
		
	</head>
	<%
		cetScoreBean cetResult = (cetScoreBean)session.getAttribute("cetResult");
	%>

	<body>
		<div class="logo">
			<img src="images/logo.jpg" alt="logo" width="100%" height="90px" />
		</div>
		<div class="result">
			<h2 class="title">查询结果</h2>
	
			<%
				if(cetResult != null){  
			%>
			<div>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tbody>
						<tr>
							<th>考试类别</th>
						</tr>
						<tr>
							<td><b style="background-color:yellow"><%=cetResult.getCetType() %></b></td>
						</tr>
						<tr>
							<th>考生姓名</th>
						</tr>
						<tr>
							<td><b style="background-color:yellow"><%=cetResult.getXm() %></b></td>
						</tr>
						<tr>
							<th>学校</th>
						</tr>
						<tr>
							<td><b style="background-color:yellow"><%=cetResult.getSchool() %></b></td>
						</tr>
						<tr>
							<th>准考证号</th>
						</tr>
						<tr>
							<td><b style="background-color:yellow"><%=cetResult.getZkzh()%></b></td>
						</tr>
						<tr>
							<th>听力</th>
						</tr>
						<tr>
							<td><b style="background-color:yellow"><%=cetResult.getListen()%></b></td>
						</tr>
						<tr>
							<th>阅读</th>
						</tr>
						<tr>
							<td><b style="background-color:yellow"><%=cetResult.getReading()%></b></td>
						</tr>
						<tr>
							<th>写作翻译</th>
						</tr>
						<tr>
							<td><b style="background-color:yellow"><%=cetResult.getWrite_translation()%></b></td>
						</tr>
						<tr>
							<th>成绩总分</th>
						</tr>
						<tr>
							<td><b style="background-color:yellow"><%=cetResult.getScore()%></b></td>
						</tr>
					</tbody>
				</table>
			</div>
			<%
				}else{
			%>
			<div style="height:100px;line-height:50px;">
				<a href="http://zcwx.oschina.mopaas.com/cet46.html"><span id="layer" >5秒后，转入到输入界面。</span></a>
			<%  
        	//转向语句  
       		response.setHeader("Refresh", "5;URL=http://zcwx.oschina.mopaas.com/cet46.html"); %>
			<script type="text/javascript">  
				var time = 5;  
				function returnUrlByTime(){
					window.setTimeout('returnUrlByTime()', 1000);  
					document.getElementById("layer").innerHTML = time+"秒后，转入输入界面。"; 
					time = time - 1;  
				}  
				returnUrlByTime();
			</script> 
			<%
				}
			%>
			</div>
		</div>
		<div >
            <div id="mcover" onclick="document.getElementById('mcover').style.display='';" style="">
				<img src="images/guide.png">
			</div>
            <div id="mess_share">
                <div id="share_1">
                    <button class="button2" onclick="document.getElementById('mcover').style.display='block';"><img src="images/icon_msg.png"> 发送给朋友</button>
                </div>
                <div id="share_2">
                    <button class="button2" onclick="document.getElementById('mcover').style.display='block';"><img src="images/icon_timeline.png"> 分享到朋友圈</button>
                </div>
            </div>	
		</div>
		<script type="text/javascript">
			document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
				// 发送给好友
				WeixinJSBridge.on('menu:share:appmessage', function (argv) {
					WeixinJSBridge.invoke('sendAppMessage', {
						"img_url": "http://g.hiphotos.bdimg.com/wisegame/pic/item/462dd42a2834349b3d99e9c1cbea15ce37d3bed3.jpg",
						"img_width": "160",
						"img_height": "160",
						"link": "http://zcwx.oschina.mopaas.com/cet46.html",
						"desc":  "全国大学英语四六级考试成绩查询微信入口",
						"title": "四六级查询"
					}, function (res) {
						_report('send_msg', res.err_msg);
					})
				});

				// 分享到朋友圈
				WeixinJSBridge.on('menu:share:timeline', function (argv) {
					WeixinJSBridge.invoke('shareTimeline', {
						"img_url": "http://g.hiphotos.bdimg.com/wisegame/pic/item/462dd42a2834349b3d99e9c1cbea15ce37d3bed3.jpg",
						"img_width": "160",
						"img_height": "160",
						"link": "http://zcwx.oschina.mopaas.com/cet46.html",
						"desc":  "全国大学英语四六级考试成绩查询微信入口",
						"title": "四六级查询"
					}, function (res) {
						_report('timeline', res.err_msg);
					});
				});
			}, false)
		</script>
        <script type="text/javascript">
            document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
                WeixinJSBridge.call('hideToolbar');
            });
        </script>
	</body>
</html>