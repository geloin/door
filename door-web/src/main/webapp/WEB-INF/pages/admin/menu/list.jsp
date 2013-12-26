<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Insert title here</title>
<script type="text/javascript"
	src="${ctx }resources/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript"
	src="${ctx }resources/js/jquery.paginate.js"></script>
<link rel="stylesheet" type="text/css"
	href="${ctx }resources/css/jPaginate/css/style.css" media="screen" />
<script type="text/javascript">
	$(function() {
		$("#demo1").paginate(
				{
					count : 100,
					start : 1,
					display : 8,
					border : true,
					border_color : '#fff',
					text_color : '#fff',
					background_color : 'black',
					border_hover_color : '#ccc',
					text_hover_color : '#000',
					background_hover_color : '#fff',
					images : false,
					mouse : 'press',
					onChange : function(page) {
						$('._current', '#paginationdemo').removeClass(
								'_current').hide();
						$('#p' + page).addClass('_current').show();
					}
				});
	});
</script>
</head>
<body>
	<div id="demo1"></div>
</body>
</html>