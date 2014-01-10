$(function() {

	var addRow = function() {
		var url = ctx + 'admin/article/toEdit.html?channelId=' + channelId;
		location.href = url;
	};

	function changeButtonDisplay(ids, display) {
		$.each(ids, function(i, id) {
			$('#' + id).css('display', display);
		});
	}

	var editRow = function() {
		var gr = $("#userTable").getGridParam('selrow');
		var url = ctx + 'admin/article/toEdit.html?channelId=' + channelId
				+ '&id=' + gr;
		location.href = url;
	};

	var delRows = function() {
		var gr = $("#userTable").getGridParam('selarrrow');
		if (gr != null) {
			var url = ctx + 'admin/article/delete.html?';
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
			title = '新增文章';
			$('#passwordDiv').css('display', '');
		} else {
			title = '修改文章';
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
						url : ctx + 'admin/article/listJson.html',
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
		$.post(ctx + 'admin/article/save.html', o, function(data) {
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
			var url = ctx + 'admin/article/updateSort.html?';
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

	var reloadSort = function() {
		var o = {};
		o.parentId = parentId;
		var url = ctx + 'admin/article/reloadSort.html';
		$.post(url, o, function() {
			$('#userTable').trigger('reloadGrid');
		});
	}

	$('#userTable')
			.jqGrid(
					{
						url : ctx + 'admin/article/listJson.html',
						postData : {
							'channelId' : channelId
						},
						caption : '文章管理',
						datatype : 'json',
						colNames : [ '序号', '标题', '所属栏目', '排序' ],
						colModel : [ {
							name : 'id',
							index : 'id',
							align : 'center',
							editable : false,
							sortable : true
						}, {
							name : 'title',
							index : 'title',
							editable : false,
							sortable : true
						}, {
							name : 'channel',
							index : 'channel',
							editable : false,
							sortable : true,
							formatter : function(a) {
								return a.name;
							}
						}, {
							name : 'sort',
							index : 'sort',
							editable : false,
							sortable : true
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

	if (!channelId) {
		jAlert('当前没有栏目，请先添加栏目', '警告', function(e, a, b) {
			location.href = ctx + 'admin/article/toChannelList.html';
		});

	}

	var nextRandom = function() {
		var x = 99; // 上限
		var y = 1; // 下限
		var rand = parseInt(Math.random() * (x - y + 1) + y);
		return rand;
	};

	// 联动菜单
	var catchChannel = function(pcId, parentIds) {

		var url = ctx + 'admin/channel/listJson.html';
		var o = {};

		if (!parentIds) {
			// 显示第一级
			if (pcId) {
				o.parentId = pcId;
			}
			$.post(url, o, function(data) {
				if (data) {
					var json = $.parseJSON(data);
					var channels = json.rows;
					if (channels && channels.length > 0) {
						var selId = 'selector' + nextRandom();
						$('<select id="' + selId + '"></select>').insertBefore(
								'#btn_filter');
						$('#' + selId).addClass('selectorClass');
						$('<option value="-1">--请选择--</option>').appendTo(
								$('#' + selId));
						$.each(channels, function(i, channel) {
							$(
									'<option value="' + channel.id + '">'
											+ channel.name + '</option>')
									.appendTo('#' + selId);
						});
						$('#' + selId).change(function() {
							var selectedId = $('#' + selId).val();
							parentIds = selId;
							if (selectedId != -1) {
								catchChannel(selectedId, parentIds);
							} else {
								clearSelector(parentIds);
							}
						});
					}
				}
			});
		} else {
			clearSelector(parentIds);

			// 显示第一级
			if (pcId) {
				o.parentId = pcId;
			}
			$.post(url, o, function(data) {
				if (data) {
					var json = $.parseJSON(data);
					var channels = json.rows;
					if (channels && channels.length > 0) {
						var selId = 'selector' + nextRandom();
						$('<select id="' + selId + '"></select>').insertBefore(
								'#btn_filter');
						$('#' + selId).addClass('selectorClass');
						$('<option value="-1">--请选择--</option>').appendTo(
								$('#' + selId));
						$.each(channels, function(i, channel) {
							$(
									'<option value="' + channel.id + '">'
											+ channel.name + '</option>')
									.appendTo('#' + selId);
						});
						$('#' + selId).change(function() {
							var selectedId = $('#' + selId).val();
							if (selectedId != -1) {
								parentIds += ',' + selId;
								catchChannel(selectedId, parentIds);
							} else {
								clearSelector(parentIds);
							}
						});
					}
				}
			});
		}

	}

	var clearSelector = function(parentIds) {
		var allSelectors = $('#channelSelector select');
		if (allSelectors) {
			$.each(allSelectors, function(i, sel) {
				var isParent = false;
				var parentIdArr = parentIds.split(',');
				$.each(parentIdArr, function(j, pid) {
					if (pid == sel.id) {
						isParent = true;
					}
				});
				if (!isParent) {
					$('#' + sel.id).remove();
				}
			});
		}
	}

	catchChannel();

	$('#btn_filter')
			.button()
			.click(
					function() {
						var lastSelector = $('#channelSelector').find(
								'select:last');
						var seChannelId = lastSelector.val();
						if (seChannelId == -1) {
							var selectors = $('#channelSelector select');
							var seLength = selectors.length;
							if (seLength == 1) {
								seChannelId = $('#' + selectors[0].id).val();
							} else {
								seChannelId = $(
										'#'
												+ selectors[selectors.length - 2].id)
										.val();
							}
						}
						if (seChannelId == -1) {
							jAlert('请先选择栏目', '警告');
						} else {
							$('#userTable').jqGrid('setGridParam', {
								postData : {
									'channelId' : seChannelId
								}
							}).trigger('reloadGrid');
						}
					});

});