<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no">
    <meta name="robots" content="Disallow">
    <meta name="author" content="lvchao, ideaalloc@gmail.com">
    <title>后台管理系统</title>
    <link rel="shortcut icon" type="image/x-icon"
          href="/assets/images/favicon.ico">
    <link rel="stylesheet" href="/libs/bootstrap/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="/libs/bootstrap/dist/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="/assets/css/formValidation.min.css">
    <link rel="stylesheet" href="/libs/bootstrap3-dialog/dist/css/bootstrap-dialog.min.css">

    <script src="/libs/jquery/dist/jquery.min.js"></script>
    <script src="/libs/jquery-cookie/jquery.cookie.js"></script>
    <script src="/libs/bootstrap/dist/js/bootstrap.min.js"></script>
    <script src="/libs/bootstrap3-dialog/dist/js/bootstrap-dialog.min.js"></script>
    <script src="/assets/js/formValidation.js"></script>
    <script src="/assets/js/bootstrap.js"></script>
    <script src="/assets/js/zh_CN.js"></script>

    <style>
        .header-banner {
            background: url(/assets/images/banner.jpg) no-repeat 0px 0px;
            background-size: cover;
            -webkit-background-size: cover;
            -moz-background-size: cover;
            -o-background-size: cover;
            min-height: 1200px;
            text-align: center;
        }

        .header-banner-info {
            padding: 30px;
            margin-top: 10em;
            text-align: center;
        }

        .header-banner-info h1 {
            color: #fff;
            font-size: 3em;
            font-weight: 100;
            margin: 0 auto;
            letter-spacing: 6px;
        }

        .header-banner-info p {
            font-size: 2em;
            color: #fff;
            font-weight: 100;
            margin-top: 18px;
        }

        .more {
            border: none;
            font-family: 'Questrial', sans-serif;
            color: #fff;
            background: #7ab800;
            cursor: pointer;
            padding: 10px 35px;
            display: inline-block;
            letter-spacing: 1px;
            outline: none;
            font-size: 17px;
            font-weight: 100;
            border-bottom: 3px solid #60890E;
            transition: 0.2s;
            -webkit-transition: 0.2s;
            -moz-transition: 0.2s;
            -o-transition: 0.2s;
            border-radius: 4px;
            -webkit-border-radius: 4px;
            -moz-border-radius: 4px;
            -o-border-radius: 4px;
            margin-top: 3em;
        }

        .more:hover {
            background-color: #60890E;
            color: #fff;
            text-decoration: none;
            border-bottom: 3px solid #7ab800;
        }
    </style>
</head>
<body>

<div class="header-banner">
    <div class="container">
        <div class="header-banner-info">
            <h1>Tumcca</h1>

            <p>Let's rock!</p>
        </div>
        <button class="more" data-toggle="modal" data-target="#loginModal">登录</button>
    </div>
</div>

<div class="modal fade" id="loginModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">登录</h4>
            </div>

            <div class="modal-body">
                <!-- The form is placed inside the body of modal -->
                <form id="loginForm" class="form-horizontal">
                    <div class="form-group">
                        <label class="col-xs-3 control-label">用户名</label>

                        <div class="col-xs-5">
                            <input type="text" class="form-control" name="username" id="username"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-xs-3 control-label">密码</label>

                        <div class="col-xs-5">
                            <input type="password" class="form-control" name="password" id="password"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-xs-5 col-xs-offset-3">
                            <button id="loginBtn" class="btn btn-default">登录</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<script>
    $(function () {
        $("#loginForm").submit(false);

        $("#loginBtn").on("click", function (e) {
            var loginClass = $("#loginForm").attr("class");

            if (loginClass == "modal fade") {
                return false;
            }

            if (!validate()) {
                return false;
            }

            var username = $("#username").val();
            var password = $("#password").val();
            var data = {};
            data.username = username;
            data.password = password;

            var url = '/auth/sign-in';

            $.ajax({
                url: url,
                type: "POST",
                data: JSON.stringify(data),
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                success: function (response) {
                    $("#loginModal").attr('class', 'modal fade');
                    var credentials = response.sessionId;
                    $.removeCookie('Authorization');
                    $.cookie('Authorization', credentials, {expires: 1, path: '/'});
                    document.location.href = '/admin';
                },
                error: function (request, status, error) {
                    $("#loginModal").attr('class', 'modal fade');
                    BootstrapDialog.alert({
                        title: '警告',
                        message: '用户名或密码错误',
                        type: BootstrapDialog.TYPE_WARNING,
                        buttonLabel: '确定'
                    });
                }
            });
        });

    });

    $('#loginForm').formValidation({
        framework: 'bootstrap',
        icon: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            username: {
                validators: {
                    notEmpty: {
                        message: '须输入用户名'
                    }
                }
            },
            password: {
                validators: {
                    notEmpty: {
                        message: '须输入密码'
                    }
                }
            }
        }
    });

    var validate = function () {
        var fv = $("#loginForm").data("formValidation");
        fv.validateContainer($("#loginForm"));
        var isValid = fv.isValidContainer($("#loginForm"));
        if (isValid === false || isValid === null) {
            return false;
        }
        return true;
    }
</script>
</body>
</html>