<style type="text/css">
    /* Adjust feedback icon position */
    #userForm .form-control-feedback {
        right: 15px;
    }

    #userForm .selectContainer .form-control-feedback {
        right: 25px;
    }
</style>

<form id="userForm" method="post">
    <div class="form-group">
        <div class="row">
            <div class="col-xs-4 selectContainer">
                <label class="control-label">门店</label>
                <select class="form-control" name="shopId" id="shopSelect">
                    <option value="">选择一个门店</option>
                </select>
            </div>
            <div class="col-xs-8">
                <label class="control-label">用户名</label>
                <input type="text" class="form-control" name="username" id="username"
                       pattern="[a-zA-Z0-9]+" data-fv-regexp-message="用户名须仅有数字和字母组成">
            </div>
        </div>
    </div>

    <div class="form-group">
        <div class="row">
            <div class="col-xs-12">
                <label class="control-label">密码</label>
                <input class="form-control" name="password" id="password"
                       type="password">
            </div>
        </div>
    </div>

    <div class="form-group">
        <div class="row">
            <div class="col-xs-12">
                <label class="control-label">积分</label>
                <input class="form-control" name="credit" id="credit"
                       type="number"
                       data-fv-integer-message="输入的不是整数"/>
            </div>
        </div>
    </div>

    <button id="confirmBtn" class="btn btn-default">确认</button>
</form>

<script>
    var initShopSelect = function () {
        var headers = new Auth2Header().create();
        $.ajax({
            type: "GET",
            url: "/api/shops",
            headers: headers,
            success: function (response) {
                var kvs = response;
                var options = '';
                for (var pos = 0; pos < kvs.length; pos++) {
                    var kv = kvs[pos];
                    options += '<option value="' + kv.key + '">' + kv.value + '</option>';
                }
                $('#shopSelect').append(options);
            },
            error: function (request, status, error) {
                BootstrapDialog.show({
                    title: '获取店名失败',
                    message: eval("(" + request.responseText + ")").Message
                });
            }
        });
    }

    $('#userForm').formValidation({
        framework: 'bootstrap',
        icon: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            shopId: {
                row: '.col-xs-4',
                validators: {
                    notEmpty: {
                        message: '须选择门店'
                    }
                }
            },
            username: {
                row: '.col-xs-8',
                validators: {
                    notEmpty: {
                        message: '须输入姓名'
                    },
                    stringLength: {
                        min: 2,
                        max: 16,
                        message: '姓名长度须是2至16个字符'
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
        var fv = $("#userForm").data("formValidation");
        fv.validateContainer($("#userForm"));
        var isValid = fv.isValidContainer($("#userForm"));
        if (isValid === false || isValid === null) {
            return false;
        }
        return true;
    }

    $(function () {
        initShopSelect();
        $("#userForm").submit(false);

        $("#confirmBtn").on("click", function (e) {
            if (!validate()) {
                return false;
            }

            var shopId = $("#shopSelect").val();
            var username = $("#username").val();
            var credit = $("#credit").val();
            var password = $("#password").val();

            var data = {};
            data.shopId = shopId;
            data.username = username;
            data.credit = credit;
            data.password = password;

            var url = '/api/sign-up';

            $.ajax({
                url: url,
                type: "POST",
                data: JSON.stringify(data),
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                success: function (response) {
                    userDT.fnDraw();
                    addUserDialog.close();
                },
                error: function (request, status, error) {
                    BootstrapDialog.show({
                        title: '注册失败',
                        message: eval("(" + request.responseText + ")").Message
                    });
                }
            });
        });

    });
</script>