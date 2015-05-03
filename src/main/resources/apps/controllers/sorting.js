(function () {
    'use strict';

    /* define the controller for sorting */
    var app = angular.module('app');
    app.controller('appSortingCtrl', function ($scope, dataSvc) {
        // initialize the collectionview
        var cv = new wijmo.collections.CollectionView(dataSvc.getData(10));

        // initialize the scope data.
        $scope.cvSorting = cv;
        $scope.fieldNames = dataSvc.getNames();

        // sorting
        $scope.toggleSort = function (fieldName) {
            // get all the sort descriptions.
            var sd = cv.sortDescriptions;
            var ascending = true;

            // try to find whether the field has been sorted.
            if (sd.length > 0 && sd[0].property === fieldName) {
                // if finded, toggle the sort order.
                ascending = !sd[0].ascending;
            }

            // create a new SortDescription object.
            var sdNew = new wijmo.collections.SortDescription(fieldName, ascending);

            // remove any old sort descriptors and add the created one.
            sd.splice(0, sd.length, sdNew);
        };

        // get the sort label
        $scope.getSort = function (propName) {
            var sd = cv.sortDescriptions;
            if (sd.length > 0 && sd[0].property === propName) {
                return sd[0].ascending ? '▲' : '▼';
            }
            return '';
        };
    });
})();