(function () {
    'use strict';

    /* define the controller for current record management */
    var app = angular.module('app');
    app.controller('appCRMCtrl', function ($scope, dataSvc) {
        // initialize the collectionview
        var cv = new wijmo.collections.CollectionView(dataSvc.getData(10));

        // initialize the scope data.
        $scope.cvCRM = cv;
        $scope.fieldNames = dataSvc.getNames();

        // forbid changing current when the current item is the 4th one.
        var stopCurrentIn4th = function (sender, e) {
            // when the current is the 4rd item, stop moving.
            if (sender.currentPosition === 3) {
                e.cancel = true;
            }
        }
        $scope.stopCurrent = function () {
            cv.currentChanging.addHandler(stopCurrentIn4th);
        };

        // restore to be able to change current.
        $scope.reset = function () {
            cv.currentChanging.removeHandler(stopCurrentIn4th);
        };
    });
})();