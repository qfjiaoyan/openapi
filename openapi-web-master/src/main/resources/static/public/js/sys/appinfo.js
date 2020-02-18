$(function () {
    var option = {
        url: '../appinfo?act=table',
        toolbar: '#toolbar',
        queryParams: function (params) {
            var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                corpName: $("#app-name").val()
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
            {field: 'corpName', title: '公司名称'},
            {field: 'appName', title: '应用名称'},
            {field: 'appKey', title: 'appKey'},
            {field: 'appSecret', title: '秘钥'},
            {field: 'redirectUrl', title: '回调地址'},
            {field: 'limit', title: '日调用量限制'},
            {field: 'description', title: '描述'}
        ]
    };
    $('#table').bootstrapTable(option);
});

var vm = new Vue({
    el: '#dtapp',
    data: {
        showList: true,
        title: null,
        appinfo: {}
    },
    methods: {
        update: function (event) {
            var id = 'id';
            var gatewayId = getSelectedRow()[id];//common.js
            if (gatewayId == null) {
                return;
            }
            //sys/menu/info/1
            $.get("../appinfo?act=info&id=" + gatewayId, function (r) {
                vm.showList = false;
                vm.title = "修改";
                vm.appinfo = r;
            });
        },
        saveOrUpdate: function (event) {
            var url ="../appinfo?act=update";
            $.ajax({
                type: "POST",
                url: url,
                data: JSON.stringify(vm.appinfo),
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