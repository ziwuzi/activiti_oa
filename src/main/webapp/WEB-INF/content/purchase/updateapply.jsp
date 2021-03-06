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
			<li><a href="#">调整申请</a></li>
		</ol>
	</div>
</div>       
            <div class="container-fluid">
                       <div class="row">
                    <div class="col-lg-12">
                        <div class="box ui-draggable ui-droppable">
				<div class="box-header">
					<div class="box-name">
						<i class="fa fa-coffee"></i> <span>调整申请</span>
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
							                <th data-column-id="applyer" data-identifier="true" data-type="numeric">申请人</th>
							                <th data-column-id="applytime" data-formatter="applytime">申请时间</th>
							                <th data-column-id="itemlist">申请内容</th>
							                <th data-column-id="total">总金额（元）</th>
							                <th data-column-id="taskid">任务ID</th>
							                <th data-column-id="taskname">任务名称</th>
							                <th data-column-id="processinstanceid" >流程实例ID</th>
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
						<i class="fa fa-search"></i> <span>调整申请</span>
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
							<textarea id="itemlist" rows="6" class="form-control" name="itemlist">
							</textarea>
						</div>
						<div class="form-group has-feedback">
							<label>总金额(元)</label> <input id="total" class="form-control" name="total" placeholder="总金额">
						</div>
						<div class="form-group">
						                <label>是否重新申请</label>
						                <div class="controls">
											<select name="updateapply">
												<option value="true">重新申请</option>
												<option value="false">不再申请</option>
											</select>
										</div>
						             </div> 
						<button id="btn" type="button" class="btn btn-primary">完成</button>
					</form>
				</div>
			</div>

		</div>
	</div>

</div>
            
            
            
    <script type="text/javascript">
    
    $(document).ready(function(){
	$('#startime').datepicker({setDate: new Date(), dateFormat: 'yy-mm-dd'});
	$('#endtime').datepicker({setDate: new Date(), dateFormat: 'yy-mm-dd'});
    
    	$("#dept").hide();
	    var grid=$("#grid-data").bootgrid({
	    	navigation:2,
  			columnSelection:false,
		    ajax:true,
		    url:"updatepurchaseapply",
		    formatters: {
		    "applytime":function(column, row){
		    	return getLocalTime(row.applytime);
		    },
		    "commands": function(column, row)
		    {
		            return "<button class=\"btn btn-xs btn-default ajax-link command-run1\" data-row-total="+row.total+" data-row-itemlist="+row.itemlist+" data-row-id=\"" + row.taskid + "\">处理</button>";
		    }
	    	}
	    
	    }).on("loaded.rs.jquery.bootgrid", function()
	    		{
	    	    grid.find(".command-run1").on("click", function(e)
	    	    {
	    	    	var taskid=$(this).data("row-id");
	    	    	var total=$(this).data("row-total");
	    	    	var itemlist=$(this).data("row-itemlist");
	    	    	$("#total").val(total);
	    	    	$("#itemlist").val(itemlist);
	    	    	$("#dept").show();
	    	    	$("#btn").click(function(){
	    		    	$.post("task/updateapplycomplete/"+taskid,$("form").serialize(),function(a){
	    		    		$.MsgBox.Alert("消息","处理成功");
	    		    		LoadAjaxContent("updateapply");
	    		    	});
	    	    	
	    	    });
	    	    });
	    
	    
	    });
	  });
	  
	   function getLocalTime(nS) {  
 return new Date(parseInt(nS)).toLocaleString().replace(/:\d{1,2}$/,' ');  
}
    </script>
