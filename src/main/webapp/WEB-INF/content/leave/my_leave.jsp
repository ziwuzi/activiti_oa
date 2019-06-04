<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!--Start Breadcrumb-->
<div class="row">
    <div id="breadcrumb" class="col-xs-12">
        <a href="#" class="show-sidebar">
            <i class="fa fa-bars"></i>
        </a>
        <ol class="breadcrumb pull-left">
            <li><a href="index">首页</a></li>
            <li><a href="#">人事管理</a></li>
            <li><a href="#">我发起的请假流程</a></li>
        </ol>
    </div>
</div>
<div class="container-fluid">
    <div class="row">
        <div class="col-lg-12">
            <div class="box ui-draggable ui-droppable">
                <div class="box-header">
                    <div class="box-name">
                        <i class="fa fa-coffee"></i> <span>我发起的请假流程</span>
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
                    <button id="leave_history" type="button" class="btn btn-primary">申请历史</button>
                    <table id="grid-data" class="table table-condensed table-hover table-striped">
                        <thead>
                        <tr>
                            <th data-column-id="user_id" data-identifier="true" data-type="numeric">申请人</th>
                            <th data-column-id="leave_type">类型</th>
                            <th data-column-id="start_time">请假开始时间</th>
                            <th data-column-id="end_time">请假结束时间</th>
                            <th data-column-id="reason">请假原因</th>
                            <th data-column-id="taskname">任务名称</th>
                            <th data-column-id="state">状态</th>
                            <th data-column-id="commands">操作</th>
                        </tr>
                        </thead>
                    </table>
                </div>
            </div>
        </div>
    </div>

</div>


<script type="text/javascript">
    $(document).ready(function () {
        $("#grid-data").bootgrid({
            navigation: 2,
            columnSelection: false,
            ajax: true,
            url: "leave/my_leave",
            formatters: {
                "commands": function(column, row)
                {
                    return "<button class=\"btn btn-xs btn-default ajax-link command-run1\" data-row-id=\"" + row.taskid + "\">处理</button>";
                }
            }

        }).on("loaded.rs.jquery.bootgrid", function () {
        });
        $("#leave_history").click(function (){
            LoadAjaxContent("historyprocess");
        });
    });


</script>
