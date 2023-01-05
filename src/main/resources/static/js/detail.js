var data = [{
	"commentId": 11,
	"article": null,
	"user": {
		"userId": 11,
		"nickname": "清风",
		"headPortrait": "http://qzapp.qlogo.cn/qzapp/101477629/B4901BFB60F8DEE83F01692F2544E612/100",
		"sex": "男",
		"registrationDate": "2018-08-01 15:19:26",
		"latelyLoginTime": "2018-08-01 15:19:26",
		"commentNum": 1
	},
	"content": "<p>网站开源吗</p><p><br></p>",
	"commentDate": "2018-08-01 15:19:41",
	"site": "河北省保定市  铁通",
	"reply":[{
			"replyId": 2,
			"comment": {
				"commentId": 11,
				"article": null,
				"user": {
					"userId": 11,
					"nickname": "清风",
					"headPortrait": "http://qzapp.qlogo.cn/qzapp/101477629/B4901BFB60F8DEE83F01692F2544E612/100",
					"sex": "男",
					"registrationDate": "2018-08-01 15:19:26",
					"latelyLoginTime": "2018-08-01 15:19:26",
					"commentNum": 1
				},
				"content": "<p>网站开源吗</p><p><br></p>",
				"commentDate": "2018-08-01 15:19:41",
				"site": "河北省保定市  铁通"
			},
			"user": {
				"userId": 1,
				"nickname": "Single",
				"headPortrait": "http://qzapp.qlogo.cn/qzapp/101477629/2F1EDDE252859E5FF645F959893C6863/100",
				"sex": "男",
				"registrationDate": "2018-07-26 21:24:49",
				"latelyLoginTime": "2018-08-09 10:25:36",
				"commentNum": 1
			},
			"content": "最近有点忙，后期会开源到GitHub上的。",
			"replyDate": "2018-08-01 22:10:28",
			"site": "湖南省湘潭市  移动"
		}]
},  {
	"commentId": 2,
	"article": null,
	"user": {
		"userId": 2,
		"nickname": "Mr.Long",
		"headPortrait": "http://qzapp.qlogo.cn/qzapp/101477629/B5D5212D0429E4491D932EEEF814FE99/100",
		"sex": "男",
		"registrationDate": "2018-07-26 21:30:24",
		"latelyLoginTime": "2018-08-01 22:16:42",
		"commentNum": 4
	},
	"content": "试试<br>",
	"commentDate": "2018-07-26 21:30:52",
	"site": "湖南省湘潭市  移动",
	"reply":[]
}];


layui.use('flow', function(){
    var flow = layui.flow;
    //评论显示
    flow.load({
        elem: '#commentList' //流加载容器
        ,done: function(page, next){ //执行下一页的回调
            setTimeout(function(){
                    var lis = [];
                    for(var i = 0; i < data.length; i++){
                        var str="";
                        var datas = {
                            comment:data[i].commentId
                        };
                        for(var r = 0; r < data[i].reply.length; r++){
                            str+="<div class=\"comment-child\">\n" +
                                "      <img src=\""+data[i].reply[r].user.headPortrait+"\" alt=\""+data[i].reply[r].user.nickname+"\" />\n" +
                                "      <div class=\"info\">\n" +
                                "          <span class=\"username\">	"+data[i].reply[r].user.nickname+" : </span>";
                            if(data[i].reply[r].user.userId=='1'){
                                str+="<span class=\"is_bloger\">博主</span>&nbsp;";
                            }
                            str+="回复 <span class=\"username\">@"+data[i].user.nickname+" </span>";
                            if(data[i].user.userId=='1'){
                                str+="<span class=\"is_bloger\">博主</span>&nbsp;";
                            }
                            str+= "：<span>"+data[i].reply[r].content+"</span>\n" +
                                "      </div>\n" +
                                "      <p class=\"info\"><span class=\"time\">"+data[i].reply[r].replyDate+"</span>&nbsp;&nbsp;&nbsp;&nbsp;<span>"+data[i].reply[r].site+"</span></p>\n" +
                                "  </div>\n";
                        }
                        lis.push( "<li>\n" +
                            "               <div class=\"comment-parent\">\n" +
                            "                   <img src=\""+data[i].user.headPortrait+"\" alt=\""+data[i].user.nickname+"\" />\n" +
                            "                   <div class=\"info\">\n" +
                            "                       <span class=\"username\">"+data[i].user.nickname+"</span>\n");
                        if(data[i].user.userId=='1'){
                            lis.push("<span class=\"is_bloger\">博主</span>&nbsp;");
                        }
                        lis.push("                   </div>\n" +
                            "                   <div class=\"content\">\n" +
                            "                       "+data[i].content+"\n" +
                            "                   </div>\n" +
                            "                   <p class=\"info info-footer\"><span class=\"time\">"+data[i].commentDate+"</span>&nbsp;&nbsp;&nbsp;&nbsp;<span class=\"time\">"+data[i].site+"</span>&nbsp;&nbsp;<a class=\"btn-reply\" style=\"color: #009688;font-size:14px;\" href=\"javascript:;\" onclick=\"btnReplyClick(this)\">回复</a></p>\n" +
                            "               </div>\n" +
                            "               <hr />\n" + str +
                            "               <!-- 回复表单默认隐藏 -->\n" +
                            "               <div class=\"replycontainer layui-hide\">\n" +
                            "                   <form class=\"layui-form\" action=\"/reply/list/\">\n" +
                            "                   <input type=\"hidden\" id=\"comment\" name=\"comment\" value=\""+data[i].commentId+"\" />\n" +
                            "                   <input type=\"hidden\" id=\"user\" lay-verify=\"userId\" name=\"user\" value=\""+$('#user').val()+"\" />\n" +
                            "                       <div class=\"layui-form-item\">\n" +
                            "                           <textarea name=\"content\" lay-verify=\"replyContent\" placeholder=\"回复  @"+data[i].user.nickname+":\" class=\"layui-textarea\" style=\"min-height:80px;\"></textarea>\n" +
                            "                       </div>\n" +
                            "                       <div class=\"layui-form-item\">\n" +
                            "                           <button class=\"layui-btn layui-btn-mini\" lay-submit=\"formReply\" lay-filter=\"formReply\">提交</button>\n" +
                            "                       </div>\n" +
                            "                   </form>\n" +
                            "               </div>\n" +
                            "           </li>");
                    }

                    //执行下一页渲染，第二参数为：满足“加载更多”的条件，即后面仍有分页
                    //pages为Ajax返回的总页数，只有当前页小于总页数的情况下，才会继续出现加载更多
                    next(lis.join(''), page < 1);
            }, 500);
        }
    });

});

$(document).ready(function() {
    $(".fa-file-text").parent().parent().addClass("layui-this");
});


function classifyList(id) {
	layer.msg('功能要自己写哦！');
}