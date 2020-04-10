<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="">
    
    <title>body</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/book/list.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/jsp/pager/pager.css'/>" />
    <script type="text/javascript" src="<c:url value='/jsp/pager/pager.js'/>"></script>
	<script src="https://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
	<script type="text/javascript" src="<c:url value='/jsp/js/book/list.js'/>"></script>
<style type="text/css">
a {text-decoration: none;}

	body {
		font-size: 10pt;
		color: #404040;
		font-family: SimSun;
	}
	
	.divBody {
		margin-left: 15%;
	}
	
	.divTitle {
		text-align:left;
		width: 900px;
		height: 25px;
		line-height: 25px;
		background-color: #efeae5;
		border: 5px solid #efeae5;
	}
	.divContent {
		width: 900px;
		height: 230px;
		border: 5px solid #efeae5;
		margin-right:20px; 
		margin-bottom:20px;
	}
	.spanTitle {
		margin-top: 10px;
		margin-left:10px;
		height:25px;
		font-weight: 900;
	}
a {text-decoration: none;}
a:visited {color: #018BD3;}
a:hover {color:#FF6600; text-decoration: underline;}
}

</style>
  </head>
  <body>
  <div>
 <a href="<c:url value='/BookServlet/recommend'/>" target="body" style="font-size: 50pt">推荐图书</a></div>
   <div>
<a href="<c:url value='/BookServlet/newBook'/>" target="body" style="font-size: 50pt">最新上架图书</a>
</div>
  </body>
</html>
