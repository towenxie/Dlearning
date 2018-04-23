<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查询IP</title>

<style>
.find_center{
    text-align:center;
    margin:0 auto;
     width: 60%; 
  height: 60%;
  min-width: 200px;
  max-width: 400px;
  padding: 40px;
  padding-bottom:0;
}
}
</style>

<script type="text/javascript"
 src="${pageContext.request.contextPath}/js/jquery-1.10.2.min.js">  </script> 

<script type="text/javascript">
    $(document).ready(function() { 
        $("#button_submit").click(function() { 
            var name = $("#ip").val(); 

            $.ajax({ 
                type : "POST", 
                url : "${pageContext.request.contextPath}/deeplearning/IP/getMetaIp/ip",
                data : {ip:name}, 
                success : function(data) {
                	$("#result").html(data); 
//                 	$("#errormsg").html("成功").show(300).delay(3000).hide(300); 
                }, 
                error : function(e) { 
                    alert(); 
//                     $("#errormsg").html("出错：" + e).show(300).delay(3000).hide(300); 
                } 
            }); 
        }); 
    }); 
 </script> 

</head>

<body>
<div>
    <label id="errormsg">  </label>
    </div>
    <div>
    <div class="find_center">
	<form>
		<table>
			<tr>
				<td>查询IP：</td>
				<td><input type="text" id="ip" name="ip"></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td><input type="button" id="button_submit" value="提交">
				</td>
			</tr>
		</table>
	</form>
    </div>
            <div>
            <label id="result"></label>
        </div>
        </div>

</body>
</html>