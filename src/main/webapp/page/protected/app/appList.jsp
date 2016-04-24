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
<meta content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0" name="viewport">
<title>应用列表</title>
<style type="text/css">
table {
	border: #bcbcbc 1px solid;
	border-collapse: collapse;
	padding: 3px;
	width: 100%;
	margin-left: auto;
	margin-right: auto;
}

td {
	margin: 5px;
	padding: 5px;
	border: green 1px solid;
	text-align: center;
}
</style>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/common.css" />
<script src="<%=basePath%>js/jquery-2.2.3.min.js"></script>
<script type="text/javascript">
	$(function() {
		$("tr:even").css("background", "#bcbcbc");
		$("tr:odd").css("background", "white");
	});
</script>
</head>
<body>


	<div style="text-align: center">

		<h3 style="text-size: 24px; padding-top: 10px; padding-bottom: 10px;">
			<c:if test="${platform =='android'}">Android 应用列表</c:if>
			<c:if test="${platform =='ios'}">IOS 应用列表</c:if>
		</h3>

	</div>

	<table>
		<c:if test="${not empty appList }">
			<tr>
				<th>序号</th>
				<th>名字</th>
				<th>版本</th>
				<th>包名</th>
				<th>版本号</th>
				<th>最新时间</th>
				<th>下载次数</th>
				<th>下载别名</th>
				<th>应用下载</th>
				<th>历史版本</th>
				<th>更新</th>
			</tr>
			<c:forEach var="apk" items="${appList}" varStatus="status">
				<tr>
					<td>${status.index}</td>
					<td>${apk.appName}</td>
					<td style="color: red;">${apk.versionName}</td>
					<td style="color: blue;">${apk.packageName}</td>
					<td>${apk.versionCode}</td>
					<td>${apk.updateTime}</td>
					<td>${apk.downloadCount}次</td>
					<td>${apk.mappingUrl}</td>
					<td><c:if test="${apk.platform =='android'}">
							<a
								href="<%=basePath%>app/public/download/app.action?categroy=new&platform=${apk.platform}&url=${apk.url}&mappingUrl=${apk.mappingUrl}">下载</a>
						</c:if></td>
					<td><a
						href="<%=basePath%>app/appOldlist.action?packageName=${apk.packageName}&platform=${apk.platform}">历史版本</a></td>

					<td><a
						href="<%=basePath%>app/goToUpdateApk.action?packageName=${apk.packageName}&platform=${apk.platform}">更新</a></td>

				</tr>
			</c:forEach>

		</c:if>
	</table>

</body>
</html>