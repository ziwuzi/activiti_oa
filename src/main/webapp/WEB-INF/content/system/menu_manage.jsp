<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<link rel="stylesheet" href="plugins/zTree/bootstrapStyle.css" type="text/css">
<script type="text/javascript" src="plugins/zTree/jquery.ztree.core.js"></script>
<script type="text/javascript" src="plugins/zTree/jquery.ztree.excheck.js"></script>
<!--Start Breadcrumb-->
<div class="row">
    <div id="breadcrumb" class="col-xs-12">
        <a href="#" class="show-sidebar">
            <i class="fa fa-bars"></i>
        </a>
        <ol class="breadcrumb pull-left">
            <li><a href="index">首页</a></li>
            <li><a href="#">系统管理</a></li>
            <li><a href="#">菜单权限修改</a></li>
        </ol>
    </div>
</div>
<div class="container-fluid">
    <div class="row">
        <div class="col-lg-12">
            <div class="box ui-draggable ui-droppable">
                <div class="box-header">
                    <div class="box-name">
                        <i class="fa fa-coffee"></i> <span>菜单权限修改</span>
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
                    <ul id="treeDemo" class="ztree"></ul>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    var setting = {
        check: {
            enable: true,
            chkboxType: {
                Y: "ps",
                N: "ps"
            }
        },
        data: {
            simpleData: {
                enable: true
            }
        }
    };

    $(document).ready(function(){
        $.ajax({
            type: 'GET',
            url: "system/menu_manage",
            data: {"roleId" : 0},
            success:function(data) {
                console.log(data);
                $.fn.zTree.init($("#treeDemo"), setting, data);
            }
        });
    });
</script>