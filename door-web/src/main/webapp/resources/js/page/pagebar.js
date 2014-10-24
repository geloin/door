function Pagebar(options){
	//判断是否是number类型
	function isNumber(v) {
		return typeof v === 'number' && isFinite(v);
	}
	//每页条数，缺省值12
	var pageSize = options.pageSize||12;
	//总数量，缺省值0
	var total = options.total||0;
	//当前页，缺省值1
	var currentPage = options.currentPage||1;
	//渲染的目标对象
	var node = options.node;
	//文本框的ID
	var inputId = 'text_'+options.node;
	var me = this;
	//总的页数
	var countPage = 1;
	//绑定点击按钮的点击事件
	$(node).bind('click',function(e){
		var ta = $(e.target);
		if(ta.attr("dataIndex")){
			var pageIndex = ta.attr("dataIndex");
			if(pageIndex == -1){
				pageIndex = parseInt(document.getElementById(inputId).value,10);
			}
			pageIndex = parseInt(pageIndex,10);
			if ((isNumber(pageIndex))&&(pageIndex<=countPage)) {
				options.goFunction.call(this, pageIndex);
			}
			
		}
	});
	
	//获取分页信息
	this.getPage = function(){
		if(total%pageSize==0){
			countPage = total/pageSize;
		}else{
			countPage = parseInt(total/pageSize)+1;
		}
		return {
			countPage:countPage,
			currentPage:currentPage,
			pageSize:pageSize
		};
	};

	//渲染分页条
	this.render = function(){
		if(total!=0){
			var page = me.getPage();
			var beforeHtml = '';
			var nextHtml = '';
			var beforeSize = 2;
			var nextSize = 2;
			
			var nextLimit = page.countPage-page.currentPage;
			var beforeLimit = page.currentPage-1;
			if(beforeLimit<=2){
				beforeSize = beforeLimit;
				nextSize = nextSize + 2 - beforeLimit;
				if(nextSize>nextLimit){
					nextSize = nextLimit;
				}
			}else if(nextLimit<2&&beforeLimit>2){
				nextSize = nextLimit;
				beforeSize = beforeSize + 2 - nextLimit;
				if(beforeSize>beforeLimit){
					beforeSize = beforeLimit;
				}
			}
			
			if(page.currentPage!=1){
				var p = page.currentPage - 1;
				beforeHtml = beforeHtml + '<li class="nextPage" dataIndex='+p+'>«</li>';
			}
			for(var i=0;i<beforeSize;i++){
				var size = page.currentPage-beforeSize+i;
				beforeHtml = beforeHtml+'<li class="paging" dataIndex='+size+'>'+size+'</li>';
			}
			var currentHtml = '<li class="paging_pitch">'+page.currentPage+'</li>';
			for(var i=0;i<nextSize;i++){
				var size = page.currentPage+1+i;
				nextHtml = nextHtml+'<li class="paging" dataIndex='+size+'>'+size+'</li>';
			}
			if(page.currentPage!=page.countPage){
				var q = page.currentPage + 1;
				nextHtml = nextHtml + '<li class="nextPage" dataIndex='+q+'>»</li>';
			}
			
			var totalHtml = '<li class="allPage">共'+page.countPage+'页</li>';
			
			var goHtml = '<li class="go_area"><input id="'+inputId+'" type="text" class="go_left"/><span class="go_right" dataIndex=-1>Go<span></li>';
			
			var pageHtml = '<ul>'+beforeHtml+currentHtml+nextHtml+totalHtml+goHtml+'</ul>';
			
			$(node).html(pageHtml);
		}
	};
	
	//销毁分页条
	this.destory = function(){
		$(node).html('');
	};

	//重置分页条
	this.resize = function(){
		me.destory();
		me.render();
	};
	
	//重新加载分页条
	this.reload = function(opt) {
		currentPage = opt.currentPage||1;
		total = opt.total;
		me.resize();
	};
	
	//设置当前页
	this.setCurrentPage = function(currPage){
		currentPage = parseInt(currPage,10);
		me.resize();
	};
	
	//获取当前页
	this.getCurrentPage = function(){
		return currentPage;
		};
		
		this.setTotal = function(totalCount){
		total = totalCount;
		};

	//获取总数
	this.getTotal = function(){
		return total;
	};
};