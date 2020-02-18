$(function () {
    $('#startTime').datetimepicker({
        format: 'yyyy-mm-dd hh:ii:ss',
        autoclose: true
    });
    $('#expireTime').datetimepicker({
        format: 'yyyy-mm-dd hh:ii:ss',
        autoclose: true
    });
    var option = {
        url: '../token?act=table',
        pagination: true,	//显示分页条
        sidePagination: 'server',//服务器端分页
        toolbar: '#toolbar',
        queryParams: function (params) {
            var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                key: $("#token").val(),
                limit: params.limit,   //页面大小
                offset: params.offset  //页码
            };
            return temp;
        },
        striped: true,     //设置为true会有隔行变色效果
        columns: [
            {checkbox: true},
            {title: '序号', field: 'id'},
            {title: 'appId', field: 'appId'},
            {title: 'userId', field: 'userId'},
            {title: 'accessToken', field: 'accessToken'},
            {title: 'startTime', field: 'startTime',formatter: function (value, row, index) {return formatDate(value)}},
            {title: 'expireTime', field: 'expireTime',formatter: function (value, row, index) {return formatDate(value)}}
        ]
    };
    $('#table').bootstrapTable(option);
});
function formatDate(time){
    var date=new Date(time);
    return date.getFullYear()+"-"+date.getMonth()+"-"+date.getDate()+" "+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds()
}
var vm = new Vue({
    el: '#dtapp',
    data: {
        showList: true,
        title: null,
        token: {id: null, appId: null, userId: null, accessToken: null, startTime: null, expireTime: null}
    },
    methods: {
        update: function (event) {
            var id = 'id';
            var userId = getSelectedRow()[id];//common.js
            if (userId == null) {
                return;
            }
            $.get("../token?act=info&id=" + userId, function (r) {
                vm.showList = false;
                vm.title = "修改";
                vm.token = r;
                //vm.token.startTime=formatDate(r.startTime);
                //vm.token.expireTime=formatDate(r.expireTime);
            });
        },
        add: function (event) {
            //sys/menu/info/1
            vm.showList = false;
            vm.title = "添加";
        },
        saveOrUpdate: function (event) {
            var url = "../token?act=update";
            $.ajax({
                type: "POST",
                url: url,
                data: JSON.stringify({
                    id: vm.token.id,
                    appId:  vm.token.appId,
                    userId:  vm.token.userId,
                    accessToken:  vm.token.accessToken,
                    startTime: new Date($("#startTime").val()).getTime(),
                    expireTime: new Date($("#expireTime").val()).getTime()
                }),
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