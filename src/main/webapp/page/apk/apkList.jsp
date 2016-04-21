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


<title>应用列表</title>
<style type="text/css">
   table tr td{ border-bottom:#FF0000 solid 1px; text-align:center; line-height:24px;}
</style>
</head>
<body>
<div><a href="<%=basePath%>page/apk/apk_upload.jsp">上传安卓应用</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="<%=basePath%>app/public/applist.action?platform=android">安卓应用列表</a>
<a href="<%=basePath%>page/apk/app_upload.jsp">上传苹果应用</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="<%=basePath%>app/public/applist.action?platform=ios">苹果应用列表</a><br/>
<hr/>
</div>

<div style="text-align:center">

 <h3><c:if test="${platform =='android'}">Android 应用列表</c:if><c:if test="${platform =='ios'}">IOS 应用列表</c:if></h3>

</div>

<table>
<c:if test="${not empty appList }">
 <tr>
 	<th>序号</th>
 	<th>名字</th>
 	<th>版本</th>
 	<th>包名</th>
 	<th>版本code</th>
 	<th>版本更新时间</th>
 	<th>该版本下载次数</th>
 	<th>应用下载</th>
 	<th>老版本</th>
 	<th>修改名称和更新说明</th>
 </tr>
  <c:forEach var="apk" items="${appList}" varStatus="status">
    <tr>
 		<td>${status.index}</td>
 		<td>${apk.appName}</td>
 		<td style="color:red;">${apk.versionName}</td>
 		<td style="color:blue;">${apk.packageName}</td>
 		<td >${apk.versionCode}</td>
 		<td>${apk.updateTime}</td>
 		<td>${apk.downLoadCount}次</td>
 		<td><a href="<%=basePath%>app/public/download/app.action?tb=new&packageName=${apk.packageName}&versionCode=${apk.versionCode}&platform=${apk.platform}&url=${apk.url}">下载</a></td>
 		<td><a href="<%=basePath%>app/public/apkOldlist.action?packageName=${apk.packageName}&platform=${apk.platform}">历史版本</a></td>
 	    <td><a href="<%=basePath%>app/public/apk/goToUpdateApk.action?packageName=${apk.packageName}&platform=${apk.platform}">更新</a></td>	
 	</tr>
  </c:forEach>
	
</c:if>
</table>

</body>
</html>