<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!--Start Breadcrumb-->
<div class="row">
	<div id="breadcrumb" class="col-xs-12">
		<a href="#" class="show-sidebar">
			<i class="fa fa-bars"></i>
		</a>
		<ol class="breadcrumb pull-left">
			<li><a href="index">首页</a></li>
			<li><a href="#">考勤管理</a></li>
			<li><a href="#">员工考勤</a></li>
		</ol>
	</div>
</div>
<div class="container-fluid">
	<div class="row">
		<div class="col-lg-12">
			<div class="box ui-draggable ui-droppable">
				<div class="box-header">
					<div class="box-name">
						<i class="fa fa-coffee"></i> <span>上传考勤数据</span>
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
					<form id="upload" action="attence/upload_attence" method="post" enctype="multipart/form-data">
						<div class="form-group">
							<label for="fileupload">考勤数据</label>
							<input type="file" name="upload" class="form-control" id="fileupload">
						</div>
						<button type="submit" class="btn btn-primary">上传</button>
					</form>
				</div>
			</div></div>
	</div>
	<div class="row">
		<div class="col-lg-12">
			<div class="box ui-draggable ui-droppable">
				<div class="box-header">
					<div class="box-name">
						<i class="fa fa-coffee"></i> <span>员工考勤</span>
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
					<table id="grid-data" class="table table-condensed table-hover table-striped">
						<thead>
						<tr>
							<th data-column-id="id" data-identifier="true" data-type="numeric">编号</th>
							<th data-column-id="userId">用户id</th>
							<th data-column-id="date">日期</th>
							<th data-column-id="startTime">上班时间</th>
							<th data-column-id="offTime">下班时间</th>
							<th data-column-id="status">考勤状态</th>
							<th data-column-id="leaveInfo">请假信息</th>
							<th data-formatter="weekday">星期</th>
							<th data-column-id="remark">备注</th>
						</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
	</div>

</div>



<script type="text/javascript">
	$(document).ready(function(){
		$("#dept").hide();
		var grid=$("#grid-data").bootgrid({
			navigation:2,
			columnSelection:false,
			ajax:true,
			url:"attence/all_attence",
			formatters: {
				"weekday": function(column, row)
				{
					let weekdays = ['星期日','星期一','星期二','星期三','星期四','星期五','星期六'];
					return weekdays[row.weekday];
				}
			}

		}).on("loaded.rs.jquery.bootgrid", function()
		{
		});

		$("#upload").submit(function(){
			if($("#fileupload").val()=="")
			{
				$.MsgBox.Alert("消息","请选择考勤文件上传！");
				return false;
			}
		});
	});


</script>
