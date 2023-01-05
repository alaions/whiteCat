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
}, {
	"commentId": 8,
	"article": null,
	"user": {
		"userId": 7,
		"nickname": "默〃",
		"headPortrait": "http://qzapp.qlogo.cn/qzapp/101477629/436C863FE45FEBC7D279B0C22E9A62FD/100",
		"sex": "男",
		"registrationDate": "2018-07-28 21:00:50",
		"latelyLoginTime": "2018-07-28 21:00:50",
		"commentNum": 2
	},
	"content": "<p>旧时光博客博主&nbsp;https://www.zqfirst.top 来过</p>",
	"commentDate": "2018-07-28 21:02:20",
	"site": "河南省郑州市  移动",
	"reply":[]
}, {
	"commentId": 7,
	"article": null,
	"user": {
		"userId": 7,
		"nickname": "默〃",
		"headPortrait": "http://qzapp.qlogo.cn/qzapp/101477629/436C863FE45FEBC7D279B0C22E9A62FD/100",
		"sex": "男",
		"registrationDate": "2018-07-28 21:00:50",
		"latelyLoginTime": "2018-07-28 21:00:50",
		"commentNum": 2
	},
	"content": "网站不错，继续加油。",
	"commentDate": "2018-07-28 21:01:27",
	"site": "河南省郑州市  移动",
	"reply":[]
}, {
	"commentId": 6,
	"article": null,
	"user": {
		"userId": 4,
		"nickname": "十七",
		"headPortrait": "http://qzapp.qlogo.cn/qzapp/101477629/142350DC6080D761759CA72042447829/100",
		"sex": "男",
		"registrationDate": "2018-07-28 11:05:56",
		"latelyLoginTime": "2018-07-28 11:05:56",
		"commentNum": 2
	},
	"content": "网站不错，学到东西了<img src=\"http://www.long225.cn/static/foreground/layui/images/face/2.gif\" alt=\"[哈哈]\">",
	"commentDate": "2018-07-28 11:10:45",
	"site": "湖南省长沙市  移动",
	"reply":[]
}, {
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

layui.use(['jquery', 'form', 'layedit', 'flow'], function() {
	var form = layui.form;
	var $ = layui.jquery;
	var layedit = layui.layedit;
	var flow = layui.flow;

	//评论和留言的编辑器
	var editIndex = layedit.build('remarkEditor', {
		height: 150,
		tool: ['face', '|', 'left', 'center', 'right', '|', 'link'],
	});
	//评论和留言的编辑器的验证
	layui.form.verify({
		content: function(value) {
			value = $.trim(layedit.getText(editIndex));
			if(value == "") return "至少得有一个字吧";
			layedit.sync(editIndex);
		},
		userId: function(value) {
			if(value == "" || value == null) return "至少你得先登录吧！";
		},
		replyContent: function(value) {
			if($.trim(value) == "") {
				return "至少得有一个字吧!";
			}
		}
	});
	//评论显示
	flow.load({
		elem: '#messageList'//流加载容器
		,done: function(page, next) { //执行下一页的回调
			setTimeout(function() {
					var lis = [];
					
					for(var i = 0; i < data.length; i++) {
						var str = "";
						var datas = {
							comment: data[i].commentId
						};
						for(var r = 0; r < data[i].reply.length; r++) {
							str += "<div class=\"comment-child\">\n" +
								"      <img src=\"" + data[i].reply[r].user.headPortrait + "\" alt=\"" + data[i].reply[r].user.nickname + "\" />\n" +
								"      <div class=\"info\">\n" +
								"          <span class=\"username\">	" + data[i].reply[r].user.nickname + " : </span>";
							if(data[i].reply[r].user.userId == '1') {
								str += "<span class=\"is_bloger\">博主</span>&nbsp;";
							}
							str += "回复 <span class=\"username\">@" + data[i].user.nickname + "：</span>";
							if(data[i].user.userId == '1') {
								str += "<span class=\"is_bloger\">博主</span>&nbsp;";
							}
							str += "<span>" + data[i].reply[r].content + "</span>\n" +
								"      </div>\n" +
								"      <p class=\"info\"><span class=\"time\">" + data[i].reply[r].replyDate + "</span>&nbsp;&nbsp;&nbsp;&nbsp;<span>" + data[i].reply[r].site + "</span></p>\n" +
								"  </div>\n";
						}
						lis.push("<li>\n" +
							"               <div class=\"comment-parent\">\n" +
							"                   <img src=\"" + data[i].user.headPortrait + "\" alt=\"" + data[i].user.nickname + "\" />\n" +
							"                   <div class=\"info\">\n" +
							"                       <span class=\"username\">" + data[i].user.nickname + "</span>\n");
						if(data[i].user.userId == '1') {
							lis.push("<span class=\"is_bloger\">博主</span>&nbsp;");
						}
						lis.push("                   </div>\n" +
							"                   <div class=\"content\">\n" +
							"                       " + data[i].content + "\n" +
							"                   </div>\n" +
							"                   <p class=\"info info-footer\"><span class=\"time\">" + data[i].commentDate + "</span>&nbsp;&nbsp;&nbsp;&nbsp;<span>" + data[i].site + "</span>&nbsp;&nbsp;<a class=\"btn-reply\" style=\"color: #009688;font-size:14px;\" href=\"javascript:;\" onclick=\"btnReplyClick(this)\">回复</a></p>\n" +
							"               </div>\n" +
							"               <hr />\n" + str +
							"               <!-- 回复表单默认隐藏 -->\n" +
							"               <div class=\"replycontainer layui-hide\">\n" +
							"                   <form class=\"layui-form\" action=\"/reply/list/\">\n" +
							"                   <input type=\"hidden\" id=\"comment\" name=\"comment\" value=\"" + data[i].commentId + "\" />\n" +
							"                   <input type=\"hidden\" id=\"user\" lay-verify=\"userId\" name=\"user\" value=\"" + $('#user').val() + "\" />\n" +
							"                       <div class=\"layui-form-item\">\n" +
							"                           <textarea name=\"content\" lay-verify=\"replyContent\" placeholder=\"回复  @" + data[i].user.nickname + ":\" class=\"layui-textarea\" style=\"min-height:80px;\"></textarea>\n" +
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

	//监听留言提交
	form.on('submit(formLeaveMessage)', function(data) {
		var index = layer.load(1);
		//模拟留言回复
		var url = '/comment/add';
		$.ajax({
			type: "POST",
			url: url,
			data: data.field,
			success: function(res) {
				if(res.success) {
					layer.close(index);
					var content = data.field.content;
					var html = '<li><div class="comment-parent"><img src="' + res.comment.user.headPortrait + '" alt="' + res.comment.user.nickname + '"/><div class="info"><span class="username">' + res.comment.user.nickname + '</span>';
					if(res.comment.user.userId == '1') {
						html += " <span class=\"is_bloger\">博主</span>&nbsp;";
					}
					html += '</div><div class="content">' + data.field.content + '</div><p class="info info-footer"><span class="time">' + res.comment.commentDate + '</span>&nbsp;&nbsp;&nbsp;&nbsp;<span>' + res.comment.site + '</span>&nbsp;&nbsp;<a class="btn-reply"href="javascript:;" style="color: #009688;font-size:14px;" onclick="btnReplyClick(this)">回复</a></p></div><hr /><!--回复表单默认隐藏--><div class="replycontainer layui-hide"><form class="layui-form"action="">            <input type="hidden" id="comment" name="comment" value="' + res.comment.commentId + '" />       <input type="hidden" id="user" lay-verify="userId" name="user" value="' + res.comment.user.userId + '" />                    <div class="layui-form-item"><textarea name="content"lay-verify="replyContent"placeholder="回复@' + res.comment.user.nickname + '"class="layui-textarea"style="min-height:80px;"></textarea></div><div class="layui-form-item"><button class="layui-btn layui-btn-mini"lay-submit="formReply"lay-filter="formReply">提交</button></div></form></div></li>';
					$('.blog-comment').prepend(html);
					$('#remarkEditor').val('');
					editIndex = layui.layedit.build('remarkEditor', {
						height: 150,
						tool: ['face', '|', 'left', 'center', 'right', '|', 'link'],
					});
					layer.msg("评论成功", {
						icon: 1
					});
				} else {
					layer.msg("评论失败！");
				}
			},
			error: function(data) {
				layer.msg("网络错误！");
			}
		});
		return false;
	});

	//监听留言回复提交
	form.on('submit(formReply)', function(data) {
		var index = layer.load(1);
		//模拟留言回复
		var url = '/reply/add';
		$.ajax({
			type: "POST",
			url: url,
			data: data.field,
			success: function(res) {
				if(res.success) {
					layer.close(index);
					var html = '<div class="comment-child"><img src="' + res.reply.user.headPortrait + '" alt="' + res.reply.user.nickname + '"/><div class="info"><span class="username">' + res.reply.user.nickname + ' : </span>';
					if(res.reply.user.userId == '1') {
						html += " <span class=\"is_bloger\">博主</span>&nbsp;";
					}
					html += "回复 <span class=\"username\">@" + res.reply.comment.user.nickname + " </span>";
					if(res.reply.comment.user.userId == '1') {
						html += " <span class=\"is_bloger\">博主</span>&nbsp;";
					}
					html += '：<span>' + data.field.content + '</span></div><p class="info"><span class="time">' + res.reply.replyDate + '</span>&nbsp;&nbsp;&nbsp;&nbsp;<span>' + res.reply.site + '</span></p></div>';
					$(data.form).find('textarea').val('');
					$(data.form).parent('.replycontainer').before(html).siblings('.comment-parent').children('p').children('a').click();
					layer.msg("回复成功", {
						icon: 1
					});
				} else {
					layer.msg("回复失败！");
				}
			},
			error: function(data) {
				layer.msg("网络错误！");
			}
		});
		return false;
	});
});

function btnReplyClick(elem) {
	var $ = layui.jquery;
	$(elem).parent('p').parent('.comment-parent').siblings('.replycontainer').toggleClass('layui-hide');
	if($(elem).text() == '回复') {
		$(elem).text('收起')
	} else {
		$(elem).text('回复')
	}
}

function systemTime() {
	//获取系统时间。
	var dateTime = new Date();
	var year = dateTime.getFullYear();
	var month = dateTime.getMonth() + 1;
	var day = dateTime.getDate();
	var hh = dateTime.getHours();
	var mm = dateTime.getMinutes();
	var ss = dateTime.getSeconds();
	//分秒时间是一位数字，在数字前补0。
	mm = extra(mm);
	ss = extra(ss);

	//将时间显示到ID为time的位置，时间格式形如：19:18:02
	document.getElementById("time").innerHTML = year + "-" + month + "-" + day + " " + hh + ":" + mm + ":" + ss;
	//每隔1000ms执行方法systemTime()。
	setTimeout("systemTime()", 1000);
}

//补位函数。
function extra(x) {
	//如果传入数字小于10，数字前补一位0。
	if(x < 10) {
		return "0" + x;
	} else {
		return x;
	}
}