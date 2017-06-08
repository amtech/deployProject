<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<%String path = request.getContextPath();%>

<!doctype html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		
		<title>文件上传服务器</title>
		<script type="text/javascript" src="js/jquery-3.2.1.min.js"></script>
		<script >
			encodingURI(encodingURI());
		</script>
		<script type="text/javascript">
			function checkForm(form){
			var flag=validateFile();
			if(!flag){
				alert("您还没有选择上传文件");
				return false;
			}else{
				return true;
			}
		}
	function validateFile(){
		var tmp=document.getElementById("file1").value;
	//	var tmpStr=tmp.IndexOf("//");
		if(tmp==null||tmp==""){
			return false;
		}else{
			return true;
		}
	}
	
 //  window.onload=
	 /*
	   function show(){  
        //开始初始化XMLHttpRequest对象  
        //除IE5 IE6 以外的浏览器XMLHttpRequest是window的子对象
        var ajax;
        if(window.XMLHttpRequest)   
        {   //Mozilla 浏览器  
            ajax = new XMLHttpRequest();  
            if (ajax.overrideMimeType)   
            {   //设置MiME类别  
                ajax.overrideMimeType("text/xml");  
            }  
        }  
        //IE5 IE6是以ActiveXObject的方式引入XMLHttpRequest的 
        else if (window.ActiveXObject)   
        {   // 新版本IE浏览器  
            try   
            {  
                ajax = new ActiveXObject("Msxml2.XMLHTTP");  
            }   
            catch (e)   
            {  //兼容老版本浏览器
                try   
                {  
                    ajax = new ActiveXObject("Microsoft.XMLHTTP");  
                }   
                catch (e) {
                    alert(e.message);
                }  
            }  
        }  
        if (!ajax)   
        {   // 异常，创建对象实例失败  
            window.alert("不能创建XMLHttpRequest对象实例.");  
            return false;  
        }  

        //通过POST方式打开连接  
        ajax.open("POST", "showUploaded.do", true);  

        ajax.setRequestHeader("Content-Type","test/html;charset=UTF-8");  
        
        //发送POST数据  
        ajax.send(null);  
        //获取执行状态  
        ajax.onreadystatechange = function(){   
            //如果执行状态成功，那么就把返回信息写到指定的层里  
            if (ajax.readyState == 4 && ajax.status == 200)   
            {   
            	 //	alert("成功");
                            var user = ajax.response;  
                     //       alert(user);
                        //    user = eval(user); //这里需注意，后台返回的是纯文本数据而不是json数据，要用eval方法转换成json格式的数据  
                    //利用后台返回的json数据自动创建节点，采用拼接字符串的方法
                    		
                    		$('#uploaded').append(user);  
                    		var s=encodingURI(encodingURI(user));
                        	alert(s);
                             
            }   
        }

        /* 等待五秒后再次请求数据 */
      //  window.setTimeout("show()", 5000);  
   // }  
	//*/
	
	
    /*
    function show11(){
    	$.ajax({
	        type:"POST",  
	        url:"showUploaded.do",  
	        dataType: "json", 
	        error:function(data){  
	              alert("连接超时");  
	          },  
	           success:function(data){  
	        	   alert("成功了");
	               $("#uploaded").html(data);
	              }
	       });
    }
    */
    
</script>
</head>
	</script>
</head>
<body>
	<form style="padding-left:40%;"name="demo" action="doUpload.do" enctype="multipart/form-data" method="POST" >
		<div id="J_TabCase">
			<input type="file" align="center" name="file1" id="file1" >
			<input type=submit align="center" name="submit1" id="jia" value="上传" onClick="return checkForm(this.form);"/>
		</div>
	</form>
	<div align="center">	
		<table id="uploaded" name="uploaded" border=1>
		<tr>
			<th id="uploadedXh" value="1">序号</td>
		
			<th id="uploadedNo">上传文件列表</td>
			<th id="lastModifyTime">最新修改时间</td>
		</tr>
		 <a>${name }</a>
	</table>
	</div>
</body>

</html>