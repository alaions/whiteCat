layui.use(['form', 'layer'],
    function() {
        $ = layui.jquery;
        var form = layui.form,
        layer = layui.layer;

        //自定义验证规则
        form.verify({
        nikename: function(value) {
        if (value.length < 5) {
        return '昵称至少得5个字符啊';
    }
    },
        pass: [/(.+){6,12}$/, '密码必须6到12位'],
        repass: function(value) {
        if ($('#L_pass').val() != $('#L_repass').val()) {
        return '两次密码不一致';
    }
    }
    });

    });

var _hmt = _hmt || []; (function() {
    var hm = document.createElement("script");
    hm.src = "https://hm.baidu.com/hm.js?b393d153aeb26b46e9431fabaf0f6190";
    var s = document.getElementsByTagName("script")[0];
    s.parentNode.insertBefore(hm, s);
})();

function usernameCheck() {
    $.ajax({
        type : "get",
        url: "/chen/user/usernameCheck",
        data: {"username":$("#L_username").val()},
        success:function (data) {
            if(data.toString()==='ok'){
                $("#usernameInfo").css("color", "green");
            }else {
                $("#usernameInfo").css("color", "red");
            }
            $("#usernameInfo").html(data);
        }
    })
}

function passwordCheck() {
    $.ajax({
        type : "get",
        url: "/chen/user/passwordCheck",
        data: {"password":$("#L_pass").val()},
        success:function (data) {
            if(data.toString()==='ok'){
                $("#passwordInfo").css("color", "green");
            }else {
                $("#passwordInfo").css("color", "red");
            }
            $("#passwordInfo").html(data);
        }
    })
}

function rePasswordCheck() {
    $.ajax({
        type : "get",
        url: "/chen/user/rePasswordCheck",
        data: {
            "rePassword":$("#L_repass").val(),
            "password":$("#L_pass").val()
        },
        success:function (data) {
            if(data.toString()==='ok'){
                $("#rePasswordInfo").css("color", "green");
            }else {
                $("#rePasswordInfo").css("color", "red");
            }
            $("#rePasswordInfo").html(data);
        }
    })
}

    function emailCheck() {
    $.ajax({
        type : "get",
        url: "/chen/user/emailCheck",
        data: {"email":$("#L_email").val()},
        success:function (data) {
            if(data.toString()==='ok'){
                $("#emailInfo").css("color", "green");
            }else {
                $("#emailInfo").css("color", "red");
            }
            $("#emailInfo").html(data);
        }
    })
}
