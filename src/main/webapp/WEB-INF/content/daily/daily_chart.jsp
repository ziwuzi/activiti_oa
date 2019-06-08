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
			<li><a href="#">未写日报统计</a></li>
		</ol>
	</div>
</div>
<div class="container-fluid">
	<div class="row">
		<div class="col-lg-12">
			<div class="box ui-draggable ui-droppable">
				<div class="box-header">
					<div class="box-name">
						<i class="fa fa-coffee"></i> <span>查询表单</span>
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
					<form class="form-horizontal" role="form">
						<h4 class="page-header">查询条件</h4>
						<div class="form-group">
							<label class="col-xs-2 control-label">开始时间：</label>
							<div class="col-xs-4">
								<input id="start" class="form-control" name="start" placeholder="开始时间">
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-2 control-label">结束时间：</label>
							<div class="col-xs-4">
								<input id="end" class="form-control" name="end" placeholder="结束时间">
							</div>
						</div>
						<div class="form-group">
							<div class="col-xs-2 col-xs-offset-2">
								<button type="button" class="btn btn-primary btn-label-left" id="queryBtn">
									<span><i class="fa fa-clock-o"></i></span>
									查询
								</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-lg-12">
			<div class="box ui-draggable ui-droppable">
				<div class="box-header">
					<div class="box-name">
						<i class="fa fa-coffee"></i> <span>未写日报统计</span>
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
							<th data-column-id="date">日期</th>
							<th data-column-id="count">人数</th>
							<th data-column-id="users">姓名</th>
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
		$('#queryBtn').click(function () {
			$("#grid-data").bootgrid("destroy");
			$("#grid-data").bootgrid({
				navigation:2,
				columnSelection:false,
				ajax:true,
				url:"daily/daily_chart",
				post:{'start':$('#start').val(),'end':$('#end').val()},
				formatters: {
				}

			}).on("loaded.rs.jquery.bootgrid", function()
			{
			});
		});
		$('#queryBtn').click();
	});
</script>
