<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Insert title here</title>

<%@ include file="/grid.jsp"%>

<script type="text/javascript"
	src="${ctx }resources/js/pages/admin/user/list.js"></script>

<style type="text/css">
.inputEl {
	width: 350px;
}

.inputLabel {
	width: 90px;
	display: block;
	float: left;
}
</style>

</head>
<body>
	<div style="padding: 20px;">
		<table id="userTable"></table>
		<div id="userPager"></div>
	</div>
	<div id="userDialog" style="display: none;">
		<input type="hidden" name="id" id="id" />
		<div>
			<label class="inputLabel">登录账号：</label><input type="text"
				id="loginName" name="loginName" class="inputEl" />
		</div>
		<div style="margin-top: 10px;" id="passwordDiv">
			<label class="inputLabel">登录密码：</label><input type="password"
				id="password" name="password" class="inputEl" />
		</div>
		<div style="margin-top: 10px;">
			<label class="inputLabel">姓氏：</label><input type="text" id="lastName"
				name="lastName" class="inputEl" />
		</div>
		<div style="margin-top: 10px; margin-bottom: 10px;">
			<label class="inputLabel">名字：</label><input type="text"
				id="firstName" name="firstName" class="inputEl" />
		</div>
	</div>
</body>
</html>