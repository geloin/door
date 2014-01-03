var jqGridJs = jsDir + 'jquery.jqGrid.min.js';
var jqGridCNJs = jsDir + 'grid.locale-cn.js';
var userFuncJs = jsDir + 'pages/admin/menu/listUserFunc.js';

var jqGridCss = cssDir + 'ui.jqgrid.css';

var includeJSFiles = new Array(jqGridJs, jqGridCNJs);
var includeCssFiles = new Array(jqGridCss);

loadResource(includeCssFiles, includeJSFiles, userFuncJs)
