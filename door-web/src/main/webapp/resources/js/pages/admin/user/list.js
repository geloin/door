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
			$.post(ctx + 'admin/user/findById.html', o, function(data) {
				var json = $.parseJSON(data);
				$('#id').attr({
					value : json.id
				});
				$('#loginName').attr({
					value : json.loginName
				});
				$('#firstName').attr({
					value : json.firstName
				});
				$('#lastName').attr({
					value : json.lastName
				});
				openEditDialog('edit');
			});
		}
	};

	var delRows = function() {
		var gr = $("#userTable").getGridParam('selarrrow');
		if (gr != null) {
			var url = ctx + 'admin/user/delete.html?';
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
		$('#loginName').attr({
			value : ''
		});
		$('#password').attr({
			value : ''
		});
		$('#lastName').attr({
			value : ''
		});
		$('#firstName').attr({
			value : ''
		});
	}

	var openEditDialog = function(opt) {
		var title;
		if (opt == 'save') {
			title = '新增用户';
			$('#passwordDiv').css('display', '');
			$('#loginName').attr({
				readonly : false
			});
		} else {
			title = '修改用户';
			$('#passwordDiv').css('display', 'none');
			$('#loginName').attr({
				readonly : true
			});
		}
		$('#userDialog').dialog({
			autoOpen : true,
			modal : true,
			resizable : true,
			width : 500,
			height : 350,
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
			height : 250,
			title : '查询',
			position : "center",
			buttons : {
				'查询' : function() {
					var loginName = $('#loginName').val();
					var firstName = $('#firstName').val();
					var lastName = $('#lastName').val();
					$("#userTable").setGridParam({
						url : ctx + 'admin/user/listJson.html',
						postData : {
							'loginName' : loginName,
							'firstName' : firstName,
							'lastName' : lastName
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
		o.loginName = $('#loginName').val();
		o.password = $('#password').val();
		o.firstName = $('#firstName').val();
		o.lastName = $('#lastName').val();
		$.post(ctx + 'admin/user/save.html', o, function(data) {
			$("#userTable").trigger("reloadGrid");
			jAlert('保存成功', '提示');
		});
	}

	$('#userTable')
			.jqGrid(
					{
						url : ctx + 'admin/user/listJson.html',
						datatype : 'json',
						colNames : [ '序号', '登录名', '屏幕名' ],
						colModel : [ {
							name : 'id',
							index : 'id',
							align : 'center',
							editable : false,
							sortable : true
						}, {
							name : 'loginName',
							index : 'loginName',
							editable : false,
							sortable : true
						}, {
							name : 'fullName',
							index : 'fullName',
							editable : false,
							sortable : true
						} ],
						rowNum : 10,
						rowList : [ 10, 20, 30 ],
						pager : '#userPager',
						sortable : true,
						sortname : 'id',
						sortorder : 'asc',
						autowidth : true,
						autoheight : true,
						viewrecords : true,
						multiselect : true,
						height : 400,
						loadComplete : function(data) {
							if (data.rows.length == 0) {
								var newTr = '<tr><td colspan="4" align="center" style="font-size: 20px; color:red">无数据显示</td></tr>';
								$(newTr).appendTo($(this));
							}
							if (data.userdata != null) {
								var newTr = '<tr><td colspan="4" style="font-size: 20px; color: red;">异常'
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
	});

});