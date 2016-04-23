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
<title><c:if test="${platform =='android'}">Android 应用上传</c:if><c:if test="${platform =='ios'}">IOS 应用上传</c:if></title>
<style type="text/css">
   .submitClass{
   width:300px; 
    height:30px;
   }
   .inputClass{
   width:300px;
   height:20px;
   
   }
     table{
   margin-left: auto;
	margin-right: auto;
}
 </style>
<script type="text/javascript">
function upload(){
    document.getElementById("uploadForm").submit();  
  }
</script>
</head>
<body>  
<div>
	<div style="text-align: center">

		<h3 style="text-size: 24px; padding-top: 10px; padding-bottom: 10px;"><c:if test="${platform =='android'}">Android 应用上传</c:if><c:if test="${platform =='ios'}">IOS 应用上传</c:if></h3>
	</div>
	<form id="uploadForm" action="<%=basePath%>app/upload.action" method="POST" enctype="multipart/form-data">
	<table>
	 
	 <tr><td>名字</td><td><input type="text" name="appName" value="${app.appName}" class="inputClass"/></td></tr>
	 <tr><td>版本</td><td><input type="text" name="versionCode" value="${app.versionCode}"   class="inputClass" disabled/></td></tr>
	  <tr><td>包名</td><td><input type="text" name="packageName" value="${app.packageName}"   class="inputClass"disabled/></td></tr>
	 <tr><td>应用文件</td><td><input type="file" name="apk" <c:if test="${platform =='android'}">accept=".apk"</c:if><c:if test="${platform =='ios'}">accept=".ipa"</c:if>/></td></tr>
	 <tr><td>映射别名</td><td><input type="text" name="mappingUrl" value="${app.mappingUrl}"  class="inputClass"/></td></tr>
	 <tr><td>版本说明</td><td><textarea rows="6" cols="43" name="log">${app.log}</textarea></td></tr>
	 <tr><td></td><td><button class="submitClass">提交更新</button></td></tr>
	
	</table>
	
	</form>
	
	<c:if test="${success==true}">
	    <script type="text/javascript">
	    alert("恭喜,提交成功");
	    </script>
	</c:if>
	
</div>
</body>
</html>