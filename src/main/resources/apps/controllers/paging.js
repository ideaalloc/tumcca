(function () {
    'use strict';

    /* define the controller for paging */
    var app = angular.module('app');
    app.controller('appPagingCtrl', function ($scope, dataSvc) {
        // initialize the collectionview
        var cv = new wijmo.collections.CollectionView(dataSvc.getData(55));

        // initialize the page size with 10.
        cv.pageSize = 10;

        // initialize the scope data.
        $scope.cvPaging = cv;
        $scope.fieldNames = dataSvc.getNames();
    });
})();