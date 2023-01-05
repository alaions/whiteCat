layui.use(['jquery','carousel','flow','layer'], function () {
    var $ = layui.jquery;
    var flow = layui.flow;
    var layer = layui.layer;

    var width = width || window.innerWeight || document.documentElement.clientWidth || document.body.clientWidth;
    width = width>1200 ? 1170 : (width > 992 ? 962 : width);
    var carousel = layui.carousel;
    //建造实例
    carousel.render({
      elem: '#carousel'
      ,width: width+'px' //设置容器宽度
      ,height:'360px'
      ,indicator: 'inside'
      ,arrow: 'always' //始终显示箭头
      ,anim: 'default' //切换动画方式
      
    });

    $(".home-tips-container span").click(function(){
        layer.msg($(this).text(), {
            time: 20000, //20s后自动关闭
            btn: ['明白了']
        });
    });


    $(".recent-list .hotusers-list-item").mouseover(function () {
        var name = $(this).children(".remark-user-avator").attr("data-name");
        var str = "【"+name+"】的评论";
        layer.tips(str, this, {
            tips: [2,'#666666']
        });
    });


    $("#QQjl").mouseover(function(){
        layer.tips('QQ交流', this,{
            tips: 1
        });
    });
    $("#gwxx").mouseover(function(){
        layer.tips('消息', this,{
            tips: 1
        });
    });
    $("#xlwb").mouseover(function(){
        layer.tips('新浪微博', this,{
            tips: 1
        });
    });
    $("#htgl").mouseover(function(){
        layer.tips('设置', this,{
            tips: 1
        });
    });
    
    $(function () {

        $(".fa-home").parent().parent().addClass("layui-this");
        //播放公告
        playAnnouncement(5000);
    });
    
    function playAnnouncement(interval) {
        var index = 0;
        var $announcement = $('.home-tips-container>span');
        //自动轮换
        setInterval(function () {
            index++;    //下标更新
            if (index >= $announcement.length) {
                index = 0;
            }
            $announcement.eq(index).stop(true, true).fadeIn().siblings('span').fadeOut();  //下标对应的图片显示，同辈元素隐藏
        }, interval);
    }
});

function classifyList(id){
    	layer.msg('功能要自己写');
}



