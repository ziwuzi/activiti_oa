<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!--Start Breadcrumb-->
<div class="row">
	<div id="breadcrumb" class="col-xs-12">
	<a href="#" class="show-sidebar">
						  <i class="fa fa-bars"></i>
						</a>
		<ol class="breadcrumb pull-left">
			<li><a href="index">首页</a></li>
			<li><a href="#">采购管理</a></li>
			<li><a href="#">申请采购</a></li>
		</ol>
	</div>
</div>
<div class="container-fluid">
	<div class="row">
		<div class="col-lg-12">
			<div class="box ui-draggable ui-droppable" id="dept">
				<div class="box-header">
					<div class="box-name">
						<i class="fa fa-coffee"></i> <span>填写采购申请</span>
					</div>
					<div class="box-icons">
						<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
						</a> <a class="expand-link"> <i class="fa fa-expand"></i>
						</a> <a class="close-link"> <i class="fa fa-times"></i>
						</a>
					</div>
					<div class="no-move"></div>
				</div>
				<div class="box-content">
					<form role="form" action="startleave" method="post">
						<div class="form-group has-feedback">
							<label class="control-label">物品清单</label> 
							<textarea id="itemlist" rows="6" class="form-control" name="itemlist" placeholder="物品清单"></textarea>
						</div>
						<div class="form-group has-feedback">
							<label>总金额(元)</label> <input id="total" class="form-control" name="total" placeholder="总金额">
						</div>
						<button id="btn" type="button" class="btn btn-primary">开始申请</button>
						<button type="reset" class="btn btn-primary">重置</button>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
$(document).ready(function(){
	$("#btn").click(function(){
		if($("#itemlist").val() == ""){
			$.MsgBox.Alert("消息","物品清单不能为空！");
			return false;
		}
		if($("#total").val() == ""){
			$.MsgBox.Alert("消息","总金额不能为空！");
			return false;
		}
		$.MsgBox.Confirm("确认","是否申请？",function () {
			$.post("startpurchase", $("form").serialize(), function () {
				$.MsgBox.Alert("消息", "申请成功");
				LoadAjaxContent("purchase");
			});
		});
	});
});

</script>
