<%@page contentType="text/html; charset=UTF-8"%>
<jsp:include page="../common/bootstrap.jsp" />

<form id="editForm" name="editForm" method="post"
	class="form-horizontal">
	<input type="hidden" name="id" id="id" value="${data.id }" />
	<input type="hidden" name="sort" id="sort" value="${data.sort }" />
	<div class="row-fluid">
		<div class="span5">
			<div class="control-group">
				<label class="control-label" for="name"><i
					style="color: red">*&nbsp;</i>菜单名称：</label>
				<div class="controls">
					<input id="name" name="name" type="text" value="${data.name }" />
				</div>
			</div>
		</div>
	</div>
	<div class="row-fluid">
		<div class="span5">
			<div class="control-group">
				<label class="control-label" for="url"><i style="color: red">*&nbsp;</i>服务地址：</label>
				<div class="controls">
					<input id="url" name="url" type="text" value="${data.url }" />
				</div>
			</div>
		</div>
	</div>

</form>


<script type="text/javascript">
function getParams() {
	var id = $('#id').val();
	var name = $('#name').val();
	var url = $('#url').val();
	var sort = $('#sort').val();
	
	var o = {};
	o.id = id;
	o.name = name;
	o.url = url;
	o.sort = sort;
	
	return o;
}

var addValidateObj = $('#editForm').validate({
	onkeyup:false,	
	rules: {
		name: {
			required: true,
			maxlength:100
		},
		url: {
			required: true
		}
	},
	messages: {
		name: {
			required: "请输入菜单名称",
			maxlength:"只能输入100个字符或者汉字"
		},
		url: {
			required: "请输入服务地址"
		}
	}
});
</script>
