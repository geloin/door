<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Insert title here</title>

<%@ include file="/grid.jsp"%>

<script type="text/javascript"
	src="${ctx }resources/js/pages/admin/article/list.js"></script>

<script type="text/javascript">
	var channelId = '${channelId}';
</script>

<style type="text/css">
.inputEl {
	width: 350px;
}

.inputLabel {
	width: 90px;
	display: block;
	float: left;
}

.selectorClass {
	height : 35px;
}

#btn_filter {
	margin-left: 20px;
}
</style>

</head>
<body>
	<div style="padding: 20px; padding-bottom: 0;" id="channelSelector">
		<input type="button" id="btn_filter" value="过滤" />
	</div>
	<div style="padding: 20px;">
		<table id="userTable"></table>
		<div id="userPager"></div>
	</div>
</body>
</html>