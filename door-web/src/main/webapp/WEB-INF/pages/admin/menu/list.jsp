<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resources.jsp"%>
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
	src="${ctx }resources/js/jquery-migrate-1.2.1.min.js"></script>
<script type="text/javascript"
	src="${ctx }resources/js/jquery.alerts.js"></script>

<link rel="stylesheet" type="text/css"
	href="${ctx }resources/css/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx }resources/css/smoothness/jquery-ui-1.10.3.custom.min.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx }resources/css/jalert/jquery.alerts.css" />


<style type="text/css">
.inputEl {
	width: 350px;
}
</style>

<script type="text/javascript">
	$(function() {
		$("#userTable")
				.jqGrid(
						{
							url : '${ctx}admin/menu/listJson.html',
							postData : {
								'parentId' : '${parentId}'
							},
							datatype : "json",
							colNames : [ '序号', '名称', '地址', '排序', '操作' ],
							colModel : [ {
								name : 'id',
								index : 'id',
								width : 55,
								align : "center",
								editable : false,
								sortable : true
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
							}, {
								name : 'opt',
								index : 'opt',
								width : 100,
								editable : false,
								align : 'center',
								formatter : showChildren
							} ],
							rowNum : 10,
							rowList : [ 10, 20, 30 ],
							pager : '#userPager',
							sortable : true,
							sortname : 'id',
							autowidth : true,
							viewrecords : true,
							sortorder : "asc",
							multiselect : true,
							loadComplete : function(data) {
								if (data.length == 0) {
									$(
											"<tr><td colspan='5'><p style='text-align: center'>无数据显示</p><td></tr>")
											.appendTo($("#userTable"));
								}
								var userData = $(this).getGridParam('userData');
								if (JSON.stringify(userData) != '{}') {
									$(
											"<tr><td colspan='5'><div style='font-size: 20px; color: red;'>异常："
													+ userData
													+ "</div><td></tr>")
											.appendTo($("#userTable"));
								}

								changeButtonDisplay(new Array('add_userTable'),
										'');
								changeButtonDisplay(
										new Array('edit_userTable'), 'none');
							},
							onSelectRow : function(rowid, status) {
								var gr = $("#userTable").jqGrid('getGridParam',
										'selarrrow');
								if (gr.length != 0) {
									changeButtonDisplay(new Array(
											'add_userTable'), 'none');
								} else {
									changeButtonDisplay(new Array(
											'add_userTable'), '');
								}
								if (gr.length != 1) {
									changeButtonDisplay(new Array(
											'edit_userTable'), 'none');
								} else {
									changeButtonDisplay(new Array(
											'edit_userTable'), '');
								}
							},
							onSelectAll : function(aRowids, status) {
								var gr = $("#userTable").jqGrid('getGridParam',
										'selarrrow');
								if (gr.length != 0) {
									changeButtonDisplay(new Array(
											'add_userTable'), 'none');
								} else {
									changeButtonDisplay(new Array(
											'add_userTable'), '');
								}
								if (gr.length != 1) {
									changeButtonDisplay(new Array(
											'edit_userTable'), 'none');
								} else {
									changeButtonDisplay(new Array(
											'edit_userTable'), '');
								}
							}
						});
		$("#userTable").jqGrid("navGrid", "#userPager", {
			addfunc : addRow,
			editfunc : editRow,
			delfunc : deleteRow,
			searchfunc : searchRow,
			alerttext : "请选择需要操作的数据行!"
		}).jqGrid('navButtonAdd', "#userPager", {
			caption : '返回上一级',
			buttonicon : 'none',
			onClickButton : showParent,
			id : 'to_parent'
		});

		function changeButtonDisplay(ids, display) {
			$.each(ids, function(i, id) {
				$('#' + id).css('display', display);
			});
		}

		function showParent() {
			var o = {};
			o.id = '${parentId}';
			$
					.post(
							'${ctx}admin/menu/findById.html',
							o,
							function(data) {
								var json = $.parseJSON(data);
								if (json.parent) {
									window.location.href = '${ctx}admin/menu/list.html?parentId='
											+ json.parent.id;
								} else {
									jAlert('已经是最顶级', '提示');
								}
							});
		}

		function addRow() {
			openSaveDialog();
		}

		function searchRow() {
			openSearchDialog();
		}

		function editRow() {
			var gr = $("#userTable").jqGrid('getGridParam', 'selrow');
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
					openSaveDialog();
				});
			}
		}
		function deleteRow() {
			var gr = $("#userTable").jqGrid('getGridParam', 'selarrrow');
			if (gr != null) {
				var url = '${ctx}admin/menu/delete.html?';
				$.each(gr, function(i, val) {
					url += 'ids=' + val + '&';
				});
				url = url.substring(0, url.length - 1);
				$.post(url, function() {
					$('#userTable').trigger('reloadGrid');
					jAlert('删除成功', '提示');
					reloadMenuIFrame();
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
				$("#userTable").trigger("reloadGrid");
				jAlert('保存成功', '提示');
				reloadMenuIFrame();
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

		function openSaveDialog() {
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

		function openSearchDialog() {
			$("#saveMenuDialog").dialog({
				autoOpen : true,
				modal : true,
				resizable : true,
				width : 500,
				height : 250,
				position : "center",
				buttons : {
					'查询' : function() {
						var name = $('#name').val();
						var url = $('#url').val();
						$("#userTable").jqGrid('setGridParam', {
							url : '${ctx}admin/menu/listJson.html',
							postData : {
								'name' : name,
								'url' : url
							},
							page : 1
						}).trigger("reloadGrid");
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

		function reloadMenuIFrame() {
			var menuIFrame = $('#menuIFrame', window.parent.document);
			menuIFrame.attr({
				src : '${ctx}admin/index/menu.html'
			});
		}

		function showChildren(cellvalue, options, rowObject) {
			var url = '${ctx}admin/menu/list.html?parentId=' + rowObject.id;
			return '<a href="' + url + '">子菜单管理</a>';
		}

	});
</script>

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