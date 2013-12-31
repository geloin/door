<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<script type="text/javascript"
	src="${ctx }resources/js/jquery-1.10.2.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Insert title here</title>
<style type="text/css">
body {
	width: 98%;
	height: 98%;
	margin: 0 auto;
}

#bodyDiv {
	width: 100%;
	height: 100%;
}

#topBanner {
	width: 100%;
	height: 80px;
}

#menuDiv {
	width: 20%;
	height: 100%;
	float: left;
}

#contentDiv {
	width: 79%;
	height: 100%;
	float: right;
}
</style>

<script type="text/javascript">
function reinitIframe(){
	var menuIFrame = document.getElementById("menuIFrame");
	var contentIFrame = document.getElementById("contentIFrame");
	try{
		var menuIFrameHeight = contentIFrame.contentWindow.document.body.scrollHeight;
		var menuIFrameHeight = contentIFrame.contentWindow.document.documentElement.scrollHeight;
		var menuIFrameHeight = Math.max(menuIFrameHeight, menuIFrameHeight);
		menuIFrame.height =  menuIFrameHeight;
		
		var contentIFrameHeight = contentIFrame.contentWindow.document.body.scrollHeight;
		var contentIFrameHeight = contentIFrame.contentWindow.document.documentElement.scrollHeight;
		var contentIFrameHeight = Math.max(contentIFrameHeight, contentIFrameHeight);
		contentIFrame.height =  contentIFrameHeight;
		
	}catch (ex){}
}
window.setInterval("reinitIframe()", 500);
</script>

</head>
<body>
	<div id="bodyDiv">
		<div id="topBanner"></div>
		<div>
			<div id="menuDiv">
				<iframe id="menuIFrame" name="menuIFrame" align="left"
					frameborder="0" marginheight="0" marginwidth="0" scrolling="auto"
					src="${ctx }admin/index/menu.html" width="100%" height="100%"></iframe>
			</div>
			<div id="contentDiv">
				<iframe id="contentIFrame" name="contentIFrame" align="left"
					frameborder="0" marginheight="0" marginwidth="0" scrolling="auto"
					width="100%" height="100%"></iframe>
			</div>
		</div>
	</div>
</body>

</html>