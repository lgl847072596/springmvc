 var box ;
 var showDiv;
window.onload=function(){
	
	 box=document.getElementById('drop_area'); //拖拽区域 
	 showDiv=document.getElementById("preview");
	box.ondragover=function(e){
		e.preventDefault();
	}
	box.ondrop=function(e){
		e.preventDefault();
		 var fileList = e.dataTransfer.files; //获取文件对象 
        //检测是否是拖拽文件到页面的操作 
        if(fileList.length == 0){ 
            return false; 
        } 
        //检测文件是不是图片 
       if(fileList[0].name.indexOf('apk') === -1){ 
          alert("您拖的不是apk文件！"); 
          return false; 
      } 
         
        //拖拉图片到浏览器，可以实现预览功能 
       // var img = window.webkitURL.createObjectURL(fileList[0]); 
        var filename = fileList[0].name; //图片名称 
        var filesize = Math.floor((fileList[0].size)/1024);  
		
       document.getElementById("preview").innerHTML="filename="+filename+"<br/>filesize="+filesize+"KB"; 
         
        //上传  
       uploadFile(fileList[0]);
	}
	
	//XmlHttpRequest对象    
	function createXmlHttpRequest(){    
	    if(window.ActiveXObject){ //如果是IE浏览器    
	        return new ActiveXObject("Microsoft.XMLHTTP");    
	    }else if(window.XMLHttpRequest){ //非IE浏览器    
	        return new XMLHttpRequest();    
	    }    
	}   
	
	  function uploadFile(file) {
        var fd = new FormData();
        fd.append("apk", file);
        var xhr = createXmlHttpRequest();
        //2.设置回调函数    
        xhr.onreadystatechange =zswFun(xhr); 
        xhr.open("POST", BASE_URL+"/app/public/uploadApk.json",true);
        xhr.send(fd);
      }

	//回调函数    
	  function zswFun(httpRequest){    
		  
		  if (httpRequest.readyState == 4 && httpRequest.status == 200) {  
              var resultValue = httpRequest.responseText;  
              //表示初始化上传时,显示进度是0%  
              if (resultValue == "") {  
            	  showDiv.innerHTML = "上传进度：0%";  
                  return;  
              }  

              showDiv.innerHTML = "上传进度：" + resultValue + "%";  
              //当result为100时,不再进行进度条的更新  
              if (resultValue == 100) {  
                  //自动消失  
                  showDiv.innerHTML = "上传进度：100% " + "上传已完成!";  
              }  
          }  
	  }  
	  
      function uploadProgress(evt) {
        if (evt.lengthComputable) {
          var percentComplete = Math.round(evt.loaded * 100 / evt.total);
          document.getElementById("preview").innerHTML = percentComplete.toString() + '%';
        }
        else {
           document.getElementById("preview").innerHTML = 'unable to compute';
        }
      }

      function uploadComplete(evt) {
        /* This event is raised when the server send back a response */
        alert(evt.target.responseText);
      }

      function uploadFailed(evt) {
        alert("There was an error attempting to upload the file.");
      }

      function uploadCanceled(evt) {
        alert("The upload has been canceled by the user or the browser dropped the connection.");
      }
	
   
  
}