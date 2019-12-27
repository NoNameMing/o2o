$(function () {
    // 登录验证 controller 的 url
    var loginUrl = '/o2o/local/logincheck';
    // 从地址栏的 url 里获取 usertype
    // usertype = 1 则为 customer，其余为 shopowner
    var usertype = getQueryString('usertype');
    // 登录次数，累计登录三次失败后弹出验证码要求输入
    var loginCount = 0;

    $('#submit').click(function () {
        // 获取输入的帐号
        var userName = $('#username').val();
        // 或取输入的密码
        var password = $('#psw').val();
        // 获取验证码信息
        var verifyCodeActual = $('#j_captcha').val();
        // 是否需要验证码验证，默认为 false，不需要
        var needVerify = false;
        // 如果登录三次都失败，就需要验证码校验了
        if(loginCount >= 3) {
            if(!verifyCodeActual) {
                $.toast('请输入验证码');
                return;
            } else {
                needVerify = true;
            }
        }
        // 访问后台登录验证
        $.ajax({
            url: loginUrl,
            async: false,
            cache: false,
            type: "post",
            dataType: 'json',
            data: {
                username: userName,
                password: password,
                verifyCodeActual: verifyCodeActual,
                // 是否需要验证码校验
                needVerify: needVerify
            },
            success : function (data) {
                if(data.success) {
                    $.toast("登录成功！");
                    if(usertype == 1) {
                        // 若用户在前端展示系统则自动链接到展示系统首页
                        window.location.href = '/o2o/frontend/index';
                    } else {
                        // 若用户是在店家管理系统则自动链接到店铺列表中
                        window.location.href = '/o2o/shopadmin/shoplist';
                    }
                } else {
                    $.toast("登录失败" + data.errMsg);
                    loginCount++;
                    if(loginCount >= 3) {
                        // 登录失败三次，需要做验证码检验
                        $('#verifyPart').show();
                    }
                }
            }
        })
    });

})