/**
 * Author: Bill Lv<ideaalloc@gmail.com>
 * Date: 2015-03-24
 */
var reviewRequest = {
    url: '/ws/review',
    contentType: "application/json",
    transport: 'websocket',
    trackMessageLength: true,
    fallbackTransport: 'long-polling'
};

reviewRequest.onOpen = function (response) {
};

reviewRequest.onMessage = function (response) {
    var message = response.responseBody;
    try {
        var json = atmosphere.util.parseJSON(message);
    } catch (e) {
        console.log('This doesn\'t look like a valid JSON: ', message);
        return;
    }

    var responseMessage;

    var process = json.process;
    if (process == 'UNDER REVIEW') {
        responseMessage = '店员 ' + json.username + ' 已提交车辆审核申请, 车辆注册号为: ' + json.regId;
    } else {
        responseMessage = '审核状态更新为: ' + process;
    }

    $('.top-right').notify({
        message: { text: responseMessage },
        fadeOut: { enabled: false, delay: 3000 }
    }).show();
};

reviewRequest.onClose = function (response) {
}

reviewRequest.onError = function (response) {
    BootstrapDialog.alert({
        title: '警告',
        message: '消息服务错误, 请联系系统管理员',
        type: BootstrapDialog.TYPE_WARNING,
        buttonLabel: '确定'
    });
};