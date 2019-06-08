<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!--Start Breadcrumb-->
<script src="js/echart/echarts.min.js"></script>
<div class="row">
    <div id="breadcrumb" class="col-xs-12">
        <a href="#" class="show-sidebar">
            <i class="fa fa-bars"></i>
        </a>
        <ol class="breadcrumb pull-left">
            <li><a href="index">首页</a></li>
            <li><a href="#">人事管理</a></li>
            <li><a href="#">统计图表</a></li>
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
                    <form class="form-horizontal" role="form" id="leaveForm">
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
                            <label class="col-xs-2 control-label">姓名：</label>
                            <div class="col-xs-4">
                                <input id="name" class="form-control" name="name" placeholder="姓名">
                            </div>
                            <div class="col-sm-2">
                                <div class="checkbox">
                                    <label>
                                        <input type="checkbox" id="queryType"> 按姓名查询
                                        <i class="fa fa-square-o small"></i>
                                    </label>
                                </div>
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
                        <i class="fa fa-coffee"></i> <span>统计图表</span>
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
                    <h4>人事统计</h4>
                    <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
                    <div id="chart" style="width: 800px;height:400px;"></div>
                </div>
            </div>
        </div>
    </div>

</div>

<script type="text/javascript">
    $(document).ready(function () {
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
            if($('#queryType')[0].checked){
                queryByName();
            }
            else{
                queryAll();
            }
        });

    });

    function queryAll() {
        $.post("leave/leave_chart",$('#leaveForm').serialize(),function(data){
            let source = [['product']];
            let series = [];
            for (let i = 0; i < data.length; i++) {
                series.push({type: 'bar'});
                source[0].push(data[i].leaveType);
                for (let j = 0; j < data[i].dataDetails.length; j++) {
                    let detail = data[i].dataDetails[j];
                    if(i == 0){
                        source.push([detail.userName]);
                    }
                    source[j+1].push(detail.count);
                }
            }
            console.log(source);
            /*let xData = [];
            let yData = [];
            for (let i = 0; i < data.length; i++) {
                xData.push(data[i].userName);
                yData.push(data[i].count);
            }*/
            echarts.dispose(document.getElementById('chart'));
            let myChart = echarts.init(document.getElementById('chart'));
            let option = {
                legend: {},
                tooltip: {},
                dataset: {
                    source: source
                },
                xAxis: {type: 'category'},
                yAxis: {},
                series: series
            };
            myChart.setOption(option);
            console.log(data);
        });
    }

    function queryByName() {
        $.post("leave/leave_chart_name",$('#leaveForm').serialize(),function(data){
            if(data == 'noName'){
                $.MsgBox.Alert("消息","用户不存在");
                return;
            }
            let xData = ["事假","病假","年假","丧假","产假"];
            let yData = data;
            echarts.dispose(document.getElementById('chart'));
            let myChart = echarts.init(document.getElementById('chart'));
            let option = {
                xAxis: {
                    type: 'category',
                    data: xData
                },
                yAxis: {
                    type: 'value'
                },
                series: [{
                    data: yData,
                    type: 'bar'
                }]
            };
            myChart.setOption(option);
            console.log(data);
        });
    }
</script>
