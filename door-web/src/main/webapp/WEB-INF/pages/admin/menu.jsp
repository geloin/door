<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Insert title here</title>
	<link rel="stylesheet"
		href="${ctx }resources/css/zTreeStyle/zTreeStyle.css" type="text/css" />
	<script type="text/javascript"
		src="${ctx }resources/js/jquery-1.10.2.min.js"></script>
	<script type="text/javascript"
		src="${ctx }resources/js/jquery.ztree.core-3.5.min.js"></script>
	<script type="text/javascript">
		var url = '${ctx}admin/index/menuTree.html';
		$.post(url, function(data, status) {
			var json = $.parseJSON(data);
			if (json.success) {

				var zNodes = new Array();
				
				var menuTree = json.data;
				$.each(menuTree, function(n, menu) {
					var o = {};
					o.id = menu.id;
					if (menu.parent) {
						o.pId = menu.parent.id;
					} else {
						o.pId = 0;
					}
					o.name = menu.name;
					o.url = menu.url;
					o.open = true;
					o.target = 'contentIFrame';
					
					zNodes[n] = o;
				});
				
				var setting = {
					data : {
						simpleData : {
							enable : true
						}
					}
				};

				$(document).ready(function() {
					$.fn.zTree.init($("#treeDemo"), setting, zNodes);
				});

			} else {
				alert(json.msg);
			}
		});
	</script>
</head>
<body>
	<ul id="treeDemo" class="ztree"></ul>
</body>
</html>