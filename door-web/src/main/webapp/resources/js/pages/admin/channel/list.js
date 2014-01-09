$(function() {

	var addRow = function() {
		openEditDialog('save');
	};

	function changeButtonDisplay(ids, display) {
		$.each(ids, function(i, id) {
			$('#' + id).css('display', display);
		});
	}

	var editRow = function() {
		var gr = $("#userTable").getGridParam('selrow');
		if (gr != null) {
			var o = {};
			o.id = gr;
			$.post(ctx + 'admin/channel/findById.html', o, function(data) {
				var json = $.parseJSON(data);
				$('#id').attr({
					value : json.id
				});
				$('#name').attr({
					value : json.name
				});
				openEditDialog('edit');
			});
		}
	};

	var delRows = function() {
		var gr = $("#userTable").getGridParam('selarrrow');
		if (gr != null) {
			var url = ctx + 'admin/channel/delete.html?';
			$.each(gr, function(i, val) {
				url += 'ids=' + val + '&';
			});
			url = url.substring(0, url.length - 1);
			$.post(url, function() {
				$('#userTable').trigger('reloadGrid');
				jAlert('删除成功', '提示');
			});
		}
	};

	var searchRows = function() {
		openSearchDialog();
	};

	var reset = function() {
		$('#name').attr({
			value : ''
		});
	}

	var openEditDialog = function(opt) {
		var title;
		if (opt == 'save') {
			title = '新增栏目';
			$('#passwordDiv').css('display', '');
		} else {
			title = '修改栏目';
			$('#passwordDiv').css('display', 'none');
		}
		$('#userDialog').dialog({
			autoOpen : true,
			modal : true,
			resizable : true,
			width : 500,
			height : 200,
			title : title,
			position : 'center',
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

	var openSearchDialog = function() {
		$("#userDialog").dialog({
			autoOpen : true,
			modal : true,
			resizable : true,
			width : 500,
			height : 200,
			title : '查询',
			position : "center",
			buttons : {
				'查询' : function() {
					var name = $('#name').val();
					$("#userTable").setGridParam({
						url : ctx + 'admin/channel/listJson.html',
						postData : {
							'name' : name
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

	var save = function() {
		var o = {};
		o.id = $('#id').val() || '';
		o.name = $('#name').val();
		o.parentId = parentId;
		$.post(ctx + 'admin/channel/save.html', o, function(data) {
			$("#userTable").trigger("reloadGrid");
			jAlert('保存成功', '提示');
		});
	}

	var updateSort = function(e) {
		var tarId = e.currentTarget.id.toString();
		var optId = tarId.substring('updateSort'.length, tarId.length);
		var gr = $(this).getGridParam('selarrrow');
		if (gr.length == 0) {
			jAlert('请先选择数据再操作!');
		} else {
			var url = ctx + 'admin/channel/updateSort.html?';
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

	var showChildren = function(cellvalue, options, rowObject) {
		var url = ctx + 'admin/channel/list.html?parentId=' + rowObject.id;
		return '<a href="' + url + '">子栏目管理</a>';
	}

	var reloadSort = function() {
		var o = {};
		o.parentId = parentId;
		var url = ctx + 'admin/channel/reloadSort.html';
		$.post(url, o, function() {
			$('#userTable').trigger('reloadGrid');
		});
	}

	$('#userTable')
			.jqGrid(
					{
						url : ctx + 'admin/channel/listJson.html',
						postData : {
							'parentId' : parentId
						},
						datatype : 'json',
						colNames : [ '序号', '名称', '排序', '操作' ],
						colModel : [ {
							name : 'id',
							index : 'id',
							align : 'center',
							editable : false,
							sortable : true
						}, {
							name : 'name',
							index : 'name',
							editable : false,
							sortable : true
						}, {
							name : 'sort',
							index : 'sort',
							editable : false,
							sortable : true
						}, {
							name : 'opt',
							index : 'opt',
							editable : false,
							sortable : false,
							align : 'center',
							formatter : showChildren
						} ],
						rowNum : 10,
						rowList : [ 10, 20, 30 ],
						pager : '#userPager',
						sortable : true,
						sortname : 'sort',
						sortorder : 'asc',
						autowidth : true,
						autoheight : true,
						viewrecords : true,
						multiselect : true,
						height : 400,
						loadComplete : function(data) {
							if (data.rows.length == 0) {
								var newTr = '<tr><td colspan="5" align="center" style="font-size: 20px; color:red">无数据显示</td></tr>';
								$(newTr).appendTo($(this));
							}
							if (data.userdata != null) {
								var newTr = '<tr><td colspan="5" style="font-size: 20px; color: red;">异常'
										+ data.userdata + '</td></tr>';
								$(newTr).appendTo($(this));
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
	$('#userTable').navGrid('#userPager', {
		addfunc : addRow,
		editfunc : editRow,
		delfunc : delRows,
		searchfunc : searchRows,
		alerttext : '请先选择数据再操作'
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

});