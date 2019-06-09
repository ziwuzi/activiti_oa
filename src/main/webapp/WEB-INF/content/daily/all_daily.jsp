<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!--Start Breadcrumb-->
<div class="row">
	<div id="breadcrumb" class="col-xs-12">
		<a href="#" class="show-sidebar">
			<i class="fa fa-bars"></i>
		</a>
		<ol class="breadcrumb pull-left">
			<li><a href="index">首页</a></li>
			<li><a href="#">日报管理</a></li>
			<li><a href="#">员工日报</a></li>
		</ol>
	</div>
</div>
<div class="container-fluid">
	<div class="row">
		<div class="col-lg-12">
			<div class="box ui-draggable ui-droppable">
				<div class="box-header">
					<div class="box-name">
						<i class="fa fa-coffee"></i> <span>员工日报</span>
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
							<th data-column-id="userName">姓名</th>
							<th data-column-id="date">日期</th>
							<th data-column-id="dailyContent">工作内容</th>
							<th data-column-id="plan">明日计划</th>
							<th data-column-id="feedback">反馈</th>
							<th data-column-id="comment">评语</th>
							<th data-formatter="commands">操作</th>
						</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-lg-12">
			<div class="box ui-draggable ui-droppable" id="dept">
				<div class="box-header">
					<div class="box-name">
						<i class="fa fa-search"></i> <span>明细</span>
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
					<form role="form" action="" method="post">
						<div class="form-group">
							<label>编号</label>
							<input class="form-control" id="dailyId" readonly="readonly">
						</div>
						<div class="form-group">
							<label>姓名</label>
							<input class="form-control" id="userName" readonly="readonly">
						</div>
						<div class="form-group">
							<label>日期</label>
							<input class="form-control" id="date" readonly="readonly">
						</div>
						<div class="form-group">
							<label>工作内容</label>
							<textarea class="form-control" id="dailyContent" readonly="readonly"></textarea>
						</div>
						<div class="form-group">
							<label>明日计划</label>
							<textarea class="form-control" id="plan" readonly="readonly"></textarea>
						</div>
						<div class="form-group">
							<label>反馈</label>
							<textarea class="form-control" id="feedback" readonly="readonly"></textarea>
						</div>
						<div class="form-group">
							<label>评语</label>
							<textarea class="form-control" id="comment" placeholder="评语"></textarea>
						</div>
						<button id="commentBtn" type="button" class="btn btn-default">评价</button>
						<button id="btn" type="button" class="btn btn-default" onclick="$('#dept').hide();">关闭</button>
					</form>
				</div>
			</div>

		</div>
	</div>
</div>



<script type="text/javascript">
	var rows = [];
	$(document).ready(function(){
		$("#dept").hide();
		rows = [];
		var grid=$("#grid-data").bootgrid({
			navigation:2,
			columnSelection:false,
			ajax:true,
			url:"daily/get_all_daily",
			formatters: {
				"commands": function(column, row)
				{
					rows.push(row);
					return "<button class=\"btn btn-xs btn-default ajax-link\" onclick=show(\"" + row.id + "\")>查看</button>";
				}
			}

		}).on("loaded.rs.jquery.bootgrid", function()
		{
		});
	});

	function show(id){
		for (let i = 0; i < rows.length; i++) {
			let row = rows[i];
			if(row.id == id){
				console.log(row);
				$("#dailyId").val(row.id);
				$("#userName").val(row.userName);
				$("#date").val(row.date);
				$("#dailyContent").val(row.dailyContent);
				$("#plan").val(row.plan);
				$("#feedback").val(row.feedback);
				$("#comment").val(row.comment);
				$("#commentBtn").click(function () {
					if($("#comment").val() == ""){
						$.MsgBox.Alert("消息","评语不能为空！");
						return false;
					}
					if($("#comment").val() == row.comment){
						$.MsgBox.Alert("消息","评语未修改！");
						return false;
					}
					$.MsgBox.Confirm("确认","是否评价？",function () {
						$.post("daily/comment/" + id, {"comment": $("#comment").val()}, function (data) {
							$.MsgBox.Alert("消息", "处理成功", function () {
								LoadAjaxContent("daily/all_daily");
							});
						});
					});
				});
				$("#dept").show();
			}
		}
	}
</script>
