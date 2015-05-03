(function () {
    'use strict';

    /* define the controller for editing */
    var app = angular.module('app');
    app.controller('appEditingCtrl', function ($scope, dataSvc) {
        // initialize the collectionview
        var cv = new wijmo.collections.CollectionView(dataSvc.getData(55));

        cv.pageSize = 10;
        $scope.cvPaging = cv;

        // define the new item value.
        cv.newItemCreator = function () {
            var item = dataSvc.getData(1)[0];

            // aggregate the max value of id in the collection.
            item.id = wijmo.getAggregate(wijmo.Aggregate.Max, cv.sourceCollection, 'id') + 1;
            return item;
        }

        // initialize the scope data.
        $scope.cvEditing = cv;
        $scope.fieldNames = dataSvc.getNames();
        $scope.currentItem = cv.currentItem;
        $scope.confirmUpdate = function () {
            // commit editing/adding
            cv.commitEdit();
            cv.commitNew();
        };
        $scope.cancelUpdate = function () {
            // cancel editing or adding
            cv.cancelEdit();
            cv.cancelNew();
        };

        // syn the scope currentItem with the collectionview.
        cv.currentChanged.addHandler(function () {
            $scope.currentItem = cv.currentItem;
        });
    });
})();