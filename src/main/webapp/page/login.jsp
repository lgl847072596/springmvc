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
<title>用户登录</title>
 <link type="text/css" rel="stylesheet" href="<%=basePath%>css/login.css">
    <script src="<%=basePath%>js/jquery-2.2.3.min.js"></script>
    <script src="<%=basePath%>js/login.js"></script>

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
	return true;
}

function register(){
	var form=document.getElementById("form");
	form.action="<%=basePath%>user/public/register.action";
			return true;
		}
	</script>



<div class="cd-user-modal">
    <div class="cd-user-modal-container">

        <div style="text-align: center;padding-top: 20px;"><label style="font-size: 20px;font-weight: bold">用户登录</label></div>

        <div id="cd-login"> <!-- 登录表单 -->
            <form class="cd-form" id="form">
                <p class="fieldset">
                    <label class="image-replace cd-username" for="signin-username">用户名</label>
                    <input class="full-width has-padding has-border" id="signin-username" name="account" type="text" placeholder="输入账号">
                </p>

                <p class="fieldset">
                    <label class="image-replace cd-password" for="signin-password">密码</label>
                    <input class="full-width has-padding has-border" id="signin-password" name="password" type="password"  placeholder="输入密码">
                </p>
                <p class="fieldset">
                    <input class="full-width2" type="submit" onclick="login()" value="登 录">
                </p>
                <p class="fieldset">
                    <input class="full-width2" type="submit" onclick="register()" value="注 册">
                </p>
            </form>
        </div>
        </div>
    </div>

</body>
</html>