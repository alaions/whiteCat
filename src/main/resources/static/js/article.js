layui.use('form', function(){
    var form = layui.form;
    form.on('submit(searchForm)', function(data){
        var keywords=$("#keywords").val();
        if(keywords==null || keywords==""){
            layer.msg('请输入要搜索的关键字');
            return false;
        }
        search();
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });
});



$(function(){
    $(".fa-file-text").parent().parent().addClass("layui-this");
    var keywords=$("#keywords").val();
    $("#keywords").keydown(function (event) {
        if (event.keyCode == 13) {
            var keyword=$("#keywords").val();
            if(keyword==null || keyword==""){
                layer.msg('请输入要搜索的关键字');
                return false;
            }
            search();
        }
    });
});

function search() {
	layer.msg('功能要自己写哦！');
}

function classifyList(id) {
	layer.msg('功能要自己写哦！');
}
