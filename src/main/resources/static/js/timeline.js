var path = $("#path").val();
var datas = [{
		"year": "2018",
		"month": {
			"08": [{
				"create_time": "08月08日 01:28",
				"content": "生日快乐<img src=\"http://www.long225.cn/static/fsLayui/plugins/layui/images/face/71.gif\" alt=\"[蛋糕]\">，还是和往常一样。"
			}, {
				"create_time": "08月07日 09:25",
				"content": "重装系统把博客2.0的源码搞没了，现在只有1.0的了，好无语&nbsp;<img src=\"http://www.long225.cn/static/fsLayui/plugins/layui/images/face/40.gif\" alt=\"[晕]\">。。。&nbsp;"
			}],
			"07": [{
				"create_time": "07月22日 04:03",
				"content": "<p>修复：</p><p>1.更换留言板加载动画以及修复加载留言数据时因同步而导致卡顿的问题！</p><p>2.优化网站操作流畅性！</p>"
			}, {
				"create_time": "07月13日 10:03",
				"content": "Long博客更新为v2.0,修改了部分样式，移除了音乐播放器插件。"
			}, {
				"create_time": "07月12日 09:27",
				"content": "<p><span>本站接入QQ登录，方便快捷~美滋滋<img src=\"http://www.long225.cn/static/fsLayui/plugins/layui/images/face/1.gif\" alt=\"[嘻嘻]\"></span></p>"
			}, {
				"create_time": "07月10日 02:03",
				"content": "<p><span>开始搭建项目，构思架构</span></p>"
			}]
		}
	}];

$(function() {
	var _html = '';
	for(var i = 0; i < datas.length; i++) {
		_html += '<div class="timeline-year">';
		_html += '<h2><a class="yearToggle" href="javascript:;">' + datas[i].year + '年</a><i class="fa fa-caret-down fa-fw"></i></h2>';
		var monthDatas = datas[i].month;
		for(var _month in monthDatas) {
			var _data = monthDatas[_month];
			_html += '<div class="timeline-month">';
			_html += '<h3 class=" animated fadeInLeft"><a class="monthToggle" href="javascript:;">' + _month + '月</a><i class="fa fa-caret-down fa-fw"></i></h3>';
			_html += '<ul>';
			for(var j = 0; j < _data.length; j++) {
				_html += '<li class=" ">';
				_html += '<div class="h4  animated fadeInLeft">';
				_html += '<p class="date">' + _data[j].create_time + '</p>';
				_html += '</div>';
				_html += '<p class="dot-circle animated "><i class="fa fa-dot-circle-o"></i></p>';
				_html += '<div class="content animated fadeInRight">' + _data[j].content + '</div>';
				_html += '<div class="clear"></div>';
				_html += '</li>';
			}
			_html += '</ul>';
			_html += '</div>';
		}
		_html += '</div>';
	}
	$(".timeline-line").after(_html);
	$('.monthToggle').click(function() {
		$(this).parent('h3').siblings('ul').slideToggle('slow');
		$(this).siblings('i').toggleClass('fa-caret-down fa-caret-right');
	});
	$('.yearToggle').click(function() {
		$(this).parent('h2').siblings('.timeline-month').slideToggle('slow');
		$(this).siblings('i').toggleClass('fa-caret-down fa-caret-right');
	});
})

$(document).ready(function() {
	$(".fa-hourglass-half").parent().parent().addClass("layui-this");
});