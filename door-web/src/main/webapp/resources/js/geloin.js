var i = 0;
var jsFiles;
var userJs;
var cssFiles;

var loadCssFiles = function() {
	// load css files
	$.each(cssFiles, function(i, file) {
		var cssFile = '<link rel="stylesheet" href="' + file
				+ '" type="text/css"/>';
		$('head').append(cssFile);
	});
}
var loadJsFiles = function(i) {
	if (i < jsFiles.length - 1) {
		// load js files
		$.getScript(jsFiles[i], function() {
			loadJsFiles(i + 1);
		});
	} else {
		// load userJs
		$.getScript(jsFiles[i], function() {
			$.getScript(userJs);
		});
	}
}

var loadResource = function(cssFiles, jsFiles, userJs) {
	// load css files
	this.cssFiles = cssFiles;
	this.jsFiles = jsFiles;
	this.userJs = userJs;
	loadCssFiles();
	loadJsFiles(0);
}