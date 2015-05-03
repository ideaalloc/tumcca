<div id="container">
    <div class="btn-group" role="group" aria-label="操作">
        <button id="doReview" type="button" class="btn btn-default">审核</button>
    </div>
</div>
<br>
<div class="clearfix"></div>

<table id="review" class="display" cellspacing="0" width="100%">
    <thead>
    <tr>
        <th>登记ID</th>
        <th>进度</th>
        <th>积分</th>
        <th>创建时间</th>
        <th>评论</th>
    </tr>
    </thead>
</table>

<script>
    var reviewDT;
    var selected = null;
    $(function () {
        reviewDT = $('#review').dataTable({
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
                "url": "/admin/reviews",
                "type": "POST"
            },
            "columns": [
                {"data": "regId", "searchable": false},
                {"data": "process"},
                {"data": "integral", "searchable": false},
                {"data": "createTime", "searchable": false},
                {"data": "message", "orderable": false, "searchable": false}
            ]
        });

        $('#review tbody').on('click', 'tr', function () {
            if (selected != null) {
                $(selected).removeClass('selected');
            }
            selected = this;
            $(this).toggleClass('selected');
        });

        $('#doReview').on('click', function () {
            if (selected == null) {
                BootstrapDialog.alert({
                    title: '警告',
                    message: '请先选择一个登记记录',
                    type: BootstrapDialog.TYPE_WARNING,
                    buttonLabel: '确定'
                });
            } else {
                var regId = $(selected).find('td:first-child').text();

                BootstrapDialog.show({
                    title: '审核',
                    message: $('<div></div>').load('/admin/review-reports/' + regId)
                });
            }

        });
    });
</script>