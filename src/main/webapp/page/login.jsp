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
<title>世纪战场入口</title>
</head>
<body>

<c:if test="${not empty message }">
	<script type="text/javascript">
		alert("${message}");
	</script>
</c:if>

<script type="text/javascript">

function login(){
	var form=document.getElementById("form");
	form.action="<%=basePath%>user/public/login.action";
	form.submit();
}

function register(){
	var form=document.getElementById("form");
	form.action="<%=basePath%>user/public/register.action";
	form.submit();
}

</script>

	<form  id="form"  method="post">

		<fieldset>
			<legend>用户名</legend>
			<input type="text" maxlength="100" name="account"  value='<c:out value="${account }"></c:out>' />
		</fieldset>
		<fieldset>
		<legend>密码</legend>
		<input type="password" maxlength="100" name="password"  value='<c:out value="${password }"></c:out>' />
		</fieldset>
		<button onclick="login()">登录战场</button>	<button onclick="register()">注册</button>
	</form>


</body>
</html>