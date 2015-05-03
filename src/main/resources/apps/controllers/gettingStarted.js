(function () {
    'use strict';

    /* define the controller for getting started */
    var app = angular.module('app');
    app.controller('appGSCtrl', function ($scope, dataSvc) {
        // initialize the collectionview
        var cv = new wijmo.collections.CollectionView(dataSvc.getData(10));

        // initialize the scope data.
        $scope.cvGettingStarted = cv;
        $scope.fieldNames = dataSvc.getNames();
    });
})();