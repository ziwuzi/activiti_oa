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
            <li><a href="#">申请明细</a></li>
        </ol>
    </div>
</div>
<div class="container-fluid">
    <div class="row">
        <div class="col-lg-12">
            <div class="box ui-draggable ui-droppable">
                <div class="box-header">
                    <div class="box-name">
                        <i class="fa fa-coffee"></i> <span>申请明细</span>
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
                            <th data-column-id="user_id" data-identifier="true" data-type="numeric">申请人</th>
                            <th data-column-id="leave_type">类型</th>
                            <th data-column-id="start_time">请假开始时间</th>
                            <th data-column-id="end_time">请假结束时间</th>
                            <th data-column-id="reason">请假原因</th>
                            <th data-column-id="taskname">任务名称</th>
                            <th data-formatter="state">状态</th>
                            <th data-formatter="commands">操作</th>
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
        $("#dept").hide();
        var grid = $("#grid-data").bootgrid({
            navigation: 2,
            columnSelection: false,
            ajax: true,
            url: "leave/all_leave",
            formatters: {
                "commands": function(column, row)
                {
                    let showBtn = "<button class=\"btn btn-xs btn-default ajax-link\" onclick=show(\"" + row.id + "\")>查看</button>";
                    return showBtn;
                },
                "state": function(column, row)
                {
                    switch (row.state) {
                        case 0 : return "<span style='color:green'>审核中</span>";
                        case 1 : return "<span style='color:green'>审核通过</span>";
                        case 2 : return "<span style='color:red'>驳回</span>";
                        case 3 : return "<span style='color:red'>撤销</span>";
                    }
                }
            }

        }).on("loaded.rs.jquery.bootgrid", function () {
        });
        $("#leave_history").click(function (){
            LoadAjaxContent("historyprocess");
        });
    });


</script>
