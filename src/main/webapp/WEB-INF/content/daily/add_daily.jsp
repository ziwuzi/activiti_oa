<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!--Start Breadcrumb-->
<div class="row">
	<div id="breadcrumb" class="col-xs-12">
	<a href="#" class="show-sidebar">
						  <i class="fa fa-bars"></i>
						</a>
		<ol class="breadcrumb pull-left">
			<li><a href="index">首页</a></li>
			<li><a href="#">功能表</a></li>
			<li><a href="#">新增日报</a></li>
		</ol>
	</div>
</div>
<div class="container-fluid">
	<div class="row">
		<div class="col-lg-12">
			<div class="box ui-draggable ui-droppable" id="dept">
				<div class="box-header">
					<div class="box-name">
						<i class="fa fa-coffee"></i> <span>新增日报</span>
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
							<label class="control-label">日期</label>
							<input id="date" class="form-control" name="date" placeholder="开始时间"><span class="fa fa-calendar txt-danger form-control-feedback"></span>
						</div>
						<div class="form-group">
							<label>工作内容
								(工作情况描述、进展/结果、待解决问题等)：</label>
							<textarea class="form-control" name="dailyContent" rows="3"></textarea>
						</div>
						<div class="form-group">
							<label>明日/下周计划：</label>
							<textarea class="form-control" name="plan" rows="3"></textarea>
						</div>
						<div class="form-group">
							<label>需反馈/支持的事项或困难：</label>
							<textarea class="form-control" name="feedback" rows="3"></textarea>
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
		$.post("add_daily",$("form").serialize(),function(data){
			if(data=="success"){
				alert("申请成功");
				LoadAjaxContent("my_daily");
			}
		});
	});
	
	$('#date').datepicker({setDate: new Date(), dateFormat: 'yy-mm-dd'});
	
});

</script>
