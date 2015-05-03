(function () {
    'use strict';

    /* define the controller for tracking changes */
    var app = angular.module('app');
    app.controller('appTCCtrl', function ($scope, dataSvc) {
        // initialize the collectionview
        var cv = new wijmo.collections.CollectionView(dataSvc.getData(6));

        //track the changes
        cv.trackChanges = true;

        // initialize the scope data.
        $scope.cvTrackingChanges = cv;
    });
})();