/**
 * Author: Bill Lv<ideaalloc@gmail.com>
 * Date: 2015-03-22
 */
var Auth2Header = function () {
};

Auth2Header.prototype.create = function () {
    var credentials = $.cookie('Authorization');
    var headers = {};
    headers.Authorization = "Bearer " + credentials;
    return headers;
};

Auth2Header.prototype.getUsername = function () {
    return $.ajax({
        type: "POST",
        url: "/auth/username",
        headers: this.create(),
        cache: false,
        async: false
    }).responseText;
};

Auth2Header.prototype.publishReview = function (regId) {
    $.ajax({
        type: "POST",
        url: "/api/inform-review/" + regId,
        headers: this.create(),
        cache: false,
        async: true
    });
};