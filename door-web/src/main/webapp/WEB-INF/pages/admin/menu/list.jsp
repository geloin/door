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
	src="${ctx }resources/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript"
	src="${ctx }resources/js/grid.locale-cn.js"></script>
<script type="text/javascript"
	src="${ctx }resources/js/jquery-ui-1.10.3.custom.min.js"></script>
<script type="text/javascript"
	src="${ctx }resources/js/jquery.alerts.js"></script>
<script type="text/javascript"
	src="${ctx }resources/js/jquery-migrate-1.2.1.min.js"></script>

<link rel="stylesheet" type="text/css"
	href="${ctx }resources/css/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx }resources/css/smoothness/jquery-ui-1.10.3.custom.min.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx }resources/css/jalert/jquery.alerts.css" />

<script type="text/javascript">
	$(function() {
		$("#list2").jqGrid({
			url : '${ctx}admin/menu/listJson.html',
			datatype : "json",
			colNames : [ '序号', '名称', '地址', '排序' ],
			colModel : [ {
				name : 'id',
				index : 'id',
				width : 55,
				align : "center",
				editable : false
			}, {
				name : 'name',
				index : 'name',
				width : 90,
				editable : true
			}, {
				name : 'url',
				index : 'url',
				width : 90,
				editable : true
			}, {
				name : 'sort',
				index : 'sort',
				width : 100,
				editable : false
			} ],
			rowNum : 10,
			pager : '#pager2',
			sortname : 'id',
			autowidth : true,
			viewrecords : true,
			sortorder : "desc",
			multiselect : true
		});
		$("#list2").jqGrid("navGrid", "#pager2", {
			addfunc : openDialog4Adding,
			editfunc : openDialog4Updating,
			delfunc : openDialog4Deleting,
			alerttext : "请选择需要操作的数据行!"
		});

		function openDialog4Adding() {
			openDialog();
		}

		function openDialog4Updating() {
			var gr = jQuery("#list2").jqGrid('getGridParam', 'selrow');
			if (gr != null) {
				var o = {};
				o.id = gr;
				$.post('${ctx}admin/menu/findById.html', o, function(data) {
					var json = $.parseJSON(data);
					$('#id').attr({
						value : json.id
					});
					$('#name').attr({
						value : json.name
					});
					$('#url').attr({
						value : json.url
					});
					openDialog();
				});
			}
		}
		function openDialog4Deleting() {
			var gr = jQuery("#list2").jqGrid('getGridParam', 'selarrrow');
			if (gr != null) {
				var url = '${ctx}admin/menu/delete.html?';
				$.each(gr, function(i, val) {
					url += 'ids=' + val + '&';
				});
				url = url.substring(0, url.length - 1);
				$.post(url, function() {
					$('#list2').trigger('reloadGrid');
					jAlert('删除成功', '提示');
				});
			}
		}

		function save() {
			var o = {};
			o.parentId = '${parentId}';
			o.id = $('#id').val() || 0;
			o.name = $('#name').val();
			o.url = $('#url').val();
			$.post('${ctx}admin/menu/save.html', o, function(data) {
				$("#list2").trigger("reloadGrid");
				jAlert('添加成功', '提示');
			});
		}

		function reset() {
			$('#name').attr({
				value : ''
			});
			$('#url').attr({
				value : ''
			});
		}

		function openDialog() {
			$("#saveMenuDialog").dialog({
				autoOpen : true,
				modal : true,
				resizable : true,
				width : 500,
				height : 250,
				position : "center",
				buttons : {
					'保存' : function() {
						save();
						$(this).dialog('close');
					},
					'重置' : function() {
						reset();
					},
					'取消' : function() {
						reset();
						$(this).dialog('close');
					}
				},
				close : function() {
					reset();
				}
			});
		}

		function touchAlert(message, title) {
			if (!title) {
				title = '警告';
			}
			$('#alertDialog').html(message);
			$('#alertDialog').dialog({
				autoOpen : true,
				resizable : true,
				title : title,
				position : 'center',
				buttons : {
					'关闭' : function() {
						$('#alertDialog').html('');
						return true;
					}
				}
			});
			return false;
		}
	});
</script>
<style type="text/css">
.inputEl {
	width: 350px;
}
</style>
</head>
<body>
	<div style="padding: 20px;">
		<table id="list2"></table>
		<div id="pager2"></div>
	</div>
	<div id="saveMenuDialog" style="display: none" title="新增菜单">
		<input type="hidden" id="id" name="id" />
		<div>
			菜单名称：<input type="text" id="name" name="name" class="inputEl" />
		</div>
		<div style="margin-top: 10px; margin-bottom: 10px;">
			菜单地址：<input type="text" id="url" name="url" class="inputEl" />
		</div>
	</div>
	<div id="alertDialog"></div>
</body>
</html>