<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="door" uri="door"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<title>菜单管理</title>

<style>
.alertDialog {
	border: 5px yellow solid !important;
}

.alertDialog .ui-dialog-header {
	background-color: yellow !important;
	font-style: italic !important;
	font-size: 20px !important;
}
</style>

</head>
<body>
	<jsp:include page="../common/init.jsp" />

	<div class="panel_border">
		<div class="row-fluid">
			<div class="span6 padding-left5top5">
				<button id="newBtn" class="btn" type="button">新建</button>
				<button id="delBtn" class="btn" type="button">删除</button>
			</div>
			<div class="input-append pull-right-plus">
				<input id="searchTxt" type="text" placeholder="请输入查询条件">
					<button class="btn" id="searchBtn" type="button">检索</button>
			</div>
		</div>

		<div class="row-fluid">
			<div class="span12">
				<div id="grid"></div>
			</div>
		</div>

		<div style="width: 100%; height: 28px;">
			<div style="width: 33%; height: 28px; float: left;"></div>
			<div id="pagebar"
				style="width: 66%; height: 28px; padding-top: 3px; float: left;"></div>
		</div>
	</div>


	<script type="text/javascript">
		$(document).ready(function() {

			var url = ctx + 'menu/listJson.html';
			var editUrl = ctx + 'menu/edit.html';
			var gdialog = new GeloinDialog();

			var config = {
				pageable : true,
				columns : function() {
					var array_columns = [ {
						name : '',
						mapping : 'checkbox',
						width : 21
					}, {
						name : '菜单名称',
						mapping : 'name',
						width : 0.3
					}, {
						name : '服务地址',
						mapping : 'url',
						align : 'left',
						width : 120
					}, {
						name : '创建时间',
						mapping : 'createTime',
						align : 'center',
						width : 80
					}, {
						name : '排序号',
						mapping : 'sort',
						align : 'center',
						width : 80
					}, {
						name : '操作',
						mapping : 'buttons',
						align : 'left',
						width : 60
					} ];

					return array_columns;
				}(),
				onRowChecked : function(event) {
					buttonControl.call(this);
				},
				onCheckAll : function(event) {
					buttonControl.call(this);
				},
				onButtonClick : function(event) {
					var name = event.name;
					var data = event.data;
					switch (name) {
					case 'button_download':
						down.call(this, data);
						break;
					case 'button_edit':
						edit.call(this, data);
						break;
					case 'button_information':
						detail.call(this, data);
						break;
					case 'button_cancel':
						cancelDoc.call(this, data);
						break;
					case 'button_delete':
						deleteDoc.call(this, data);
						break;
					default:
						break;
					}
				}
			};

			function addButtons2Data(data) {
				$(data).each(function(i, n) {

					//编辑按钮
					var button_edit = {
						cls : 'button_edit',
						value : '编辑'
					};
					//详情按钮
					var button_information = {
						cls : 'button_information',
						value : '详情'
					};
					//删除按钮
					var button_delete = {
						cls : 'button_delete',
						value : '删除'
					};

					var buttonControls = new Array();

					n['buttons'] = buttonControls;
				});
			}

			function buttonControl() {
				var checks = grid.getCheckedDatas();
			}

			var data = '${data}' || '';
			var total;
			if (data && data != '') {
				data = $.parseJSON(data);
				total = data.count;
				data = data.data;
			}
			var grid = $('#grid').ggrid(config);
			grid.display();
			addButtons2Data.call(this, data);
			grid.load(data);
			
			var pageSize = 2;
			var page = new Pagebar({
				pageSize : pageSize,
				total : total,
				currentPage : 1,
				node:"#pagebar",
				goFunction:function(pageIndex){
					console.log('1111');
				}
			});
			page.render();

			$('#newBtn').bind('click', function() {
				var url = ctx + "menu/dispatch.html?page=edit";
				gdialog.open(url, '新增菜单', {
					width : 600,
					height : 150,
					ok : function() {
						var iframe = this.iframeNode.contentWindow;
						if (iframe.addValidateObj && iframe.addValidateObj.form()) {
							var obj = iframe.getParams();
							
							$.ajax({
								url : ctx + 'menu/edit.html',
								data : obj,
								type : 'post',
								success : function(data) {
									data = $.parseJSON(data);
									if (data.success) {
										return true;
									} else {
										gdialog.alert(data.msg);
										return false;
									}
								}
							});
						}
					}
				});
			});

		});
	</script>
</body>
</html>