<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>话费充值</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta name="viewport" content="width=device-width,height=device-height,inital-scale=1.0,maximum-scale=1.0,user-scalable=no;">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="format-detection" content="telephone=no">
	<script type="text/javascript" src="js/jquery.min.js"></script>
	
	<style type="text/css">
		*{margin:0px;padding:0px;}
		.phone{width:100%;}
		.item{width:100%;height:40px;}
		.item label{height:40px;line-height:40px;}
		.item .phonenum{height:40px;line-height:40px;width:220px;border:1px solid #E2E2E2;
		height: 25px;line-height:25px;color:#333;text-indent: 5px;}
		.btn{background: none repeat scroll 0% 0% #999;border-radius:4px;line-height: 30px;padding: 0px 30px;
				border:0px none; color:#FFF; height:30px; cursor: pointer;}
		.disa{background:#999;}
	</style>
	
	<script type="text/javascript">
		$(document).ready(function(){
		
			$(".phonenum").change(function(){
				$(".teltips").html(' ');
				$("#sell").html("...");
				$(".phonenum").css({background:"#FFF"});
				$(".btn").val("检测中");
				
				var phoneno = $(this).val();
				
				var cardnum = $("input[name=cardnum]:checked").val();
				
				if(!isMobile(phoneno)){
					$(".teltips").html("错误的手机号码！");
					$(".phonenum").css({background:"#fae1dc"}).focus();
				}else {
					$.post("PhoneFreeServlet?action=check",{phoneno:phoneno,cardnum:cardnum},function(res){
						
						alert(res);
						if(res.error_code == 0){
							$(".btn").removeClass("disa").removeAttr("disabled").val("马上充值");
							$("#sell").html("￥"+res.result.inprice);
						}else{
							$(".btn").addClass("disa").attr("disabled","disabled").val("无法充值");
							$(".teltips").html(obj.reason);
							$("#sell").html("-");
						}
					},"json");
				}
			});
			
			$("input[name=cardnum]").click(function(){
				$(".teltips").html(' ');
				$("#sell").html("...");
				$(".btn").val("检测中");
				
				var cardnum = $("input[name=cardnum]:checked").val();
				var phone = $(".phonenum").val();
				
				if(isMobile(phone)){
					$.post("PhoneFreeServlet?action=check",{phoneno:phone,cardnum:cardnum},function(res){
						alert(res);
						if(res.error_code == 0){
							$(".btn").removeClass("disa").removeAttr("disabled").val("马上充值");
							$("#sell").html("￥"+res.result.inprice);
						}else{
							$(".btn").addClass("disa").attr("disabled","disabled").val("无法充值");
							$(".teltips").html(res.reason);
							$("#sell").html("-");
						}
					},"json");
				}else{
					$(".btn").addClass("disa").attr("disabled","disabled").val("无法充值");
					var scardnum = 0;
					if(cardnum==30){
						scardnum = 29.7;
					}else if(cardnum==50){
						 scardnum = 49.35;
					}else if(cardnum==100){
						scardnum = 99;
					}else if(cardnum==300){
						scardnum = 297;
					}
					$("#sell").html("￥"+scardnum);
					if(phone == " "){
						$(".teltips").html("请输入手机号！");
					}else {
						$(".teltips").html("错误的手机号码！");
					}
				}
			});
			
			$(".btn").click(function(){
				$(".teltips").html("");
				
				var phone = $(".phonenum").val();
				
				if(!isMobile(phone)){
					$(".teltips").html("错误的手机号码！");
					$(".phonenum").css({background:"#fae1dc"}).focus();
				}else{
					$("#telform").submit();
				}
			});
		});
		
		function isMobile(mobile){
			var mreg =/^1[3|5|8|4|7]{1}[0-9]{1}[0-9]{8}$/;
			if(!mreg.test(mobile)){
				return false;
			}
			return true;
		}
	</script>
	
  </head>
  
  <body>
    	<div class="top">
  		<img src="images/hfcz.jpg" width="100%" height="90px" />
  	</div>
   <div class="phone">
   	<form action="PhoneFreeServlet?action=charge"  method="post" name="telform" id="telform">
   		<div class="item">
   			<label>手机号码:</label>
   			<input type="text" name="phonenumber" class="phonenum">
   		</div>
   		<div class="item">
   			<label>充值面额:</label>
   			<input type="radio" name="cardnum"  value="30"  checked>30元
   			<input type="radio" name="cardnum"  value="50" >50元
   			<input type="radio" name="cardnum"  value="100" >100元
   			<input type="radio" name="cardnum"  value="300" >300元
   		</div>
   		 <div class="item">
   			<label>购买价格:</label>
			<span id="sell"></span>
   		</div>
   		<div class="item">
   			<input class="btn disa" disabled type="button" value="马上充值" />
   		</div>
   	</form>
   	<div class="teltips">&nbsp;</div>
   </div>
  </body>
</html>
