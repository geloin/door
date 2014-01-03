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
					if (menu.url != '#') {
						o.url = '${ctx}' + menu.url;
						o.target = 'contentIFrame';
					}
					o.open = true;
					
					zNodes[n] = o;
				});
				
				var setting = {
					data : {
						simpleData : {
							enable : true
						}
					},
					view : {
						dbClickExpand: false,
						showLine : false
					},
					callback: {
						onClick : onClick
					}
				};

				$(document).ready(function() {
					$.fn.zTree.init($("#treeDemo"), setting, zNodes);
				});
				
				function onClick(e,treeId, treeNode) {
					var zTree = $.fn.zTree.getZTreeObj("treeDemo");
					zTree.expandNode(treeNode);
				}

			} else {
				alert(json.msg);
			}
		});
	</script>
</head>
<body>
	<div style="padding: 20px;">
		<ul id="treeDemo" class="ztree"></ul>
	</div>
</body>
</html>