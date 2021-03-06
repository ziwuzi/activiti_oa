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
			<li><a href="#">我的考勤</a></li>
		</ol>
	</div>
</div>
<div class="container-fluid">

	<div class="row">
		<div class="col-lg-12">
			<div class="box ui-draggable ui-droppable">
				<div class="box-header">
					<div class="box-name">
						<i class="fa fa-coffee"></i> <span>我的考勤</span>
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
			url:"attence/my_attence",
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

	});


</script>
