<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查询IP</title>
<link rel="stylesheet"
    href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet"
    href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script type="text/javascript">
	document.onkeydown = function(e) {
		var theEvent = window.event || e;
		var code = theEvent.keyCode || theEvent.which;
		if (code == 13) {
			$("#button_submit").click();
		}
	}
	$(document)
			.ready(
					function() {
						//$('#warning-block').hide();
						$("#button_submit")
								.click(
										function() {
											var name = $("#ip").val();
											$
													.ajax({
														type : "POST",
														url : "${pageContext.request.contextPath}/deeplearning/IP/getMetaIp/ip",
														data : {
															ip : name
														},
														success : function(data) {
															$("#result").html(
																	data);
															$("#warning-block")
																	.addClass(
																			"alert-success");
															$("#warning-block")
																	.html(
																			"查询完成");
															$("#warning-block")
																	.slideDown()
																	.delay(3000)
																	.slideUp();

															//                 	$("#errormsg").html("成功").show(300).delay(3000).hide(300); 
														},
														error : function(e) {
															$("#warning-block")
																	.addClass(
																			"alert-danger");
															$("#warning-block")
																	.html("出错：" + e);
															$("#warning-block")
																	.slideDown()
																	.delay(3000)
																	.slideUp();
															//                     alert(); 
															//                     $("#errormsg").html("出错：" + e).show(300).delay(3000).hide(300); 
														}
													});
										});
					});
</script>
</head>
<body>
    <div class="content">
        <div style="height: 51px">
            <div class="alert alert-block" id="warning-block">
                <p id="status"></p>
            </div>
        </div>
        <div class="container"
            style="height: 450px; margin-top: 70px; margin-left: auto; margin-right: auto; background-color: #eee;">
            <h1 class="navbar-text"
                style="text-align: center; float: center; margin-bottom: 40px;">查询
                IP：</h1>
            <form class="navbar-form navbar-center"
                style="text-align: center; float: center;">
                <div class="form-group">
                    <input type="text" id="ip" class="form-control"
                        style="width: 326px;" placeholder="请输入IP">
                </div>
                <button type="button" id="button_submit"
                    class="btn btn-default">提交</button>
            </form>
            <p id="result" class="navbar-text"
                style="text-align: center; float: center;"></p>
        </div>
    </div>
</body>
</html>