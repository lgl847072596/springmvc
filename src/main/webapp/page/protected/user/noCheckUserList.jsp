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


<title>待审核列表</title>
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
	border: #bcbcbc 1px solid;
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
			待审核用户列表
		</h3>

	</div>


	<table>
		<c:if test="${not empty userList }">
			<tr>
				<th>序号</th>
				<th>账号</th>
				<th>名字</th>
				<th>创建日期</th>
				<th>是否同意</th>
			</tr>
			<c:forEach var="people" items="${userList}" varStatus="status">
				<tr>
					<td>${status.index}</td>
					<td style="color: red;">${people.account}</td>
					<td style="color: blue;"></td>
					<td>${people.createTime}</td>
					<td><a
						href="<%=basePath%>user/adminCheckUser.action?account=${people.account}&roleLevel=1">同意</a></td>
				</tr>
			</c:forEach>

		</c:if>
	</table>

</body>
</html>