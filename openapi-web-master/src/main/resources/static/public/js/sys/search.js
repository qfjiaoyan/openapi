$(function () {
    var option = {
        url: '../search?act=table',
        pagination: true,	//显示分页条
        sidePagination: 'server',//服务器端分页
        toolbar: '#toolbar',
        queryParams: function (params) {
            var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                limit: params.limit,   //页面大小
                offset: params.offset,  //页码
                rows: params.limit,   //页面大小
                start: params.offset,  //页码
                apiName: $("#apiName").val(),
                startTime: $("#start").val() == '' ? null : Date.parse($("#start").val()),
                endTime: $("#end").val() == '' ? null : Date.parse($("#end").val()),
                requestContent: $("#requestContent").val(),
                appKeys: $("#appKeys").val()
            };
            return temp;
        },
        striped: true,     //设置为true会有隔行变色效果
        columns: [
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
            {field: 'receiveTime', title: '接收时间',formatter: function (value, row, index) {return formatDate(value)}},
            /*{field: 'totalRepTime', title: '请求次数'},*/
            {field: 'apiName', title: '接口名'},
            {field: 'remoteIp', title: '访问ip'},
            /*{field: 'createTime', title: '创建时间',formatter: function (value, row, index) {return formatDate(value)}},*/
            {field: 'platformRepTime', title: '响应时间(毫秒)'},
            {field: 'errorCode', title: '错误码'},
            {field: 'appkey', title: 'appkey'},
            {field: 'venderId', title: 'venderId'},
            {field: 'servIP', title: '服务IP'},
            {field: 'requestContent', title: '内容'}
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
        appKeys: {}
    },
    methods: {
        reload: function (event) {
            $("#table").bootstrapTable('refresh');
        }
    },
    created: function () {//
        console.log(11)
        $.getJSON("../search?act=app", function (r) {
            vm.appKeys = r;
        });
    }
});