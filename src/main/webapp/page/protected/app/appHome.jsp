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
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/horizontal_menu.css" />
<script src="<%=basePath%>js/jquery-2.2.3.min.js"></script>
    <script type="text/javascript">
        //导航栏选中监听
    	$(function () {
            $(".ue-bar-nav li").click(function () {
                $("li[class='active']").removeAttr("class");
                $(this).addClass("active");
            });
        });        
    </script>
</head>

<body>
   <c:if test="${not empty user }">
   
    <div class="ue-bar">
            <div class="ue-bar-nav">
                <ul>             
                    <c:if test="${user.roleLevel>0}">
                    <li ><a href="<%=basePath%>app/goToUpLoad.action?platform=android" target="iframepage"><em>Android 应用上传</em></a></li>
                    </c:if>
                    <li class="active"><a href="<%=basePath%>app/applist.action?platform=android" target="iframepage"><em>Android 应用列表</em></a></li>
                   <c:if test="${user.roleLevel>0}">
                    <li><a href="<%=basePath%>app/goToUpLoad.action?platform=ios" target="iframepage"><em>IOS 应用上传</em></a></li>
                   </c:if>
                    <li><a href="<%=basePath%>app/applist.action?platform=ios" target="iframepage"><em>IOS 应用列表</em></a></li>
                    <c:if test="${user.roleLevel>1}">
                    <li><a href="<%=basePath%>user/findNoCheckUser.action" target="iframepage"><em>审核用户信息</em></a></li>
                    </c:if>
                    <li><a href="<%=basePath%>user/findUserDetail.action" target="iframepage"><em>个人信息</em></a></li>
                    <li><a href="<%=basePath%>user/public/logout.action"><em>退出</em></a></li>                   
                </ul>
      	  </div>

    </div>
  
	</c:if>

       <iframe src="<%=basePath%>app/applist.action?platform=android" 
	        id="iframepage" name="iframepage"
			frameBorder=0 scrolling=no width="100%" onLoad="iFrameHeight()"></iframe> 
	<script type="text/javascript">
		 //iframe自适应高度
		function iFrameHeight() {
			var ifm = document.getElementById("iframepage");
			var subWeb = document.frames ? document.frames["iframepage"].document
					: ifm.contentDocument;
			if (ifm != null && subWeb != null) {
				ifm.height = subWeb.body.scrollHeight;
			}
		}
	
	</script>  
</body>

</html>