(function () {
    'use strict';

    // Declare the app module
    // Include the Wijmo 5 directives in the app module
    var app = angular.module('app', ['wj']).config(function($interpolateProvider){
        $interpolateProvider.startSymbol('{[').endSymbol(']}');
    });
})();