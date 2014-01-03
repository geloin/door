<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<%@ include file="/resources.jsp"%>

<title>Insert title here</title>

<script type="text/javascript">
	var parentId = '${parentId}';
</script>

<script type="text/javascript"
	src="${ctx }resources/js/pages/admin/menu/list.js"></script>

<style type="text/css">
.inputEl {
	width: 350px;
}
</style>

</head>
<body>
	<div style="padding: 20px;">
		<table id="userTable"></table>
		<div id="userPager"></div>
	</div>
	<div id="saveMenuDialog" style="display: none;" title="新增菜单">
		<input type="hidden" id="id" name="id" />
		<div>
			菜单名称：<input type="text" id="name" name="name" class="inputEl" />
		</div>
		<div style="margin-top: 10px; margin-bottom: 10px;">
			菜单地址：<input type="text" id="url" name="url" class="inputEl" />
		</div>
	</div>
</body>
</html>