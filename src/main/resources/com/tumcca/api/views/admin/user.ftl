<div id="container">
    <div class="btn-group" role="group" aria-label="操作">
        <button id="addUser" type="button" class="btn btn-default">添加</button>
        <button id="viewUser" type="button" class="btn btn-default">查看</button>
    </div>
</div>
<br>
<div class="clearfix"></div>

<table id="user" class="display" cellspacing="0" width="100%">
    <thead>
    <tr>
        <th>店员ID</th>
        <th>汽车店ID</th>
        <th>姓名</th>
        <th>积分</th>
    </tr>
    </thead>
</table>

<script>
    var userDT;
    var addUserDialog;
    var selected = null;
    $(function () {
        userDT = $('#user').dataTable({
            "oLanguage": { // 汉化
                "sProcessing": "正在加载数据...",
                "sLengthMenu": "显示_MENU_条 ",
                "sZeroRecords": "没有您要搜索的内容",
                "sInfo": "从_START_ 到 _END_ 条记录——总记录数为 _TOTAL_ 条",
                "sInfoEmpty": "记录数为0",
                "sInfoFiltered": "(全部记录数 _MAX_  条)",
                "sInfoPostFix": "",
                "sSearch": "搜索",
                "sUrl": "",
                "oPaginate": {
                    "sFirst": "第一页",
                    "sPrevious": " 上一页 ",
                    "sNext": " 下一页 ",
                    "sLast": " 最后一页 "
                }
            },
            "processing": true,
            "serverSide": true,
            "ajax": {
                "url": "/admin/users",
                "type": "POST"
            },
            "columns": [
                {"data": "userId", "searchable": false},
                {"data": "shopId", "searchable": false},
                {"data": "username", "orderable": false},
                {"data": "credit", "searchable": false}
            ]
        });

        $('#user tbody').on('click', 'tr', function () {
            if (selected != null) {
                $(selected).removeClass('selected');
            }
            selected = this;
            $(this).toggleClass('selected');
        });

        $('#addUser').on('click', function () {
            addUserDialog = BootstrapDialog.show({
                title: '添加用户',
                message: $('<div></div>').load('/admin/user-add')
            });
        });
        $('#viewUser').on('click', function () {
            if (selected == null) {
                BootstrapDialog.alert({
                    title: '警告',
                    message: '请先选择一个用户',
                    type: BootstrapDialog.TYPE_WARNING,
                    buttonLabel: '确定'
                });
            } else {
                var userId = $(selected).find('td:first-child').text();

                BootstrapDialog.show({
                    title: '查看用户',
                    message: $('<div></div>').load('/admin/user-view/' + userId)
                });
            }

        });
    });
</script>