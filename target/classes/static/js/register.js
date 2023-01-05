layui.use(['layer', 'upload', 'element', 'form', 'layedit'], function () {
    var layer = layui.layer;
    var form = layui.form;
    var element = layui.element;
    var layedit = layui.layedit;
    var $ = layui.jquery
        , upload = layui.upload;

    $(function () {
        $("#butt").click(function () {
            layer.open({
                type: 1,
                title: "注册页面",
                area: ['600px', '700px'],
                offset: '20px',
                content: $("#gb"),
                cancel: function () {
                    // 你点击右上角 X 取消后要做什么
                    setTimeout('window.location.reload()', 1);
                },
                success: function () {
                    form.on('submit(formDemo)', function (data) {

                    });
                }
            })
            form.render();
        })

        //验证输入的密码是否相同；
        $("#pass2").blur(function () {
            var pass1 = $("#pass1").val();
            var pass2 = $("#pass2").val();
            if (pass1 != pass2) {
                layer.msg("两次输入的密码不一致", {
                    "icon": 2,
                    "time": 2000
                });
                $("#pass2").val("");
                return false;
            }
            return true;
        });
    });

    //创建一个编辑器
    var editIndex = layedit.build('LAY_demo_editor');
    //自定义验证规则
    form.verify({
        title: function (value) {
            if (value.length < 5) {
                return '标题至少得5个字符啊';
            }
        },
        pwd: [
            /^(\w){6,20}$/, '只能输入6-20个字母、数字、下划线'
        ],
        emails: [
            /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/, '请输入正确的邮箱格式：<br>如：xxx@qq.com<br>xxx@163.com等格式'
        ],
        phones: [
            /^1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\d{8}$/, '您的输入有误，请重新输入(中国11位手机号)'
        ],
        name: [
            /^[\u4e00-\u9fa5]{2,4}$/, '您的输入有误，请输入2-4位中文'
        ],
        cardId: [
            /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/, '请输入正确的身份证号'
        ],
        content: function (value) {
            layedit.sync(editIndex);
        }
    });

    //判断是登录还是注册
    var img = document.getElementById("imgs");
    if (img!=null){
        $("#demo1").attr("src","/register/showImage?path="+img.defaultValue);
    }else{
        $("#demo1").attr("src",'/images/mr.png');
    }

    //注册
    form.on('submit(formDemo)', function (result) {
        debugger
        var btn = $(this);
        //设置注册按钮不可点击，防止多次点击
        btn.text("提交中...").attr("disabled", "disabled").addClass("layui-disabled");
        $.ajax({
            url: "/register/addUser",
            data: JSON.stringify(result.field),
            type: "post",
            dataType: "json",
            contentType: "application/json;charsetset=UTF-8",//必须
            success: function (res) {
                btn.text("提交").attr("disabled", false).removeClass("layui-disabled");
                if (res.code != 200) {
                    layer.msg(res.message);
                } else {
                    //跳转到登录页面
                    if (session!=null){
                        location.href = "/";
                    }else{
                        location.href = "/login/toLogin";
                    }
                }
            }
        });
    })

    //普通图片上传
    var uploadInst = upload.render({
        elem: '#test1'
        , url: '/register/uploadFile' //改成您自己的上传接口
        , acceptMime: 'image/*'
        , field: 'mf'
        , method: "post"  //此处是为了演示之用，实际使用中请将此删除，默认用post方式提交
        , before: function (obj) {
            //预读本地文件示例，不支持ie8
            obj.preview(function (index, file, result) {
                $('#demo1').attr('src', result); //图片链接（base64）
            });
        }
        , done: function (res, index, upload) {
            $("#uploadImg").val(res.path);
            //如果上传失败
            if (res.code > 0) {
                return layer.msg('上传失败');
            }
            //上传成功
        }
        , error: function () {
            //演示失败状态，并实现重传
            var demoText = $('#demoText');
            demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
            demoText.find('.demo-reload').on('click', function () {
                uploadInst.upload();
            });
        }
    });

});

