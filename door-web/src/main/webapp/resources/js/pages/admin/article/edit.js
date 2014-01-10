$(function() {
	$('#btn_save').button().click(
			function() {
				var id = $('#id').val() || '';
				if (id == '') {
					var lastSelector = $('#channelSelector')
							.find('select:last');
					var seChannelId = lastSelector.val();
					if (seChannelId == -1) {
						var selectors = $('#channelSelector select');
						var seLength = selectors.length;
						if (seLength == 1) {
							seChannelId = $('#' + selectors[0].id).val();
						} else {
							seChannelId = $(
									'#' + selectors[selectors.length - 2].id)
									.val();
						}
					}
					if (seChannelId == -1) {
						jAlert('请先选择栏目', '警告');
					} else {
						var url = ctx + 'admin/article/save.html';

						var o = {};
						o.channelId = seChannelId;
						o.title = $('#title').val();
						o.content = $('#content').val();
						o.attachments = $('#attachments').val();

						$.post(url, o, function() {
							jAlert('保存成功', '提示', function(e) {
								location.href = ctx
										+ 'admin/article/list.html?channelId='
										+ seChannelId;
							});
						});
					}
				} else {
					var url = ctx + 'admin/article/save.html';

					var o = {};
					o.title = $('#title').val();
					o.id = $('#id').val();
					o.content = $('#content').val();
					o.attachments = $('#attachments').val();

					$.post(url, o, function() {
						jAlert('修改成功', '提示', function(e) {
							location.href = ctx
									+ 'admin/article/list.html?channelId='
									+ channelId;
						});
					});
				}
			});

	$('#btn_reset').button().click(function() {
		var inputEls = $('form input:text');
		// 普通的input
		$.each(inputEls, function(i, inputEl) {
			inputEl.value = '';
		});
		// 编辑器
		CKEDITOR.instances.content.setData('');
		// 选择器
		var selectors = $('#channelSelector select');
		selectors[0].value = '-1';
		$('#' + selectors[0].id).change();
	});
	$('#btn_cancel').button().click(function() {
		location.href = ctx + 'admin/article/list.html?channelId=' + channelId;
	});

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
						$('<select id="' + selId + '"></select>').appendTo(
								'#channelSelector');
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
						$('<select id="' + selId + '"></select>').appendTo(
								'#channelSelector');
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
});