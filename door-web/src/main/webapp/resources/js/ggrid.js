(function($){
	
	$.fn.ggrid = function(options){
		
		var defaults = {
			columns:[],
			emptyTips:'未查询到相关数据'
		};
		var options = $.extend(defaults, options);  
		
		var me = this;
//		var _id = options.id;
		var _id = this[0].id;
		var _columns = null;
		var _colMap = {};
		var _childColumns = null;
		var _childColMap = {};
		var _classNamePrefix = "test_";
//		var _classNamePrefix = options.classNamePrefix || "";
		var _data = null;
		var _raw_data = null;
		var _actived = null;
		var _pageSize = 60;
		var _rowWidth = 0;
		
		var _fixed_column_width = 0;
		var _fixed_child_column_width = 0;
		
		var _titleRowWidth = 0;
		var _rowHeight = 30;
		var _indexStart = 0;
		var _indexEnd = 0;
		var _firstArea = null;
		var _lastArea = null;
		var _firstAreaSize = 0;
		var _lastAreaSize = 0;
		var _grid = this;
		var _scrollTimer = null;
		var _mouseover = null;
		var _onBeforeLoad = options.onBeforeLoad || function(event){};
		var _onRowChecked = options.onRowChecked || function(event){};
		var _onCheckAll = options.onCheckAll || function(event){};
		var _onRowActived = options.onRowActived;
		var _onButtonClick = options.onButtonClick || function(event){};
		var _onFunctionEditorClick = options.onFunctionEditorClick || function(event){};
		var _onBeforeEdited = options.onBeforeEdited || function(event){return true};
		var _onChange = options.onChange || function(event){};
		var _batch = false;
		var _combobox_option = false;
		var _opened_combobox_options_id;
		var _loaded = false;
		var pageable = false;

		init(options);

		this.getId = function(){
			return _id;
		};
		
		//显示grid框
		this.display = function(){
			var dom = me[0];
			if (!dom) {
				return;
			}
			var width = dom.clientWidth;
//			var height = dom.clientHeight;
			var height = options.height;
			var meWindow = parent.window || window;
			if(!height && meWindow.$('#fixed-header')[0]){
				var viewHeight = $(window).height();
				var pagebarHeight = 0;
				if(options.pageable){
					pagebarHeight = 32;
				}
				height = viewHeight - meWindow.$('#navigation').position().top-50-52-20-pagebarHeight;
//				console.log(height,viewHeight,$('#fixed-header').position().to);
			}
			var str = "<div class=\"" + _classNamePrefix + "ImGrid\" id=\"" + _id + "\" _type=\"grid\"><div class=\"" + _classNamePrefix + "ImGrid_title\" id=\"" + _id + "_title\" style=\"width: " + width + "px\"><div id=\"" + _id + "_title_row\" class=\"" + _classNamePrefix + "title_content\"></div></div><div class=\"" + _classNamePrefix + "ImGrid_data\" id=\"" + _id + "_data\" style=\"width:" + width + "px\"><div id=\"ImGridContent_" + _id + "\"><span style=\"font-size:12px;\">正加载中……</span></div></div></div>";
			dom.outerHTML = str;
			dom = null;
			var gridDataDiv = document.getElementById(_id + "_data");
			
			gridDataDiv.onscroll = scrollImpl;
			gridDataDiv.onmouseover = _grid.mouseover;
			gridDataDiv.onmouseout = _grid.mouseout;
			gridDataDiv.onclick = _grid.click;

			if (document.addEventListener) {
				gridDataDiv.addEventListener('focus', _grid.focus, true);
				gridDataDiv.addEventListener('blur', _grid.blur, true);
			} else {
				gridDataDiv.onfocusin = _grid.focus;
				gridDataDiv.onfocusout = _grid.blur;
			}

			var gridTitleDiv = document.getElementById(_id + "_title");
			height -= gridTitleDiv.offsetHeight;
			gridDataDiv.style.height = height + "px";

			handleWidths(width, _fixed_column_width, _columns);
			handleWidths(width, _fixed_child_column_width, _childColumns);
			handleRowWidth();
			
			_titleRowWidth = _rowWidth;
			var titleRow = document.getElementById(_id + "_title_row");
			titleRow.outerHTML = create_title_html().join("");


//			gridTitleDiv.onmouseover = _grid.titleMouseover;
//			gridTitleDiv.onmouseout = _grid.titleMouseout;
//			gridTitleDiv.onmousemove = _grid.titleMousemove;
//			gridTitleDiv.onmousedown = _grid.titleMousedown;
//			gridTitleDiv.onmouseup = _grid.titleMouseup;
//			gridTitleDiv.onclick = _grid.titleClick;
//			gridTitleDiv.onmouseover = _grid.titleMouseover;
//			gridTitleDiv.onmouseout = _grid.titleMouseout;
//			gridTitleDiv.onmousemove = _grid.titleMousemove;
//			gridTitleDiv.onmousedown = _grid.titleMousedown;
//			gridTitleDiv.onmouseup = _grid.titleMouseup;
			gridTitleDiv.onclick = _grid.titleClick;
//			gridTitleDiv.onselectstart = function(){return false;};
			
			window.addEventListener ? window.addEventListener("resize", _grid.resize, false) : window.attachEvent("onresize", _grid.resize);
			
		}

		this.resize = function(){
			var gridDiv =  document.getElementById(_id);
			var gridDataDiv = document.getElementById(_id + "_data");
			var gridTitleDiv = document.getElementById(_id + "_title");
			var width = gridDiv.clientWidth;
			var height = gridDiv.clientHeight;
			if(_grid.getHeight() < _data.length * _rowHeight){
				gridTitleDiv.style.width = (width - 17) + "px";
			}else{
				gridTitleDiv.style.width = width + "px";
			}
			gridDataDiv.style.width = width + "px";
			gridDataDiv.style.height = (height - gridTitleDiv.offsetHeight) + "px";
			display_again();
		}
		
		this.titleClick = function(event){
			var event = event || window.event;
			var srcElement = event.srcElement || event.target;
			if(srcElement.getAttribute("type") == "checkbox"){
				if(srcElement.checked){
					_grid.checkedAll();
				}else{
					_grid.uncheckedAll();
				}
				//触发全选或全不选事件
				var event = {
					type: "checkAll",
					checked: srcElement.checked
				};
				_onCheckAll(event);
			}
		}

		//表头列宽拖拽
		this.titleMousemove = function(event){
			var event = event || window.event;
			var srcElement = event.srcElement || event.target;
			srcElement = findNode(srcElement, "title");
			if(srcElement == null){
				return;
			}
			var gridTitleDiv = document.getElementById(_id + "_title");
			//debug.value= gridTitleDiv.scrollLeft + ", " + event.clientX + ", " + getOffsetLeft(srcElement) + "," + srcElement.offsetWidth + "," + _grid._moveStart;
//			console.log(event.clientX , getOffsetLeft(srcElement) , srcElement.offsetWidth , gridTitleDiv.scrollLeft,event.clientX > getOffsetLeft(srcElement) + srcElement.offsetWidth - 5 - gridTitleDiv.scrollLeft);
//			console.log(gridTitleDiv,' : ',gridTitleDiv.scrollLeft);
//			console.log(event.clientX , getOffsetLeft(srcElement) , srcElement.offsetWidth);
			if(event.clientX > getOffsetLeft(srcElement) + srcElement.offsetWidth - 5 - gridTitleDiv.scrollLeft || _grid._moveStart){
				srcElement.style.cursor = 'col-resize';
			}else{
				srcElement.style.cursor = 'default';
			}
			if(_grid._moveStart){
				var width = _grid._oldWidth + event.clientX - _grid._oldX;
				width = width > 20 ? width : 20;
				_grid._col.style.width = width + "px";
			}

		}
		//表头列宽拖拽开始
		this.titleMousedown = function(event){
			var event = event || window.event;
			var srcElement = event.srcElement || event.target;
			srcElement = findNode(srcElement, "title");
			if(srcElement == null){
				return;
			}
			var gridTitleDiv = document.getElementById(_id + "_title");
//			console.log(event.clientX , document.body.offsetLeft , srcElement.offsetLeft , srcElement.offsetWidth , gridTitleDiv.scrollLeft,event.clientX > document.body.offsetLeft + srcElement.offsetLeft + srcElement.offsetWidth - 5 - gridTitleDiv.scrollLeft);
			if(event.clientX > document.body.offsetLeft + srcElement.offsetLeft + srcElement.offsetWidth - 5 - gridTitleDiv.scrollLeft){
				_grid._moveStart = true;
				_grid._oldX = event.clientX;
				_grid._oldWidth = srcElement.offsetWidth - 3;
				_grid._col = srcElement;
			}
		}
		//表头列宽拖拽结束
		this.titleMouseup = function(event){
			if(_grid._moveStart && _grid._col != null){
				_grid._moveStart = false;
				var colIndex = _grid._col.getAttribute("_colIndex");
				var oldWidth = _columns[colIndex].getWidth();
				var newWidth = _grid._col.offsetWidth - 3;
				_columns[colIndex].setWidth(newWidth);
				_childColumns[colIndex].setWidth(newWidth);
				_rowWidth += newWidth - oldWidth;
				_titleRowWidth += newWidth - oldWidth;
				document.getElementById(_id + "_title_row").style.width = _titleRowWidth + "px";
//				console.log(_grid._moveStart , _grid._col);
//				console.log(colIndex , oldWidth,_loaded,newWidth,_grid._col.offsetWidth);
				_grid._oldWidth = null;
				_grid._oldX = null;
				_grid._col = null;
				if(_loaded){
					display_again();
				}
			}
		}
		//表头鼠标移入
		this.titleMouseover = function(event){
			var event = event || window.event;
			var srcElement = event.srcElement || event.target;
			var type = srcElement.getAttribute("_type");
			if(type == "title"){
				srcElement.className = srcElement.className + " " + _classNamePrefix + "title_mouseover ";
			}
		}
		//表头鼠标移出
		this.titleMouseout = function(event){
			var event = event || window.event;
			var srcElement = event.srcElement || event.target;
			var type = srcElement.getAttribute("_type");
			if(type == "title"){
				srcElement.className = srcElement.className.replace(" " + _classNamePrefix + "title_mouseover ", "");
			}
		}
		//获取列宽列表
		this.getColWidths = function(){
			var widths = new Array;
			for(var i = 0; i < _columns.length; i++){
				widths.push(_columns[i].getWidth());
			}
	//		debug.value = widths;
			return widths;
		}
		//获取grid显示区域高度
		this.getHeight = function(){
			var gridDataDiv = document.getElementById(_id + "_data");
			return gridDataDiv.clientHeight;
		}
		//处理点击事件
		this.click = function(event){
			var event = event || window.event;
			var srcElement = event.srcElement || event.target;
			var type = srcElement.getAttribute("_type");
			if(type == null){
				return;
			}
			var row = findNode(srcElement, "row");
			var index = Number(row.getAttribute("_index"));
			if(type == "button"){ //按钮点击
				//触发按钮点击事件
				var event = {
					index: index,
					data: _data[index],
					type: type,
					name: srcElement.getAttribute("_name")
				};
				_onButtonClick(event);
			}else if(type=="_checkbox"){	//复选框点击
				var checked = srcElement.checked;
				_data[index].checked = checked;
				if(index != _actived){
					if(checked){
						row.className = _classNamePrefix + "row " + _classNamePrefix + "row_checked";
					}else if(index % 2){
						row.className = _classNamePrefix + "row " + _classNamePrefix + "row_even";
					}else{
						row.className = _classNamePrefix + "row " + _classNamePrefix + "row_odd";
					}
				}
				//触发行复选框选中、取消选中事件
				var event = {
					index: index,
					data: _data[index],
					type: type,
					checked: checked
				};
				_onRowChecked(event);
			}else if(type == "col" && srcElement.getAttribute("_name") == "open"){
				//展开收拢按钮
				open_close(index);
				display_again();
			}else if(type == "function_editor_button" || (type == "editor" && srcElement.getAttribute("_editor") == "function")){
				//触发function编辑器点击事件
				var event = {
					index: index,
					data: _data[index],
					name: srcElement.getAttribute("_name"),
					type: "editor"
				};
				_onFunctionEditorClick(event);
			}else if(type == "combobox_editor_button"){
				//combobox按钮点击
				create_combobox_options(srcElement, _data[index], srcElement.getAttribute("_name"), index);
			}else if(type == "combobox_editor_option"){
				//combobox选项点击
				var col = findNode(srcElement, "col");
				var inputElement = col.childNodes[0];
				
				inputElement.value = srcElement.getAttribute("_value");
				inputElement.select();
				if(col.childNodes.length == 3){
					col.childNodes[2].outerHTML = "";
					col.style.overflow = "hidden";
				}
				
				var event = {
						index: index,
						type: type,
						name: inputElement.getAttribute("_name"),
						colValue:inputElement.value,
						data:_data[index]
					};
				
				_onButtonClick(event);
		
			}else{
				var name = null;
				if(type == "col"){ //单元格点击
					name = srcElement.getAttribute("_name");
				}
				if(_actived != null){
					var activedRow = document.getElementById("Row_" + _actived);
					if(activedRow){
						if(_data[_actived].checked){
							activedRow.className = _classNamePrefix + "row " + _classNamePrefix + "row_checked";
						}else if(_actived % 2){
							activedRow.className = _classNamePrefix + "row " + _classNamePrefix + "row_even";
						}else{
							activedRow.className = _classNamePrefix + "row " + _classNamePrefix + "row_odd";
						}
					}
				}
				_actived = index;
				row.className = _classNamePrefix + "row " + _classNamePrefix + "row_actived";
				//触发行激活事件
				var event = {
					index: index,
					data: _data[index],
					name: name,
					type: "row"
				};
				if($.isFunction(_onRowActived)){
					_onRowActived(event);
				}
			}
		}
		//处理鼠标聚焦事件
		this.focus = function(event){
			var event = event || window.event;
			var srcElement = event.srcElement || event.target;
			var editor = srcElement.getAttribute("_editor");
			if(/(text|money|combobox)/.test(editor)){
				srcElement.select();
			}
		}
		//处理失去焦点事件
		this.blur = function(event){
			var event = event || window.event;
			var srcElement = event.srcElement || event.target;
			var editor = srcElement.getAttribute("_editor");
			if(editor != null && editor != "function"){
				var name = srcElement.getAttribute("_name");
				var row = findNode(srcElement, "row");
				if(row == null){
					return;
				}
				var index = Number(row.getAttribute("_index"));
				var item = _data[index];
				var value = srcElement.value;
				if(value != null && editor == "money"){
					value = value.replace(/[^\d\.-]/g, "");
				}
				var colMap = item.parent ? _childColMap : _colMap;
				if(colMap[name].check(item, value.replace(/[,]/g,""))){
					var oldValue = _data[index][name];
					if(editor == "money"){
						srcElement.value = money(value, "0.00");
					}
					_data[index][name] = value;
					//触发单元格内容修改事件
					var e = {
						index: index,
						data: _data[index],
						name: name,
						oldValue: oldValue,
						value: _data[index][name],
						type: "change"
					};
					_onChange(e);
					srcElement.style.backgroundColor = "";
				}else{
					if(editor == "money"){
						srcElement.value = money(_data[index][name], "0.00");
					}else{
						srcElement.value = _data[index][name];
					}
					srcElement.style.backgroundColor = "red";
				}
			}
			/**
			if(!_combobox_option && "combobox_editor_input" == srcElement.getAttribute("_type")){
				var col = findNode(srcElement, "col");
				if(col.childNodes.length == 3){
					col.childNodes[2].outerHTML = "";
				}
			}
			**/
		}

		//处理鼠标Over事件
		this.mouseover = function(event){
			var event = event || window.event;
			var srcElement = event.srcElement || event.target;
			var type = srcElement.getAttribute("_type");
			if(type == "combobox_editor_option"){
				_combobox_option = true;
			}else{
				_combobox_option = false;
			}
			//debug.value = "";
			//debug.value = srcElement.outerHTML;
			var row = findNode(srcElement, "row");
			if(row == null){
				return;
			}
			var index = Number(row.getAttribute("_index"));
			if(index != _actived && !_data[index].checked){
				row.className = _classNamePrefix + "row " + _classNamePrefix + "row_mouseover";
			}
		}
		//处理鼠标Out事件
		this.mouseout = function(event){
			var event = event || window.event;
			var srcElement = event.srcElement || event.target;
			var row = findNode(srcElement, "row");
			if(row == null){
				return;
			}
			var index = Number(row.getAttribute("_index"));
			if(index == _actived){
				row.className = _classNamePrefix + "row " + _classNamePrefix + "row_actived";
			}else if(_data[index].checked){
				row.className = _classNamePrefix + "row " + _classNamePrefix + "row_checked";
			}else if(index % 2){
				row.className = _classNamePrefix + "row " + _classNamePrefix + "row_even";
			}else{
				row.className = _classNamePrefix + "row " + _classNamePrefix + "row_odd";
			}
		}
		//处理滚动条事件
		this.scroll = function(){
			if(_opened_combobox_options_id != null){
				var opts = document.getElementById(_opened_combobox_options_id);
				if(opts != null){
					opts.outerHTML = "";
				}
			}
			var s = new Date();
			var height = _grid.getHeight();
			var scrollTop = document.getElementById(_id + "_data").scrollTop;
			var total = _data.length;
			if((_indexEnd * _rowHeight - scrollTop - height < height / 4)
					&& ((_indexEnd + _pageSize) * _rowHeight - scrollTop - height >= height / 4)){
				//down
				var oldIndex = _indexEnd;
				_indexEnd += _pageSize;
				_indexEnd = _indexEnd < total ? _indexEnd : total;

				var areaId = "Area_" + _id + "_" + new Date().getTime();
				var size = _indexEnd - oldIndex;
				if(size <= 0){
					return;
				}
				var strs = new Array;
				display_area_html(strs, areaId, size, oldIndex, _indexEnd);
				var content = document.getElementById("ImGridContent_" + _id);
				content.insertAdjacentHTML("beforeEnd", strs.join(""));
				if(_firstArea != null && _lastArea != null){
					_indexStart += _firstAreaSize;
					var topBlankArea = document.getElementById("TopBlankArea_" + _id);
					if(topBlankArea !=null){
						content.removeChild(_firstArea);
					}else{
						_firstArea.outerHTML = "<div id=\"TopBlankArea_" + _id + "\" style=\"width:80%\"></div>";
						topBlankArea = document.getElementById("TopBlankArea_" + _id);
					}
					topBlankArea.style.height =  _indexStart * _rowHeight + "px";
					_firstArea = _lastArea;
					_firstAreaSize = _lastAreaSize;
				}
				_lastArea = document.getElementById(areaId);
				_lastAreaSize = size;

			}else if((_indexStart > 0 && scrollTop - _indexStart * _rowHeight < height / 4) && (scrollTop - (_indexStart - _pageSize) * _rowHeight >= height / 4)){
				//up
				var oldIndex = _indexStart;
				_indexStart -= _pageSize;
				_indexStart = _indexStart > 0 ? _indexStart : 0;

				var areaId = "Area_" + _id + "_" + new Date().getTime();
				var size = oldIndex - _indexStart;
				if(size <= 0){
					return;
				}
				var strs = new Array;
				display_area_html(strs, areaId, size, _indexStart, oldIndex);
				var content = document.getElementById("ImGridContent_" + _id);
				var topBlankArea = document.getElementById("TopBlankArea_" + _id);
				if(topBlankArea != null){
					if(_indexStart > 0){
						topBlankArea.outerHTML = "<div id=\"TopBlankArea_" + _id + "\" style=\"height:" + _indexStart * _rowHeight + "px;width:80%\"></div>" + strs.join("");
					}else{
						topBlankArea.outerHTML = strs.join("");
					}
				}
				topBlankArea = null;
				if(_lastArea != null){
					content.removeChild(_lastArea);
					_indexEnd -= _lastAreaSize;
				}
				_lastArea = _firstArea;
				_lastAreaSize = _firstAreaSize;
				_firstArea = document.getElementById(areaId);
				_firstAreaSize = size;
			}else if((scrollTop - (_indexStart - _pageSize) * _rowHeight < height / 4) || ((_indexEnd + _pageSize + 1) * _rowHeight - scrollTop - height < height / 4)){
				//other
				_indexStart = Math.floor((scrollTop - height / 4) / _rowHeight);
				_indexStart = _indexStart > 0 ? _indexStart : 0;
				_indexEnd = _indexStart + _pageSize;
				_indexEnd = _indexEnd < total ? _indexEnd : total;
				var areaId = "Area_" + _id + "_" + new Date().getTime();
				var size = _indexEnd - _indexStart;
				if(size <= 0){
					return;
				}
				var strs = new Array;
				strs.push("<div id=\"ImGridContent_");
				strs.push(_id);
				strs.push("\" style=\"height:");
				strs.push(total*_rowHeight);
				strs.push("px;width:");
				strs.push(_rowWidth);
				strs.push("px\" class=\"");
				strs.push(_classNamePrefix);
				strs.push("data_content\">");
				if(_indexStart > 0){
					strs.push("<div id=\"TopBlankArea_");
					strs.push(_id);
					strs.push("\" style=\"height:");
					strs.push(_indexStart * _rowHeight);
					strs.push("px;width:80%\"></div>");

				}
				display_area_html(strs, areaId, size, _indexStart, _indexEnd);
				strs.push("</div>");
				var content = document.getElementById("ImGridContent_" + _id);
				content.outerHTML = strs.join("");
				content = null;
				_firstArea = document.getElementById(areaId);
				_firstAreaSize = size;
				_lastArea = null;
				_lastAreaSize = 0;
			}
			var topBlankArea = document.getElementById("TopBlankArea_" + _id);
		//	debug.value = new Date() - s + ", " + _indexStart + ", " + _indexEnd + ", " + (topBlankArea != null ? topBlankArea.offsetHeight : 0);
			topBlankArea = null;
		}
		//改变可编辑状态
		this.editable = function(indexs, type){
			for(var i = 0; i < indexs.length; i++){
				_data[indexs[i]].editable = type;
			}
			if(_batch){
				return;
			}
			display_again();
		}
		//获取可编辑行的数据
		this.getEditedRows = function(){
			var result = new Array;
			var len = _data.length;
			for(var i = 0; i < len; i++){
				var item = _data[i];
				if(item.editable){
					result.push(clone(item));
				}
			}
			return result;
		}
		//在数据对象上修改属性，不显示在页面上
		this.setAttribute = function(index, name, value){
			var item = _data[index];
			if(item != null){
				item[name] = value;
			}
			if(_batch){
				return;
			}
			display_again();
		}
		//开启批量处理模式
		this.start = function(){
			_batch = true;
		}

		//结束批量处理模式，刷新页面显示
		this.commit = function(){
			_batch = false;
			_data = handleData(_raw_data);
			display_again();
		}

		//滚动到指定位置
		this.scrollTo = function(type){
			var content = document.getElementById(_id + "_data");
			if(type == "top"){
				content.scrollTop = 0;
			}else if($.isNumeric(type)){
				content.scrollTop = type;
			}else{
				content.scrollTop = _data.length * _rowHeight - _grid.getHeight();
			}
		}

		this.scrollToRow = function(row){
			var content = document.getElementById(_id + "_data");
			if($.isNumeric(row)){
				row = row - 1;
				row = row <= 0 ? 0 : row;
				row = row > _data.length ? _data.length : row;
				content.scrollTop = (row * _rowHeight);
			}
		}
		//删除所有子行
		this.clearAllChild = function(){
			for(var i = 0; i < _raw_data.length; i++){
				_raw_data[i].children = null;
			}
			if(_batch){
				return;
			}
			_data = handleData(_raw_data);
			display_again();
		}
		//新增子行
		this.addChildRowAfter = function(index, item){
			var parent = _data[index];
			if(parent.parent == null){//限制数据为两级，子节点不能再添加子节点
				if(parent.children == null){
					parent.children = [];
				}
				var children = parent.children;
				children.push(item);
				if(_batch){
					return;
				}
				item.parent = parent;
				item.sn = parent.sn + "-" + children.length;
				if(!parent.open){
					open_close(index)
				}else{
					_data.splice(index + children.length, 0, item);
				}
			}
			display_again();
		}

		//新增子行
		this.addChildRowAfterByRawIndex = function(rawIndex, item){
			var parent = _raw_data[rawIndex];
			if(parent.parent == null){//限制数据为两级，子节点不能再添加子节点
				if(parent.children == null){
					parent.children = [];
				}
				var children = parent.children;
				children.push(item);
				if(_batch){
					return;
				}
				item.parent = parent;
				item.sn = parent.sn + "-" + children.length;
				if(!parent.open){
					open_close(index)
				}else{
					_data.splice(index + children.length, 0, item);
				}
			}
			display_again();
		}
		//删除子行
		this.delChildRow = function(index){
			var item = _data[index];
			var parent = item.parent;
			if(parent != null){//只能删除子行
				var children = parent.children;
				var sn = children.length - 1;
				if(sn <= 0){
					parent.children = null;
				}else{
					for(var i = sn; i >= 0; i--){
						if(children[i] == item){
							children.splice(i, 1);
						}else{
							children[i].sn = parent.sn + "-" + sn;
							sn--;
						}
					}
				}
				item.parent = null;
				item = null;
				if(_batch){
					return;
				}
				_data.splice(index, 1);
			}
			display_again();
		}
		//表首增加行
		this.addRowBefore = function(item){
			_raw_data.reverse();
			_raw_data.push(item);
			_raw_data.reverse();
			if(_batch){
				return;
			}
			_data = handleData(_raw_data);
			display_again();
			var content = document.getElementById(_id + "_data");
			content.scrollTop = 0;
		}
		//表尾增加行
		this.addRowAfter = function(item){
			_raw_data.push(item);
			if(_batch){
				return;
			}
			_data.push(item);
			item.sn = _raw_data.length;
			display_again();
			var content = document.getElementById(_id + "_data");
			content.scrollTop = _data.length * _rowHeight - _grid.getHeight();
		}
		//删除所有选中的行（TODO：没考虑子行的删除问题，因此此方法不适用有子行的场景）
		this.deleteAllCheckedRows = function(){
			var len = _raw_data.length;
			var result = new Array;
			for(var i = 0; i < len; i++){
				var item = _raw_data[i];
				if(!item.checked){
					result.push(item);
				}
			}
			_raw_data = result;
			if(_batch){
				return;
			}
			_data = handleData(_raw_data);
			display_again();
		}
		//获取选中行的id列表（TODO：没考虑子行的选中获取问题，因此此方法不适用有子行的场景）
		this.getCheckedDataAttrs = function(name){
			var attrs = new Array;
			var len = _raw_data.length;
			for(var i = 0; i < len; i++){
				var item = _raw_data[i];
				if(item.checked){
					attrs.push(item[name]);
				}
			}
			return attrs;
		}
		//获取选中行数据对象列表（TODO：没有考虑子行选中的情况）
		this.getCheckedDatas = function(){
			var data = new Array;
			var len = _raw_data.length;
			for(var i = 0; i < len; i++){
				var item = _raw_data[i];
				if(item.checked){
					data.push(clone(item));
				}
			}
			return data;
		}
		//获取选中行的index列表（TODO：没考虑子行的选中获取问题，因此此方法不适用有子行的场景）
		this.getCheckedRowIndexes = function(){
			var ids = new Array;
			var len = _data.length;
			for(var i = 0; i < len; i++){
				if(_data[i].checked){
					ids.push(i);
				}
			}
			return ids;
		}
		
		//根据选中行的index获取该行数据（zfk:20131211）
		this.getCheckedDataByIndex = function(index){
			return _data[index];
		}
		
		//获取选中行的原始数据index列表
		this.getCheckedRawIndexes = function(){
			var ids = new Array;
			var len = _raw_data.length;
			for(var i = 0; i < len; i++){
				if(_raw_data[i].checked){
					ids.push(i);
				}
			}
			return ids;
		}

		//获取当前激活行对象
		this.getActivedRow = function(){
			return _actived != null ? _data[_actived] : null;
		}

		//获取所有有子行的行对象列表
		this.getRowsByHasChild = function(){
			var result = new Array;
			var len = _raw_data.length;
			for(var i = 0; i < len; i++){
				var item = _raw_data[i];
				if(item.children != null && item.children.length > 0){
					result.push(clone(item));
				}
			}
			return result;
		}

		//选中所有行
		this.checkedAll = function(containChildren){
			var len = _raw_data.length;
			for(var i = 0; i < len; i++){
				var item = _raw_data[i];
				item.checked = true;
				if(containChildren){
					var children = item.children;
					if(children != null){
						var clen = children.length;
						for(var j = 0; j < clen; j++){
							children[j].checked = true;
						}
					}
				}
			}
			if(_batch){
				return;
			}
			_data = handleData(_raw_data);
			display_again(false);
		}
		//不选中所有行
		this.uncheckedAll = function(containChildren){
			var len = _raw_data.length;
			for(var i = 0; i < len; i++){
				var item = _raw_data[i];
				item.checked = false;
				if(containChildren){
					var children = item.children;
					if(children != null){
						var clen = children.length;
						for(var j = 0; j < clen; j++){
							children[j].checked = false;
						}
					}
				}
			}
			if(_batch){
				return;
			}
			_data = handleData(_raw_data);
			display_again(false);
		}
		//展开所有行
		this.openAll = function(){
			for(var i = _raw_data.length - 1; i >= 0 ; i--){
				_raw_data[i].open = true;
			}
			if(_batch){
				return;
			}
			_data = handleData(_raw_data);
			display_again();
		}
		//收拢所有行
		this.closeAll = function(){
			for(var i = _raw_data.length - 1; i >= 0 ; i--){
				_raw_data[i].open = false;
			}
			if(_batch){
				return;
			}
			_data = handleData(_raw_data);
			display_again();
		}
		//修改指定单元格的Combobox选项
		this.changeComboboxOptions = this.changeSelectOptions = function(index, name, options){
			var item = _data[index];
			item["__" + name + "_options"] = options;
		}
		//加载数据
		this.load = function(data){
			
			if($.isFunction(_onBeforeLoad) && data){
				_onBeforeLoad.call(this,data);
			}
			
			if($('#'+_id+'checkbox')){
				$('#'+_id+'checkbox').prop('checked',false);
			}
			
			var s = new Date();
			var content = document.getElementById(_id + "_data");
			content.scrollTop = 0;
			_raw_data = data;
			_data = handleData(data);
			
			this.scrollTo("top");
			display_again();
			_actived = null;
			_loaded = true;
			
	//		debug.value = new Date() - s + ", " + _indexStart + ", " + _indexEnd + ", " + 0;

		}

		//修改配置，重新加载数据
		this.reconfig = function(options){
			init(options);
			this.display();
			if(_loaded){
				this.load(_raw_data);
			}
		}

		//初始化参数
		function init(options){

			options.viewers = {};
			options.editors = {};
			options.types = {};
			options.titles = [];
			options.widths = [];
			options.checkers = {};
			options.aligns = {};
//			var colNames = options.columnNames || [];
			var colNames = [];
			$(options.columns).each(function(i,n){
				colNames.push(n.mapping);
				options.widths.push(n.width);
				options.titles.push(n.name);
				if(n.viewer){
					options.viewers[n.mapping] = n.viewer;
				}
				if(n.editor){
					options.editors[n.mapping] = n.editor;
				}
				if(n.type){
					options.types[n.mapping] = n.type;
				}
				if(n.checker){
					options.checkers[n.mapping] = n.checker;
				}
				if(n.align){
					options.aligns[n.mapping] = n.align;
				}
			
			});
			
			var len = colNames.length;
			_columns = new Array;
			var rowWidth = 0;
			for (var i = 0; i < len; i++) {
				var name = colNames[i];
				var width = options.widths && options.widths.length > i ? options.widths[i] : 100;
				if(width > 1){
					_fixed_column_width += width + 3;
				}
				var col = new Column(options.titles[i], name, options.types[name],
						options.viewers[name], options.editors[name],
						options.checkers[name], width, options.aligns[name]);
				_columns.push(col);
				_colMap[name] = col;
			}
			options.childViewers = options.viewers;
			options.childEditors = options.editors;
			options.childTypes = options.types;
			options.childCheckers = options.checkers;
			options.childAligns = options.aligns;
			options.childWidths = options.childWidths || options.widths;
			len = colNames.length;
			_childColumns = new Array;
			var childRowWidth = 0;
			for (var i = 0; i < len; i++) {
				var name = colNames[i];
				var width = options.childWidths && options.childWidths.length > i ? options.childWidths[i] : 100;
				if(width > 1){
					_fixed_child_column_width += width + 3;
				}
				var col = new Column("", name, options.childTypes[name],
						options.childViewers[name], options.childEditors[name],
						options.childCheckers[name], width,
						options.childAligns[name]);
				_childColumns.push(col);
				_childColMap[name] = col;
			}
			_rowWidth = childRowWidth > rowWidth ? childRowWidth : rowWidth;
		
		}
		
		
		//处理数据行宽度
		function handleRowWidth(){
			var len = _columns.length;
			var rowWidth = 0; 
			for(var i = 0; i < len; i++){
				rowWidth += _columns[i].getWidth() + 3;
			}
			var clen = _childColumns.length;
			var crowWidth = 0; 
			for(var i = 0; i < len; i++){
				crowWidth += _childColumns[i].getWidth() + 3;
			}
			_rowWidth = rowWidth > crowWidth ? rowWidth : crowWidth;
		}

		//处理列宽度中的百分比，列宽=(总宽度-所有固定宽度)*百分比
		function handleWidths(total, fixed, columns){
			if(columns == null){
				return;
			}
			var len = columns.length;
			var last = null;
			var sum = 0; 
			for(var i = 0; i < len; i++){
				var column = columns[i]
				var width = column.getRawWidth();
				if(width <= 1 && fixed < total){
					column.setWidth(Math.round((total - 1 - fixed) * width) - 3);
					sum += column.getWidth() + 3;
					last = column;
				}
			}
			if(last){
				last.setWidth(last.getWidth() + total - 1 - fixed - sum);
			}
		}

		//处理数据子节点数据（包含默认展开）
		function handleData(raw){
			var data = new Array(0);
			if(raw){
				var len = raw.length;
				for(var i = 0; i < len; i++){
					var item = raw[i];
					item.sn = i + 1;
					data.push(item);
					var children = item.children;
					if(children != null){
						var clen = children.length;
						for(var j = 0; j < clen; j++){
							var child = children[j];
							child.parent = item;
							child.sn = (i + 1) + "-" + (j + 1)
							if(item.open){
								data.push(child);
							}
						}
					}
				}
			}
			return data;
		}

		//处理数据的SN
		function handleData4SN(data){
			var len = data.length;
			var psn = 1;
			var csn = 1;
			for(var i = 0; i < len; i++){
				var item = data[i];
				if(item.parent){//子行
					item.sn = psn + "-" + csn;
					csn++;
				}else{//父行
					item.sn = psn;
					psn++;
				}
			}
		}

		//滚动
		function scrollImpl(){
			if(_batch){
				return;
			}
			document.getElementById(_id + "_title").scrollLeft = document.getElementById(_id + "_data").scrollLeft;
			if (_scrollTimer) {
				window.clearTimeout(_scrollTimer);
			}
			_scrollTimer = window.setTimeout(_grid.scroll, 1);
		}
		//创建combobox下拉列表
		function create_combobox_options(obj, item, name, index){
			var values = item["__" + name + "_options"];
			if(values == null){
				var colMap = item.parent ? _childColMap : _colMap;
				values = colMap[name].getEditor() || [];
			}
			var col = findNode(obj, "col");
			if(col.childNodes.length == 3){
				_opened_combobox_options_id = null;
				col.childNodes[2].outerHTML = "";
				col.style.overflow = "hidden";
				col.style.zIndex = 0;
			}else{
				var strs = new Array;
				_opened_combobox_options_id = _id + "_" + index + "_" + name + "_options";
				strs.push("<div id=\"");
				strs.push(_opened_combobox_options_id);
				strs.push("\" class=\"");
				strs.push(_classNamePrefix);
				strs.push("combobox_options\"");
				var optionsDivLenth = values.length * 23 + 2;
				optionsDivLenth = optionsDivLenth > 202 ? 202 : optionsDivLenth;
				if((col.offsetTop + optionsDivLenth + 1 + _rowHeight) > (document.getElementById(_id + "_data").offsetTop + document.getElementById(_id + "_data").scrollTop + _grid.getHeight())){ //下面放不下
					strs.push(" style=\"top:-");
					strs.push(optionsDivLenth + _rowHeight - 1);
					strs.push("px\"");
				}
				strs.push(">");
				for(var i = 0; i < values.length; i++){
					var value = values[i];
					strs.push("<div class=\"");
					strs.push(_classNamePrefix);
					strs.push("combobox_option\" _type=\"combobox_editor_option\" _value=\"");
					strs.push(value);
					strs.push("\" title=\"");
					strs.push(value);
					strs.push("\">");
					strs.push(value);
					strs.push("</div>");
				}
				strs.push("</div>");
				col.insertAdjacentHTML("beforeEnd", strs.join(""));
				col.style.overflow = "visible";
				col.style.zIndex = 200;
			}
			col.firstChild.focus();
			col.firstChild.select();
		}

		//生成显示区块HTML
		function display_area_html(strs, areaId, size, index, endIndex){
			strs.push("<div id=\"");
			strs.push(areaId);
			strs.push("\"");
			if(size < _pageSize){
				strs.push(" style=\"height:");
				strs.push(size*_rowHeight);
				strs.push("px\"");
			}
			strs.push(" class=\"");
			strs.push(_classNamePrefix);
			strs.push("area\">");
			for(var i = index; i < endIndex; i++){
				display_rows_html(strs, i);
			}
			strs.push("</div>");
		}

		//生成表头行HTML
		function create_title_html(){
			var strs = new Array;
			strs.push("<div id=\"");
			strs.push(_id);
			strs.push("_title_row\" class=\"");
			strs.push(_classNamePrefix);
			strs.push("title_content\" style=\"height:");
			strs.push(_rowHeight);
			strs.push("px;width:");
			strs.push(_titleRowWidth);
			strs.push("px\">");
			var len = _columns.length;
			for(var i = 0; i < len; i++){
				_columns[i].title(strs, i);
			}
			strs.push("</div>");
			return strs;
		}

		//生成显示行HTML
		function display_rows_html(strs, index){
			var item = _data[index];
			strs.push("<div id=\"Row_");
			strs.push(index);
			strs.push("\" _index=\"");
			strs.push(index);
			strs.push("\" class=\"");
			strs.push(_classNamePrefix);
			strs.push("row");
			if(index == _actived){
				strs.push(" ");
				strs.push(_classNamePrefix);
				strs.push("row_actived");
			}else if(item.checked){
				strs.push(" ");
				strs.push(_classNamePrefix);
				strs.push("row_checked");
			}else if(index % 2){
				strs.push(" ");
				strs.push(_classNamePrefix);
				strs.push("row_even");
			}else{
				strs.push(" ");
				strs.push(_classNamePrefix);
				strs.push("row_odd");
			}
			strs.push("\" _type=\"row\" style=\"height:");
			strs.push(_rowHeight);
			strs.push("px;width:");
			strs.push(_rowWidth);
			strs.push("px\">");
			var columns = item.parent ? _childColumns : _columns;
			var len = columns.length;
			for(var i = 0; i < len; i++){
				columns[i].content(strs, item, index);
			}
			strs.push("</div>");
		}
		//重新生成显示区域
		function display_again(reRenderTitle){
			var gridDataDiv = document.getElementById(_id + "_data");
			var scrollTop = gridDataDiv.scrollTop;
			var height = _grid.getHeight();

			var total = _data.length||1;
			var titleDiv = document.getElementById(_id + "_title");
			var gridDiv = document.getElementById(_id);
			var width = gridDiv.clientWidth;
			if(height < total * _rowHeight){
				width -= 17;
			}
			handleWidths(width, _fixed_column_width, _columns);
			handleWidths(width, _fixed_child_column_width, _childColumns);
			handleRowWidth();
			titleDiv.style.width = width + "px";
			
			var maxScrollTop = total * _rowHeight - height;
			scrollTop = scrollTop < maxScrollTop ? scrollTop : maxScrollTop;
			_indexStart = Math.floor((scrollTop - height / 4) / _rowHeight);
			_indexStart = _indexStart > 0 ? _indexStart : 0;
			_indexEnd = _indexStart + _pageSize;
			_indexEnd = _indexEnd < total ? _indexEnd : total;
			var areaId = "Area_" + _id + "_" + new Date().getTime();
			var size = _indexEnd - _indexStart;
			var strs = new Array;
			strs.push("<div id=\"ImGridContent_");
			strs.push(_id);
			strs.push("\" style=\"height:");
			strs.push(total*_rowHeight);
			strs.push("px;width:");
			strs.push(_rowWidth);
			strs.push("px\" class=\"");
			strs.push(_classNamePrefix);
			strs.push("data_content\">");
			if(_indexStart > 0){
				strs.push("<div id=\"TopBlankArea_");
				strs.push(_id);
				strs.push("\" style=\"height:");
				strs.push(_indexStart * _rowHeight);
				strs.push("px;width:80%\"></div>");
			}
			if(_data&&_data.length>0){
				display_area_html(strs, areaId, size, _indexStart, _indexEnd);
			}else{
				strs.push('<div class="font" style="text-align:center;padding-top:5px;">'+options.emptyTips+'</div>');
			}
			strs.push("</div>");
			var content = document.getElementById("ImGridContent_" + _id);
			content.outerHTML = strs.join("");
			content = null;
			_firstArea = document.getElementById(areaId);
			_firstAreaSize = size;
			_lastArea = null;
			_lastAreaSize = 0;
			
			_titleRowWidth = _rowWidth;
			
			if(reRenderTitle != false){
				var titleRow = document.getElementById(_id + "_title_row");
				titleRow.outerHTML = create_title_html().join("");
			}
		}
		
		//获取对象的左边距
		function getOffsetLeft(obj){
//			console.log(obj,obj == document.body);
			if(obj == document.body){
				return 0;
			}else{
				return obj.offsetLeft + getOffsetLeft(obj.parentNode);
			}
		}
		
		//根据类型查找父节点中最近的节点
		function findNode(node, type){
			if(node == null || node.getAttribute("_type") == "grid"){
				return null;
			}
			if(node.getAttribute("_type") == type){
				return node;
			}
			return findNode(node.parentNode, type);
		}

		//处理展开合拢节点
		function open_close(index){
			var row = _data[index];
			var children = row.children;
			var len = children.length;
			if(children){
				if(row.open){
					row.open = false;
					_data.splice(index + 1, len);
				}else{
					row.open = true;
					for(var i = len; i > 0; i--){
						_data.splice(index + 1, 0, children[i - 1]);
					}
				}
			}
		}
		//格式化金额
		function money(s, defaultValue) { 
			if(s == null){
				return defaultValue || "";
			}
			var n = 2;   
			s = (s + "").replace(/[^\d\.-]/g, "");
			if(s == "" || s == "-"){
				return defaultValue || "";
			}
			s = parseFloat(s).toFixed(n) + "";  
			var ss =  s.split(".");
			var l =ss[0].split("").reverse();  
			var r = ss[1];   
			var t = new Array;   
			for(var i = 0; i < l.length; i++ ){   
				t.push(l[i]);
				if((i + 1) % 3 == 0 && (i + 1) != l.length && l[i+1] != "-"){
					t.push(",");
				}
			}
			t = t.reverse();
			t.push(".");
			t.push(r);
			return t.join("");   
		} 

		//Clone
		function clone(obj){
			var objClone;    
			if (obj.constructor == Object){
				objClone = new obj.constructor();     
			}else{        
				objClone = new obj.constructor(obj.valueOf());     
			}    
			for(var key in obj){
				if (!/(open|buttons|parent|sn)/.test(key) && objClone[key] != obj[key] ){             
					if ( typeof(obj[key]) == 'object' ){                 
						objClone[key] = clone(obj[key]);            
					}else{                
						objClone[key] = obj[key];            
					}        
				}    
			}    
			return objClone; 
		} 

		//字段对象
		function Column(title, name, type, viewer, editor, checker, width, align){
			type = getType(type, name, editor);
			align = getAlign(align, type);
			viewer = getViewer(viewer);
			var rawWidth = width;
			var editable = typeof editor != "undefined" && editor != null;
			this.content = function(strs, item, index, colIndex){
				if(editable && item.editable){
					this.editor(strs, item, index, colIndex);
					return;
				}
				var value = item[name];
				strs.push("<div class=\"font ");
				strs.push(_classNamePrefix);
				strs.push("col ");
				strs.push(_classNamePrefix);
				strs.push("col_");
				strs.push(name);
				if(type == "icon"){
					strs.push(" ");
					strs.push(_classNamePrefix);
					strs.push("col_");
					strs.push(name);
					strs.push("_");
					strs.push(value);
				}
				strs.push("\" _type=\"col\" _name=\"");
				strs.push(name);
				strs.push("\" _colIndex=\"");
				strs.push(colIndex);
				strs.push("\" title=\"");
				if(type == "money"){
					strs.push(money(value));
				}if(type == "buttons"){
					strs.push("");
				}else if(typeof viewer == "function"){
					var obj = viewer(item, value);
					strs.push(obj.title);
				}else{
					strs.push(viewer[value]);
				}
				strs.push("\" style=\"height:");
				strs.push(_rowHeight - 1);
				strs.push("px;width: ");
				strs.push(width);
				strs.push("px;text-align: ");
				strs.push(align);
				
				if($.isFunction(_onRowActived)){
					strs.push(";cursor: pointer;");
				}
				
				strs.push("\">");
				switch(type){
					case "icon":
						break;
					case "money":
						strs.push(money(value));
						break;
					case "checkbox":
						display_checkbox_html(strs, type, value, false);
						break;
					case "_checkbox":
						display_checkbox_html(strs, type, item.checked, true);
						break;
					case "buttons":
						display_buttons_html(strs, value);
						break;
					case "combobox":
						strs.push(value);
						break;
					default:
						if(typeof viewer == "function"){
							var obj = viewer(item, value);
							strs.push(obj.value);
						}else{
							strs.push(viewer[value]);
						}
				}
				strs.push("</div>");
			}

			this.title = function(strs, index){
				strs.push("<div class=\"font_weight ");
				strs.push(_classNamePrefix);
				strs.push("title ");
				strs.push(_classNamePrefix);
				strs.push("col ");
				strs.push(_classNamePrefix);
				strs.push("col_");
				strs.push(name);
				strs.push("\" _type=\"title\" _name=\"");
				strs.push(name);
				strs.push("\" _colIndex=\"");
				strs.push(index);
				strs.push("\" style=\"height:100%;width: ");
				strs.push(width);
				strs.push("px;text-align: ");
				strs.push(align);
				strs.push("\">");
				if(type == "_checkbox"){
					strs.push("<input id=\""+_id+"checkbox\" type=\"checkbox\">");
				}else{
					strs.push(title);
				}
				strs.push("</div>");
			}

			this.editor = function(strs, item, index,colIndex){
				var value = item[name];
				strs.push("<div class=\"font ");
				strs.push(_classNamePrefix);
				strs.push("col ");
				strs.push(_classNamePrefix);
				strs.push("col_");
				strs.push(name);
				strs.push(" ");
				strs.push(_classNamePrefix);
				strs.push("col_");
				strs.push(name);
				strs.push("_editor");
				if(type == "icon"){
					strs.push(" ");
					strs.push(_classNamePrefix);
					strs.push("col_");
					strs.push(name);
					strs.push("_");
					strs.push(value);
				}
				strs.push("\" _type=\"col\" _name=\"");
				strs.push(name);
				strs.push("\" _colInde\"");
				strs.push(colIndex);
				strs.push("\" _editor=\"");
				strs.push(type);
				strs.push("\" style=\"width:");
				strs.push(width);
				strs.push("px;height:");
				strs.push(_rowHeight - 1);
				strs.push("px\">");
				var editorId = _id + "_" + index + "_" + name + "_editor";
				switch(type){
					case "money":	//input but money
						strs.push("<input id=\"");
						strs.push(editorId);
						strs.push("\" type=\"text\" maxlength=\"16\" class=\"");
						strs.push(_classNamePrefix);
						strs.push("editor ");
						strs.push(_classNamePrefix);
						strs.push("money_editor\"");
						strs.push(" value=\"");
						strs.push(money(value, "0.00"));
						strs.push("\" _editor=\"money\" _name=\"");
						strs.push(name);
						strs.push("\" style=\"text-align: ");
						strs.push(align);
						strs.push(";height:");
						strs.push(_rowHeight - 3);
						strs.push("px;width:");
						strs.push(width - 2);
						strs.push("px\">");
						break;
					case "checkbox"://already is select
						display_checkbox_html(strs, type, value, true);
						break;
					case "select":
						strs.push("<select id=\"");
						strs.push(editorId);
						strs.push("\" class=\"");
						strs.push(_classNamePrefix);
						strs.push("editor ");
						strs.push(_classNamePrefix);
						strs.push("select_editor\" _editor=\"select\" _name=\"");
						strs.push(name);
						strs.push("\" style=\"height:");
						strs.push(_rowHeight - 1);
						strs.push("px;width:");
						strs.push(width);
						strs.push("px\">");
						var editorOptions = item["__" + name + "_options"];
						editorOptions = editorOptions || editor;
						for(var i = 0; i < editorOptions.length; i++){
							strs.push("<option value=\"");
							strs.push(editorOptions[i]);
							strs.push("\"");
							if(editorOptions[i] == value){
								strs.push(" selected");
							}
							strs.push(">");
							if(typeof viewer == "function"){
								strs.push(viewer(item, editorOptions[i]));
							}else{
								strs.push(viewer[editorOptions[i]]);
							}
							strs.push("</option>");
						}
						strs.push("</select>");
						break;
					case "combobox":
						strs.push("<input id=\"");
						strs.push(_id);
						strs.push("_");
						strs.push(index);
						strs.push("_");
						strs.push(name);
						strs.push("_editor\" type=\"text\" class=\"");
						strs.push(_classNamePrefix);
						strs.push("editor ");
						strs.push(_classNamePrefix);
						strs.push("combobox_editor\"");
						strs.push(" value=\"");
						strs.push(value);
						strs.push("\" _editor=\"combobox\" _type=\"combobox_editor_input\" _name=\"");
						strs.push(name);
						strs.push("\" style=\"text-align: ");
						strs.push(align);
						strs.push(";height:");
						strs.push(_rowHeight - 3);
						strs.push("px;width:");
						strs.push(width - 17);
						strs.push("px;\"><div _type=\"combobox_editor_button\" class=\"");
						strs.push(_classNamePrefix);
						strs.push("combobox_button\" _name=\"");
						strs.push(name);
						strs.push("\"></div>");
						break;
					case "function":
						strs.push("<div id=\"");
						strs.push(_id);
						strs.push("_");
						strs.push(index);
						strs.push("_");
						strs.push(name);
						strs.push("_editor\" class=\"");
						strs.push(_classNamePrefix);
						strs.push("editor ");
						strs.push(_classNamePrefix);
						strs.push("function_editor\"");
						strs.push(" _editor=\"function\" _type=\"editor\" _name=\"");
						strs.push(name);
						strs.push("\" style=\"text-align: ");
						strs.push(align);
						strs.push(";height:");
						strs.push(_rowHeight - 3);
						strs.push("px;width:");
						strs.push(width - 17);
						strs.push("px\" title=\"");
						var val = null;
						if(typeof viewer == "function"){
							val = viewer(item, value);
						}else{
							val = viewer[value];
						}
						strs.push(val);
						strs.push("\">");
						strs.push(val)
						strs.push("</div><div _type=\"function_editor_button\" class=\"");
						strs.push(_classNamePrefix);
						strs.push("function_button\" _name=\"");
						strs.push(name);
						strs.push("\"></div>");
						break;
					default:
						strs.push("<input id=\"");
						strs.push(_id);
						strs.push("_");
						strs.push(index);
						strs.push("_");
						strs.push(name);
						strs.push("_editor\" type=\"text\" class=\"");
						strs.push(_classNamePrefix);
						strs.push("editor ");
						strs.push(_classNamePrefix);
						strs.push("text_editor\"");
						strs.push(" value=\"");
						strs.push(value);
						strs.push("\" _editor=\"text\" _name=\"");
						strs.push(name);
						strs.push("\" style=\"text-align: ");
						strs.push(align);
						strs.push(";height:");
						strs.push(_rowHeight - 3);
						strs.push("px;width:");
						strs.push(width - 2);
						strs.push("px\">");
				}
				strs.push("</div>");
			}

			this.check = function(item, value){
				if(typeof checker == "object" && checker instanceof RegExp){
					return checker.test(value);
				}
				if(typeof checker == "function"){
					return checker(item, value);
				}
				return true;
			}

			this.getEditor = function(){
				return editor;
			}

			this.setWidth = function(value){
				width = value;
			}

			this.getWidth = function(){
				return width;
			}

			this.getRawWidth = function(){
				return rawWidth;
			}
			
			//生成显示按钮HTML
			function display_buttons_html(strs, buttons){

				if (buttons == null) {
					return;
				}
				var len = buttons.length;
				var btn;
				for (var i = 0; i < len; i++) {
					btn = buttons[i];
					strs.push("<div class=\"");
					strs.push(_classNamePrefix);
					strs.push("button ");
					strs.push(btn.cls);
					strs.push("\" _name=\"");
					strs.push(btn.cls);
					strs.push("\" title=\"");
					strs.push(btn.value);
					strs.push("\" _type=\"button\" style=\"height:");
					strs.push(_rowHeight - 1);
					strs.push("px\"></div>");
				}
			
			}
			//生成Checkbox HTML
			function display_checkbox_html(strs, type, value, editable){
				strs.push("<input _type=\"");
				strs.push(type);
				strs.push("\" type=\"checkbox\"");
				if(Boolean(value) && !/^(0|false|off)$/i.test(value)){
					strs.push(" checked");
				}
				if(editable){
					strs.push(" _editor=\"checkbox\" _name=\"");
					strs.push(name);
					strs.push("\"");
				}else{
					strs.push(" disabled");
				}
				strs.push(">");
			}

			//获取字段对齐方式
			function getAlign(align, type){
				if(/(center|left|right)/.test(align)){
					return align;
				}else{
					if(type == "money"){
						return "right";
					}else if(/(checkbox|_checkbox|icon)/.test(type)){
						return "center";
					}else{
						return "left";
					}
				}
			}

			//获取字段类型，处理默认字段的类型等
			function getType(type, name, editor){
				if(/(checkbox|_checkbox|icon|text|select|combobox|buttons|money|number)/.test(type)){
					return type;
				}else{
					if(name == "checkbox"){
						return "_checkbox";
					}else if(name == "open"){
						return "icon";
					}else if(name == "buttons"){
						return "buttons";
					}else if(editor instanceof Array){
						return "select";
					}else if(editor === "function"){
						return "function";
					}else{
						return "text";
					}
				}
			}

			function getViewer(viewer){
				if(typeof viewer === "function" || typeof viewer === "object"){
					return viewer;
				}
				return function(item, value){
					return {
						title:value,
						value:value
					};
				};
			}
		}
		return this;
	};
})(jQuery);

