<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!--Start Breadcrumb-->
<div class="row">
	<div id="breadcrumb" class="col-xs-12">
	<a href="#" class="show-sidebar">
						  <i class="fa fa-bars"></i>
						</a>
		<ol class="breadcrumb pull-left">
			<li><a href="index">首页</a></li>
			<li><a href="#">人事管理</a></li>
			<li><a href="#">我要请假</a></li>
		</ol>
	</div>
</div>
<div class="container-fluid">
	<div class="row">
		<div class="col-lg-12">
			<div class="box ui-draggable ui-droppable" id="dept">
				<div class="box-header">
					<div class="box-name">
						<i class="fa fa-coffee"></i> <span>填写申请</span>
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
						<div class="form-group">
							<label>请假类型</label> <select name="leave_type"
								class="form-control">
								<option value="事假">事假</option>
								<option value="病假">病假</option>
								<option value="年假">年假</option>
								<option value="丧假">丧假</option>
								<option value="产假">产假</option>
							</select>
						</div>
						<div class="form-group has-feedback">
							<label class="control-label">开始时间</label> 
							<input id="start" class="form-control" name="start_time" placeholder="开始时间"><span class="fa fa-calendar txt-danger form-control-feedback"></span>
						</div>
						<div class="form-group has-feedback">
							<label>结束时间</label> <input id="end" class="form-control" name="end_time"
								placeholder="结束时间"><span class="fa fa-calendar txt-danger form-control-feedback"></span>
						</div>
						<div class="form-group">
							<label>请假原因</label>
							<textarea class="form-control" name="reason" rows="3" id="reason"></textarea>
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
		if($("#start").val() == "" || $("#end").val() == ""){
			$.MsgBox.Alert("消息","请假时间不能为空！");
			return false;
		}
		if($("#reason").val() == ""){
			$.MsgBox.Alert("消息","请假理由不能为空！");
			return false;
		}
		$.MsgBox.Confirm("确认","是否申请？",function () {
			$.post("startleave", $("form").serialize(), function (data) {
				if(data == "success") {
					$.MsgBox.Alert("消息", "申请成功", function () {
						LoadAjaxContent("leave/to_my_leave");
					});
				}
				else if(data == "exist"){
					$.MsgBox.Alert("错误", "申请失败，当前时间段已存在申请！");
				}
				else{
					$.MsgBox.Alert("错误", "申请失败！");
				}
			});
		});
	});

	$('#start').datepicker({
		setDate: new Date(),
		dateFormat:'yy-mm-dd',
		onSelect: function( startDate ) {
			let $startDate = $( "#start" );
			let $endDate = $('#end');
			let endDate = $endDate.datepicker( 'getDate' );
			if(endDate < startDate){
				$endDate.datepicker('setDate', startDate - 3600*1000*24);
			}
			$endDate.datepicker( "option", "minDate", startDate );
		}
	});
	$('#end').datepicker({
		setDate: new Date(),
		dateFormat:'yy-mm-dd',
		onSelect: function( endDate ) {
			let $startDate = $( "#start" );
			let $endDate = $('#end');
			let startDate = $startDate.datepicker( "getDate" );
			if(endDate < startDate){
				$startDate.datepicker('setDate', startDate + 3600*1000*24);
			}
			$startDate.datepicker( "option", "maxDate", endDate );
		}
	});
	
});

</script>
