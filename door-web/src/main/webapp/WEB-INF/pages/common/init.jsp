<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="door" uri="door"%>

<link href="<door:ctx/>resources/css/bootstrap.min.css" rel="stylesheet"
	media="screen" />
<link href="<door:ctx/>resources/css/navigation.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css"
	href="<door:ctx/>resources/css/ggrid.css" />
<link rel="stylesheet" type="text/css"
	href="<door:ctx/>resources/css/ui-dialog.css" />
<link rel="stylesheet" type="text/css"
	href="<door:ctx/>resources/js/page/pagebar.css" />

<script type="text/javascript"
	src="<door:ctx/>resources/js/jquery-1.10.2.min.js"></script>
<script src="<door:ctx/>resources/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<door:ctx/>resources/js/ggrid.js"></script>
<script type="text/javascript"
	src="<door:ctx/>resources/js/dialog-min.js"></script>
<script type="text/javascript"
	src="<door:ctx/>resources/js/dialog-plus-min.js"></script>
<script type="text/javascript"
	src="<door:ctx/>resources/js/gdialog.js"></script>
<script type="text/javascript"
	src="<door:ctx/>resources/js/page/pagebar.js"></script>

<script type="text/javascript">
	var ctx = '<door:ctx />';
</script>

<nav role="navigation" id="navigation"
	class="sort-pages modify-pages navbar site-navigation">
	<div class="navbar-inner navbar-door">
		<div class="collapse nav-collapse">
			<ul role="menubar" class="nav nav-collapse" aria-label="Site Pages">
				<li role="presentation" aria-selected="true" id="layout_1"
					class="lfr-nav-item selected active"><a role="menuitem"
					href="http://192.168.1.84:9080/group/stysdw/index" class=""
					aria-labelledby="layout_1"> <span class="txt-door"> 首页 </span>
				</a></li>
				<li role="presentation" aria-labelledby="layout_2" id="layout_2"
					class="lfr-nav-item"><a role="menuitem"
					href="http://192.168.1.84:9080/group/stysdw/-1" class=""> <span
						class="txt-door"> 财政业务 </span>
				</a></li>
				<li role="presentation" aria-labelledby="layout_2" id="layout_2"
					class="lfr-nav-item"><a role="menuitem"
					href="http://192.168.1.84:9080/group/stysdw/-1" class=""> <span
						class="txt-door"> 财政业务 </span>
				</a></li>
				<li role="presentation" id="layout_4" class="lfr-nav-item"><a
					role="menuitem" href="http://192.168.1.84:9080/group/stysdw/4"
					class="" aria-labelledby="layout_4"> <span> 通知公告 </span>
				</a></li>
				<li role="presentation" id="layout_5" class="lfr-nav-item"><a
					role="menuitem" href="http://192.168.1.84:9080/group/stysdw/5"
					class="" aria-labelledby="layout_5"> <span> 资料库 </span>
				</a></li>
				<li role="presentation" id="layout_6" class="lfr-nav-item"><a
					role="menuitem" href="http://192.168.1.84:9080/group/stysdw/6"
					class="" aria-labelledby="layout_6"> <span> 材料上报 </span>
				</a></li>
				<li role="presentation" id="layout_27" class="lfr-nav-item"><a
					role="menuitem" href="http://192.168.1.84:9080/group/stysdw/27"
					class="" aria-labelledby="layout_27"> <span> 业务通讯录 </span>
				</a></li>
				<li role="presentation" id="layout_8" class="lfr-nav-item"><a
					role="menuitem" href="http://192.168.1.84:9080/group/stysdw/8"
					class="" aria-labelledby="layout_8"> <span> 咨询问答 </span>
				</a></li>
				<li role="presentation" id="layout_9" class="lfr-nav-item dropdown">
					<a role="menuitem" href="http://192.168.1.84:9080/group/stysdw/-9"
					class="dropdown-toggle" aria-haspopup="true"
					aria-labelledby="layout_9" data-target="#" data-toggle="dropdown">
						<span> 系统管理</span>
				</a>
					<ul aria-labelledby="dLabel" role="menu"
						class="dropdown-menu child-menu">
						<li role="presentation" id="layout_21" class="lfr-nav-item">
							<a role="menuitem" href="<door:ctx/>admin/menu/list.html"
							aria-labelledby="layout_21">用户管理</a>
						</li>
						<li role="presentation" id="layout_22" class="lfr-nav-item">
							<a role="menuitem" href="<door:ctx/>menu/list.html"
							aria-labelledby="layout_22">菜单管理</a>
						</li>
					</ul>
				</li>
				<li role="presentation" id="layout_10" class="lfr-nav-item dropdown">
					<a role="menuitem" href="http://192.168.1.84:9080/group/stysdw/-13"
					class="dropdown-toggle" aria-haspopup="true"
					aria-labelledby="layout_10" data-target="#" data-toggle="dropdown">
						<span> 个人中心 </span>
				</a>
					<ul aria-labelledby="dLabel" role="menu"
						class="dropdown-menu child-menu">
						<li role="presentation" id="layout_23" class="lfr-nav-item">
							<a role="menuitem"
							href="http://192.168.1.84:9080/group/stysdw/-10"
							aria-labelledby="layout_23">修改密码</a>
						</li>
						<li role="presentation" id="layout_24" class="lfr-nav-item">
							<a role="menuitem"
							href="http://192.168.1.84:9080/group/stysdw/-14"
							aria-labelledby="layout_24">修改个人信息</a>
						</li>
						<li role="presentation" id="layout_25" class="lfr-nav-item">
							<a role="menuitem"
							href="http://192.168.1.84:9080/group/stysdw/messagebox"
							aria-labelledby="layout_25">消息盒子</a>
						</li>
					</ul>
				</li>
			</ul>
		</div>
	</div>
</nav>