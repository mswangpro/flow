<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
    //context path
    String contextPath = request.getScheme() + "://"+request.getServerName() + ":" +request.getServerPort()+request.getContextPath();
    request.setAttribute("contextPath",contextPath);
%>
<head>
<script src="${contextPath}/resources/3rd-js/jquery/jquery-1.11.3.js"></script>
</head>
<body>
	<form id="userForm">
	<input type="text" name="entity.id"/><br/>
	<input type="text" name="entity.name"/><br/>
	<input type="text" name="entity.age"/><br/>
	</form>
	<br/>
	<br/>
	<button class="submit">测试</button>
	<script type="text/javascript">
	$.extend({
		serializeArray : function(array, listName){
			var text = "";
			for (var i = 0; i < array.length; i++) {
				for (key in array[i]) {
					var subObj = array[i][key];
					if (typeof subObj == 'object') {
						for (o in subObj) {
							text += listName + "[" + i + "]." + key + "." + o + "="
									+ subObj[o] + "&";
						}
					} else {
						text += listName + "[" + i + "]." + key + "="
								+ array[i][key] + "&";
					}
				}
			}
			return text.substring(0, text.length - 1);
		}
	});
	var hahas = [{id:1,name:1,age:2},{id:3,name:3,age:2}];
	$(".submit").on("click",function () {
		$.ajax({
            url: "login!loginSubmit.action",
            type: "POST",
            dataType:"json",
            //data: $('#userForm').serialize(),
            data: $.serializeArray(hahas,"hahas"),
            success: function(data) {
            	console.log(data);
            },
            error: function() {
            }
        });
	});
	</script>
</body>
</html>
