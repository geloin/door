<%@page import="com.ckeditor.CKEditorConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://ckeditor.com" prefix="ckeditor"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Insert title here</title>

<%@ include file="/resources.jsp"%>

<script type="text/javascript"
	src="${ctx }resources/js/pages/admin/article/edit.js"></script>

<script type="text/javascript">
	var channelId = '${channelId}';
</script>

<style type="text/css">
#login_tip {
	text-align: center;
}

.buttonDiv {
	text-align: center;
	padding: 20px 0;
}
</style>

</head>
<body>
	<div id="dialog-form" class="ui-widget ui-widget-content ui-corner-all"
		style="margin: 20px;">
		<p id="login_tip">编辑文章</p>
		<form id="login-form" method="post">
			<input type="hidden" name="channelId" id="channelId"
				value="${channelId }" />
			<input type="hidden" name="id" id="id" value="${article.id }" />
			<input type="hidden" name="attachments" id="attachments"
				value="${article.attachments }" />
			<fieldset>
				<table>
					<tr>
						<td>标题：</td>
						<td><input type="text" name="title" id="title"
							value="${article.title }"
							class="text ui-widget-content ui-corner-all" /></td>
					</tr>
					<tr>
						<td>所属栏目：</td>
						<c:choose>
							<c:when test="${null ne article }">
								<td>${article.channelName }</td>
							</c:when>
							<c:otherwise>
								<td id="channelSelector"></td>
							</c:otherwise>
						</c:choose>
					</tr>
					<tr>
						<td>内容：</td>
						<td><textarea rows="10" cols="50" name="content" id="content"
								class="text ui-widget-content ui-corner-all">${article.content }</textarea></td>
					</tr>
				</table>
			</fieldset>
			<div class="buttonDiv">
				<input type="button" id="btn_save" value="保存" /> <input
					type="button" id="btn_reset" value="重置" /> <input type="button"
					id="btn_cancel" value="取消" />
			</div>
		</form>
	</div>

	<%
		CKEditorConfig settings = new CKEditorConfig();
		settings.addConfigValue("width", "800");
		settings.addConfigValue("height", "400");
	%>
	<ckeditor:replace replace="content" basePath="${ctx }/ckeditor/"
		config="<%=settings %>" />
</body>
</html>