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
<title>个人信息</title>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/common.css" />
<style type="text/css">
 .submitClass{
   width:300px; 
    height:30px;
   }
   .inputClass{
   width:300px;
   height:20px;
   
   }
table {
	margin-left: auto;
	margin-right: auto;
}
td{
 padding-top:5px;
 padding-bottom:5px;
}
</style>
<script type="text/javascript">
  //校验表单
  function validateForm(){
	  var password=document.getElementById("password").value;
	  var repeatPassword=document.getElementById("password").value;
	  if(password!=repeatPassword){
		  alert("确认密码错误");
		  return;
	  }
	  document.getElementById("upForm").submit();
  }

</script>

</head>
<body>
	<div>
		<div style="text-align: center">

			<h3 style="text-size: 24px; padding-top: 10px; padding-bottom: 10px;">
				<c:if test="${user!=null}">修改个人信息</c:if>
			</h3>
		</div>
		<form id="upForm" action="<%=basePath%>user/updateUserDetail.action" method="POST">
			<table>

				<tr>
					<td>账号：</td>
					<td><input type="text" name="account" value="${user.account}"
						class="inputClass" readonly /></td>
				</tr>
				<tr>
					<td>姓名：</td>
					<td><input type="text" name="name" value="${person.name }" class="inputClass" /></td>
				</tr>
				<tr>
					<td>公司：</td>
					<td><input type="text" name="company"  value="${person.company }"class="inputClass" /></td>
				</tr>
				<tr>
					<td>项目组：</td>
					<td><input type="text" name="teamName" value="${person.teamName}"class="inputClass" /></td>
				</tr>
				<tr>
					<td>密码：</td>
					<td><input type="password" name="password" class="inputClass" /></td>
				</tr>
				<tr>
					<td>重复密码：</td>
					<td><input type="password" name="repeatPassword" class="inputClass" /></td>
				</tr>
				<tr>
					<td>备注：</td>
					<td><textarea rows="6" cols="43" name="tag" >${person.tag}</textarea></td>
				</tr>

				<tr>
					<td></td>
					<td><button class="submitClass" onclick="validateForm()">提交更新</button></td>
				</tr>

			</table>

		</form>

		<c:if test="${message!=null}">
			<script type="text/javascript">
				alert("${message}");
			</script>
		</c:if>

	</div>

</body>
</html>