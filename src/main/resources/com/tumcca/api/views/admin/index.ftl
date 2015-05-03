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
    <link rel="stylesheet" href="/wijmo/styles/wijmo.min.css">
    <link rel="stylesheet" href="/libs/bootstrap/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="/libs/bootstrap/dist/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="/libs/bootstrap-submenu/dist/css/bootstrap-submenu.min.css">
    <link rel="stylesheet" href="/libs/datatables/media/css/jquery.dataTables.min.css">
    <link rel="stylesheet" href="/assets/css/dataTables.bootstrap.css">
    <link rel="stylesheet" href="/libs/bootstrap3-dialog/dist/css/bootstrap-dialog.min.css">
    <link rel="stylesheet" href="/assets/css/formValidation.min.css">
    <link rel="stylesheet" href="/libs/blueimp-gallery/css/blueimp-gallery.min.css">
    <link rel="stylesheet" href="/assets/css/bootstrap-image-gallery.min.css">
    <link rel="stylesheet" href="/libs/bootstrap-notify/css/bootstrap-notify.css">

    <script src="/libs/jquery/dist/jquery.min.js"></script>
    <script src="/libs/jquery-cookie/jquery.cookie.js"></script>
    <script src="/libs/datatables/media/js/jquery.dataTables.min.js"></script>
    <script src="/assets/js/dataTables.bootstrap.js"></script>
    <script src="/libs/bootstrap/dist/js/bootstrap.min.js"></script>
    <script src="/libs/bootstrap-submenu/dist/js/bootstrap-submenu.min.js"></script>
    <script src="/libs/bootstrap3-dialog/dist/js/bootstrap-dialog.min.js"></script>
    <script src="/assets/js/jquery.bootstrap.wizard.min.js"></script>
    <script src="/assets/js/formValidation.js"></script>
    <script src="/assets/js/bootstrap.js"></script>
    <script src="/assets/js/zh_CN.js"></script>
    <script src="/libs/angular/angular.min.js"></script>
    <script src="/libs/angular-route/angular-route.min.js"></script>
    <script src="/libs/angular-datatables/dist/angular-datatables.min.js"></script>
    <script src="/wijmo/controls/wijmo.min.js"></script>
    <script src="/wijmo/controls/wijmo.grid.min.js"></script>
    <script src="/wijmo/controls/wijmo.chart.min.js"></script>
    <script src="/wijmo/controls/wijmo.input.min.js"></script>
    <script src="/wijmo/controls/wijmo.gauge.min.js"></script>
    <script src="/wijmo/controls/cultures/wijmo.culture.zh.min.js "></script>
    <script src="/wijmo/interop/angular/wijmo.angular.min.js"></script>
    <script src="/libs/blueimp-gallery/js/jquery.blueimp-gallery.min.js"></script>
    <script src="/assets/js/bootstrap-image-gallery.min.js"></script>
    <script src="/libs/atmosphere/atmosphere.js"></script>
    <script src="/libs/bootstrap-notify/js/bootstrap-notify.js"></script>

    <script src="/apps/app2.js"></script>
    <script src="/apps/services.js"></script>
    <script src="/apps/directives.js"></script>
    <script src="/apps/filters.js"></script>

    <script src="/apps/controllers/editing.js"></script>
    <script src="/assets/js/auth.js"></script>
    <script src="/assets/js/wsclient.js"></script>
</head>
<body ng-app="app">

<div class='notifications top-right'></div>

<div class="header visible-xs visible-sm">
    <div class="container">
        <h1><a href="http://7caima.com" target="_blank">后台管理系统</a></h1>
    </div>
</div>
<div class="header hidden-xs hidden-sm">
    <div class="container">
        <h1><a href="http://7caima.com" target="_blank"><img src="/assets/images/logo.png" alt="后台管理系统">后台管理系统</a>
        </h1>
    </div>
</div>

<div class="container">
    <nav class="navbar navbar-default">
        <div class="navbar-header">
            <button class="navbar-toggle" type="button" data-toggle="collapse" data-target=".navbar-collapse"><span
                    class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span
                    class="icon-bar"></span> <span class="icon-bar"></span></button>
            <a class="navbar-brand" href="#/">Tumcca</a></div>
        <div class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li class="dropdown"><a tabindex="0" data-toggle="dropdown">汽车<span class="caret"></span></a>
                    <ul class="dropdown-menu" role="menu">
                        <li><a tabindex="0" href="#/review">审核</a></li>
                    </ul>
                </li>
                <li class="dropdown"><a tabindex="0" data-toggle="dropdown">用户<span class="caret"></span></a>
                    <ul class="dropdown-menu" role="menu">
                        <li><a tabindex="0" href="#/user">店员</a></li>
                    </ul>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown"><a tabindex="0" data-toggle="dropdown">账户<span class="caret"></span></a>
                    <ul class="dropdown-menu" role="menu">
                        <li><a id="logout" tabindex="0">注销</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </nav>

    <ng-view></ng-view>
</div>
<script>
    $(function () {
        $('.dropdown-submenu > a').submenupicker();

        $("#logout").click(function (e) {
            e.preventDefault();

            var headers = new Auth2Header().create();
            $.ajax({
                type: "POST",
                url: "/auth/sign-out",
                cache: false,
                headers: headers,
                success: function (response) {
                    $.removeCookie('Authorization');
                    window.location.replace("/admin");
                },
                error: function (request, status, error) {
                    BootstrapDialog.show({
                        title: '注销失败',
                        message: '用户已登出，无法再次注销'
                    });
                }
            });
        });

        var socket = atmosphere;
        socket.subscribe(reviewRequest);
    });
</script>
</body>
</html>