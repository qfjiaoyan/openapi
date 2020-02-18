$(function(){
    var option = {
        url: '../system/menu?act=list',
        pagination: false,	//显示分页条
        toolbar: '#toolbar',
        uniqueId:"id",
        striped : true,     //设置为true会有隔行变色效果
        //idField: 'menuId',
        columns: [
            {checkbox:true},
            {
                field: 'id',
                title: '序号',
                width: 40,
                formatter: function(value, row, index) {
                    var pageSize = $('#table').bootstrapTable('getOptions').pageSize;
                    var pageNumber = $('#table').bootstrapTable('getOptions').pageNumber;
                    return pageSize * (pageNumber - 1) + index + 1;
                }

            },
            {field:'name', title:'菜单名称', formatter: function(value,row){
                    if(row.type === 0){
                        return value;
                    }
                    if(row.type === 1){
                        return '<span style="margin-left: 40px;">├─ ' + value + '</span>';
                    }
                    if(row.type === 2){
                        return '<span style="margin-left: 80px;">├─ ' + value + '</span>';
                    }
                }},
            { title: '菜单图标', field: 'icon', formatter: function(value){
                    return value == null ? '' : '<i class="'+value+' fa-lg"></i>';
                }},
            { title: '菜单URL', field: 'url'},
            { title: '授权标识', field: 'perms'},
            { title: '排序', field: 'sort'},
            { title: '类型', field: 'type', formatter: function(value){
                    if(value === 0){
                        return '<span class="label label-primary">目录</span>';
                    }
                    if(value === 1){
                        return '<span class="label label-success">菜单</span>';
                    }
                    if(value === 2){
                        return '<span class="label label-warning">按钮</span>';
                    }
                }}
        ]};
    $('#table').bootstrapTable(option);
});
var ztree;

var vm = new Vue({
	el:'#dtapp',
    data:{
        showList: true,
        title: null,
        menu:{parentId:null,name:null,parentName:null,url:null,type:null,id:null}
    },
    methods:{
        del: function(){

            //var rows = getSelectedRows();
            /**
             * getSelections
             参数： undefined
             详情：
             返回选定的行，如果未选择任何记录，则返回一个空数组。
             */
            var rows = $("#table").bootstrapTable("getSelections")
            //[]
            if(rows == null||rows.length==0){
                alert('请选择您要删除的行');
                return ;
            }
            var id = 'id';
            //提示确认框  layer huozhe sweetalert
            layer.confirm('您确定要删除所选数据吗？', {
                btn: ['确定', '取消'] //可以无限个按钮
            }, function(index, layero){
                var ids = new Array();
                //遍历所有选择的行数据，取每条数据对应的ID
                $.each(rows, function(i, row) {
                    console.log(row[id]);
                    ids[i] = row[id];
                });

                $.ajax({
                    type: "POST",
                    url: "../system/menu?act=delete",
                    data: JSON.stringify(ids),
                    success : function(r) {
                        if(r.status){
                            layer.alert('删除成功');
                            $('#table').bootstrapTable('refresh');
                            layer.close(index);
                        }else{
                            layer.alert(r.message);
                        }
                    },
                    error : function() {
                        layer.alert('服务器没有返回数据，可能服务器忙，请重试');
                    }
                });
            });
        },
        add: function(){
            vm.showList = false;
            vm.title = "新增";
            vm.menu = {parentId:null,type:1,parentName:null};
            vm.getMenu();
        },
        update: function (event) {
            var id = 'id';
            var menuId = getSelectedRow()[id];//common.js
            if(menuId == null){
                return ;
            }
            //sys/menu/info/1
            $.get("../system/menu?act=info&id="+menuId, function(r){
                vm.showList = false;
                vm.title = "修改";
                vm.menu = r;
                if(r.parentId!=null){
                    vm.menu.parentName = $("#table").bootstrapTable("getRowByUniqueId",r.parentId).name;
                }
                vm.getMenu();
            });
        },
        saveOrUpdate: function (event) {
            var url = vm.menu.id == null ? "../system/menu?act=add" : "../system/menu?act=update";
            $.ajax({
                type: "POST",
                url: url,
                data: JSON.stringify(vm.menu),
                success: function(r){
                    if(r.status){
                        layer.alert('操作成功', function(index){
                            vm.reload();
                            layer.close(index);
                        });
                    }else{
                        layer.alert(r.message);
                    }
                }
            });
        },
        reload: function (event) {
            vm.showList = true;
            $("#table").bootstrapTable('refresh');
        },
        menuTree: function(){
            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: "选择菜单",
                area: ['300px', '450px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#menuLayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    var node = ztree.getSelectedNodes();
                    //选择上级菜单
                    vm.menu.parentId = node[0].id;
                    vm.menu.parentName = node[0].name;
                    layer.close(index);
                }
            });
        },
        getMenu: function(menuId){

            var setting = {
                data: {
                    simpleData: {
                        enable: true,
                        idKey: "id",
                        pIdKey: "parentId",
                        rootPId: -1
                    },
                    key: {
                        url:"nourl"
                    }
                }
            };

            //加载菜单树
            $.get("../system/menu?act=tree",
                function(r){
                //设置ztree的数据
                    r.push({id:null,name:'根节点',parentId:null});
                ztree = $.fn.zTree.init($("#menuTree"), setting, r);

                //编辑（update）时，打开tree，自动高亮选择的条目
                var node = ztree.getNodeByParam("id", vm.menu.parentId);
                //选中tree菜单中的对应节点
                ztree.selectNode(node);
                //编辑（update）时，根据当前的选中节点，为编辑表单的“上级菜单”回填值
                vm.menu.parentName = node.name;
            });
        }
    }
});