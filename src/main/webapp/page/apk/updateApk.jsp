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
<title>Android 应用更新</title>

</head>
<body>
<div><a href="<%=basePath%>page/apk/apk_upload.jsp">上传安卓应用</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="<%=basePath%>app/public/applist.action?platform=android">安卓应用列表</a>
<a href="<%=basePath%>page/apk/app_upload.jsp">上传苹果应用</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="<%=basePath%>app/public/applist.action?platform=ios">苹果应用列表</a><br/>
<hr/>
</div>
<div style="text-align:center">

 <h3><c:if test="${platform =='android'}">Android 应用更新</c:if><c:if test="${platform =='ios'}">IOS 应用更新</c:if></h3>

</div>
<form action="<%=basePath%>app/public/apk/updateApkMsg.action" method="POST">
<table>
<c:if test="${not empty apk }">
 
 <tr><td>应用的名字</td><td><input type="text" name="appName" value="${apk.appName}" style="width:300px"/></td></tr>
 <tr><td>版本</td><td>${apk.versionName}</td></tr>
  <tr><td>包名</td><td><input type="text" name="packageName" value="${apk.packageName}" readOnly style="width:300px"/></td></tr>
 <tr><td>版本说明</td><td><textarea rows="6" cols="120" name="versionIntroduce">${apk.versionIntroduce}</textarea></td></tr>
 <tr><td></td><td><input type="submit" value="提交更新" style="width:120px;height:60px;"/></td></tr>
</c:if>
</table>

</form>
</body>
</html>