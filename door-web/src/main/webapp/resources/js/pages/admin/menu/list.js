$(function() {
	$("#userTable")
			.jqGrid(
					{
						url : ctx + 'admin/menu/listJson.html',
						postData : {
							'parentId' : parentId
						},
						caption : '菜单管理',
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
							editable : true,
							sortable : true
						}, {
							name : 'url',
							index : 'url',
							width : 90,
							editable : true,
							sortable : true
						}, {
							name : 'sort',
							index : 'sort',
							width : 100,
							editable : false,
							sortable : true
						}, {
							name : 'opt',
							index : 'opt',
							width : 100,
							editable : false,
							sortable : true,
							align : 'center',
							formatter : showChildren
						} ],
						rowNum : 10,
						rowList : [ 10, 20, 30 ],
						pager : '#userPager',
						sortable : true,
						sortname : 'sort',
						autowidth : true,
						viewrecords : true,
						height : 400,
						sortorder : "asc",
						multiselect : true,
						loadComplete : function(data) {
							if (data.rows && data.rows.length == 0) {
								$(
										"<tr><td colspan='6' align='center' style='font-size: 20px; color:red;'>无数据显示<td></tr>")
										.appendTo($(this));
							}
							if (data.userdata != null) {
								$(
										"<tr><td colspan='6' style='font-size: 20px; color : red;'>异常："
												+ data.userdata + "<td></tr>")
										.appendTo($(this));
							}

							changeButtonDisplay(new Array('add_userTable'), '');
							changeButtonDisplay(new Array('edit_userTable'),
									'none');
						},
						onSelectRow : function(rowid, status) {
							var gr = $(this).getGridParam('selarrrow');
							if (gr.length != 0) {
								changeButtonDisplay(new Array('add_userTable'),
										'none');
							} else {
								changeButtonDisplay(new Array('add_userTable'),
										'');
							}
							if (gr.length != 1) {
								changeButtonDisplay(
										new Array('edit_userTable'), 'none');
							} else {
								changeButtonDisplay(
										new Array('edit_userTable'), '');
							}
						},
						onSelectAll : function(aRowids, status) {
							var gr = $(this).getGridParam('selarrrow');
							if (gr.length != 0) {
								changeButtonDisplay(new Array('add_userTable'),
										'none');
							} else {
								changeButtonDisplay(new Array('add_userTable'),
										'');
							}
							if (gr.length != 1) {
								changeButtonDisplay(
										new Array('edit_userTable'), 'none');
							} else {
								changeButtonDisplay(
										new Array('edit_userTable'), '');
							}
						}
					});
	$("#userTable").jqGrid("navGrid", "#userPager", {
		addfunc : addRow,
		editfunc : editRow,
		delfunc : deleteRow,
		searchfunc : searchRow,
		alerttext : "请先选择数据再操作!"
	}).jqGrid('navButtonAdd', "#userPager", {
		caption : '返回上一级',
		buttonicon : 'none',
		onClickButton : showParent,
		id : 'to_parent'
	}).navButtonAdd('#userPager', {
		caption : '置顶',
		buttonIcon : 'none',
		onClickButton : updateSort,
		id : 'updateSort1'
	}).navButtonAdd('#userPager', {
		caption : '上移',
		buttonIcon : 'none',
		onClickButton : updateSort,
		id : 'updateSort2'
	}).navButtonAdd('#userPager', {
		caption : '下移',
		buttonIcon : 'none',
		onClickButton : updateSort,
		id : 'updateSort3'
	}).navButtonAdd('#userPager', {
		caption : '置底',
		buttonIcon : 'none',
		onClickButton : updateSort,
		id : 'updateSort4'
	}).navButtonAdd('#userPager', {
		caption : '刷新排序号',
		buttonIcon : 'none',
		onClickButton : reloadSort,
		id : 'btn_resetSort'
	});

	function reloadSort() {
		var o = {};
		o.parentId = parentId;
		var url = ctx + 'admin/menu/reloadSort.html';
		$.post(url, o, function() {
			$('#userTable').trigger('reloadGrid');
		});
	}

	function changeButtonDisplay(ids, display) {
		$.each(ids, function(i, id) {
			$('#' + id).css('display', display);
		});
	}

	function showParent() {
		var o = {};
		o.id = parentId;
		$.post(ctx + 'admin/menu/findById.html', o, function(data) {
			var json = $.parseJSON(data);
			if (json.parent) {
				window.location.href = ctx + 'admin/menu/list.html?parentId='
						+ json.parent.id;
			} else {
				jAlert('已经是最顶级', '提示');
			}
		});
	}

	function updateSort(e) {
		var tarId = e.currentTarget.id.toString();
		var optId = tarId.substring('updateSort'.length, tarId.length);
		var gr = $(this).getGridParam('selarrrow');
		if (gr.length == 0) {
			jAlert('请先选择数据再操作!');
		} else {
			var url = ctx + 'admin/menu/updateSort.html?';
			var o = {};
			$.each(gr, function(i, id) {
				url += 'ids=' + id + '&';
			});
			url = url.substring(0, url.length - 1);

			o.optId = optId;
			o.parentId = parentId;
			$.post(url, o, function() {
				$('#userTable').trigger('reloadGrid');
			});
		}
	}

	function addRow() {
		openEditDialog('save');
	}

	function searchRow() {
		openSearchDialog();
	}

	function editRow() {
		var gr = $("#userTable").jqGrid('getGridParam', 'selrow');
		if (gr != null) {
			var o = {};
			o.id = gr;
			$.post(ctx + 'admin/menu/findById.html', o, function(data) {
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
				openEditDialog('edit');
			});
		}
	}
	function deleteRow() {
		var gr = $("#userTable").jqGrid('getGridParam', 'selarrrow');
		if (gr != null) {
			var url = ctx + 'admin/menu/delete.html?';
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
		o.parentId = parentId;
		o.id = $('#id').val() || '';
		o.name = $('#name').val();
		o.url = $('#url').val();
		$.post(ctx + 'admin/menu/save.html', o, function(data) {
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

	function openEditDialog(opt) {
		var title;
		if (opt == 'save') {
			title = '新增菜单';
		} else {
			title = '修改菜单';
		}
		$("#saveMenuDialog").dialog({
			autoOpen : true,
			modal : true,
			resizable : true,
			width : 500,
			height : 250,
			title : title,
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
			title : '查询',
			position : "center",
			buttons : {
				'查询' : function() {
					var name = $('#name').val();
					var url = $('#url').val();
					$("#userTable").jqGrid('setGridParam', {
						url : ctx + 'admin/menu/listJson.html',
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
			src : ctx + 'admin/index/menu.html'
		});
	}

	function showChildren(cellvalue, options, rowObject) {
		var url = ctx + 'admin/menu/list.html?parentId=' + rowObject.id;
		return '<a href="' + url + '">子菜单管理</a>';
	}
});