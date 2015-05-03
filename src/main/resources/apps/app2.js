(function () {
    'use strict';

    var app = angular.module('app', ['wj', 'ngRoute', 'datatables']);

    app.config(function ($interpolateProvider) {
        $interpolateProvider.startSymbol('{[').endSymbol(']}');
    });

    app.config(['$routeProvider', function ($routeProvider) {
        $routeProvider.
            when('/model', {templateUrl: '/admin/model'}).
            when('/user', {templateUrl: '/admin/user'}).
            when('/review', {templateUrl: '/admin/review'}).
            when('/', {templateUrl: '/admin/home'}).
            otherwise({redirectTo: '/'});
    }]);

})();
