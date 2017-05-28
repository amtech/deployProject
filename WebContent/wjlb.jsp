<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>


<!doctype html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>文件上传服务器</title>
		<script type="text/javascript" src="js/jquery-3.2.1.min.js"></script>
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
    function show111(){  
        //开始初始化XMLHttpRequest对象  
        //除IE5 IE6 以外的浏览器XMLHttpRequest是window的子对象
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

        //通过GET方式打开连接  
        ajax.open("POST", "showUploaded.do", true);  

        ajax.setRequestHeader("Content-Type","application/x-www-form-urlencoded");  
        //发送POST数据  
        ajax.send(null);  
        //获取执行状态  
        ajax.onreadystatechange = function(){   
            //如果执行状态成功，那么就把返回信息写到指定的层里  
            if (ajax.readyState == 4 && ajax.status == 200)   
            {   
                document.getElementById("uploaded").innerHTML += "<tr>"+ajax.responseText+"</tr>";   
            }   
        }

        /* 等待五秒后再次请求数据 */
      //  window.setTimeout("show()", 5000);  
    }      
    
    
    
    
</script>
</head>
	
	
	
	
		</script>
		
	</head>
	
	<body>
	
		
		
		
		<table id="uploaded" name="uploaded" >
		<tr>
			<td id="uploadedXh" value="1">${uploadedNo }</td>
		
			<td id="uploadedNo">${uploadedNo }</td>
		</tr>
		
	</table>
	
	
	
	</body>

</html>