<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>应用管理主页</title>
 
</head>


<body>
   <c:if test="${not empty user }">
	<div >
		<ul style="list-style:none;">
			<li><a href="<%=basePath%>app/goToUpLoad.action?platform=${platform}" target="iframepageRight">新增${platform}应用</a></li>
			<li><a href="<%=basePath%>app/appOperator.action?platform=${platform}" target="iframepageRightTop">易就医应用</a></li>
		</ul>
	</div>
	</c:if>
</body>