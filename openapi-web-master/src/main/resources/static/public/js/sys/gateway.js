$(function () {
    var option = {
        url: '../system/gateway?act=table',
        pagination: true,	//显示分页条
        sidePagination: 'server',//服务器端分页
        toolbar: '#toolbar',
        queryParams: function (params) {
            var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                limit: params.limit,   //页面大小
                offset: params.offset,  //页码
                gatewayApiName: $("#gateway-name").val(),
                state: $("#gateway-status").val()
            };
            return temp;
        },
        striped: true,     //设置为true会有隔行变色效果
        columns: [
            {checkbox: true},
            {
                field: 'id',//field： json的key对应
                title: '序号',
                width: 40,
                formatter: function (value, row, index) {
                    var pageSize = $('#table').bootstrapTable('getOptions').pageSize;
                    var pageNumber = $('#table').bootstrapTable('getOptions').pageNumber;
                    return pageSize * (pageNumber - 1) + index + 1;
                }
            },
            {title: 'ID', field: 'id'},
            {field: 'gatewayApiName', title: 'API名称'},
            {field: 'insideApiUrl', title: '内部URL地址'},
            {field: 'serviceId', title: 'serviceId'},
            {field: 'description', title: '备注'},
            {
                title: '状态', field: 'state', formatter: function (value, row, index) {
                return value === 1 ? '<span class="label label-success">有效</span>' : '<span class="label label-danger">无效</span>'
            }
            }
        ]
    };
    $('#table').bootstrapTable(option);
});
var ztree;

var vm = new Vue({
    el: '#dtapp',
    data: {
        showList: true,
        title: null,
        gateway: {}
    },
    methods: {
        del: function () {

            //var rows = getSelectedRows();
            /**
             * getSelections
             参数： undefined
             详情：
             返回选定的行，如果未选择任何记录，则返回一个空数组。
             */
            var rows = $("#table").bootstrapTable("getSelections")
            //[]
            if (rows == null || rows.length == 0) {
                alert('请选择您要删除的行');
                return;
            }
            var id = 'id';
            //提示确认框  layer huozhe sweetalert
            layer.confirm('您确定要删除所选数据吗？', {
                btn: ['确定', '取消'] //可以无限个按钮
            }, function (index, layero) {
                var ids = new Array();
                //遍历所有选择的行数据，取每条数据对应的ID
                $.each(rows, function (i, row) {
                    console.log(row[id]);
                    ids.push(row[id]);
                });

                $.ajax({
                    type: "POST",
                    url: "../system/gateway?act=del",
                    data: JSON.stringify(ids),
                    success: function (r) {
                        if (r.status) {
                            layer.alert('删除成功');
                            $('#table').bootstrapTable('refresh');
                        } else {
                            layer.alert(r.message);
                        }
                    },
                    error: function () {
                        layer.alert('服务器没有返回数据，可能服务器忙，请重试');
                    }
                });
            });
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.gateway = {};
        },
        update: function (event) {
            var id = 'id';
            var gatewayId = getSelectedRow()[id];//common.js
            if (gatewayId == null) {
                return;
            }
            //sys/menu/info/1
            $.get("../system/gateway?act=info&id=" + gatewayId, function (r) {
                vm.showList = false;
                vm.title = "修改";
                vm.gateway = r;
            });
        },
        saveOrUpdate: function (event) {
            var url = vm.gateway.id == null ? "../system/gateway?act=add" : "../system/gateway?act=update";
            $.ajax({
                type: "POST",
                url: url,
                data: JSON.stringify(vm.gateway),
                success: function (r) {
                    if (r.status) {
                        layer.alert('操作成功', function (index) {
                            layer.close(index);
                            vm.reload();
                        });
                    } else {
                        layer.alert(r.message);
                    }
                }
            });
        },
        reload: function (event) {
            vm.showList = true;
            $("#table").bootstrapTable('refresh');
        }
    }
});