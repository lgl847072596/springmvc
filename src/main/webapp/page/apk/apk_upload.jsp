<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>上传Android 应用</title>
</head>
<style>

  #drop_area{
  background-color:#959595;
  width:100%;
  height:500px;
  
  }
  
  #preview{
  
   width:100%;
  height:100px;
  background-color:#ffffff;
  }

</style>
<script src="<%=basePath%>js/config.js"></script>
<script src="<%=basePath%>js/upload_apk.js"></script>

<body>
<div><a href="<%=basePath%>page/apk/apk_upload.jsp">上传安卓应用</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="<%=basePath%>app/public/applist.action?platform=android">安卓应用列表</a>
<a href="<%=basePath%>page/apk/app_upload.jsp">上传苹果应用</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="<%=basePath%>app/public/applist.action?platform=ios">苹果应用列表</a><br/>
<hr/>
</div>
<div style="text-align:center">

 <h3><c:if test="${platform =='android'}">Android 应用上传</c:if><c:if test="${platform =='ios'}">IOS 应用应用上传</c:if></h3>

</div>
<div id="drop_area" ></div>
<div id="preview" ></div>




</body>
</html>