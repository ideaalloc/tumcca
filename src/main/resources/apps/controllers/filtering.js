(function () {
    'use strict';

    /* define the controller for filtering */
    var app = angular.module('app');
    app.controller('appFilteringCtrl', function ($scope, dataSvc) {
        // initialize the collectionview
        var cv = new wijmo.collections.CollectionView(dataSvc.getData(10));

        // initialize the scope data.
        $scope.cvFiltering = cv;
        $scope.filter = '';
        $scope.fieldNames = dataSvc.getNames();

        // define the filter function for collectionview
        function filterFunction(item) {
            var filter = $scope.filter.toLowerCase();
            if (!filter) {
                return true;
            }
            return item['country'].toLowerCase().indexOf(filter) > -1;
        }

        // apply filter (applied on a 500 ms timeOut)
        var toFilter;
        $scope.$watch('filter', function () {
            if (toFilter) {
                clearTimeout(toFilter);
            }
            toFilter = setTimeout(function () {
                toFilter = null;
                if (cv.filter === filterFunction) {
                    cv.refresh();
                }
                else {
                    cv.filter = filterFunction;
                }
                $scope.$apply('cvFiltering.items');
            }, 500);
        });
    });
})();