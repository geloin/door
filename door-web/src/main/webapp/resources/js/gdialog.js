function GeloinDialog(){
		
	this.alert = function(msg) {
		var alertConfig = {
			title : '警告！',
			content : msg,
			width : 300,
			height : 80,
			lock : true,
			skin : 'alertDialog',
			button: [
	             {
	                 value: '关闭',
	                 callback: function () {
	                     return true;
	                 },
	                 focus: true
	             }
	         ]
		};
		dialog(alertConfig).show();
	};
	
	this.open = function(url, title, config) {
		config.url = url;
		config.title = title;
		config.lock = true;
		config.okValue = '确定';
		config.cancelValue = '取消';
		if (!config.cancel) {
			config.cancel = true;
		}
		dialog(config).showModal();
	};
		
};
