(function () {
    'use strict';

    /* define the controller for grouping */
    var app = angular.module('app');
    app.controller('appGroupingCtrl', function ($scope, dataSvc) {
        // initialize the collectionview
        var cv = new wijmo.collections.CollectionView(dataSvc.getData(20));

        // initialize the scope data.
        $scope.cvGrouping = cv;
        $scope.fieldNames = dataSvc.getNames();
        $scope.groupItems = cv.items;
        $scope.selectedGroupOpt = '';
        $scope.isGroupItem = function (item) {
            return item instanceof wijmo.collections.CollectionViewGroup;
        };
        $scope.avgAmount = function (item) {
            // it only works when the item is a group item.
            if (!$scope.isGroupItem(item)) {
                return;
            }

            // get the average value of group amount values.
            var avg = item.getAggregate(wijmo.Aggregate.Avg, 'amount');
            return wijmo.Globalize.format(avg);
        };

        // update the group list
        cv.collectionChanged.addHandler(function () {
            $scope.groupItems = cv.items;
            if (cv.groups && cv.groups.length > 0) {
                $scope.groupItems = [];
                for (var i = 0; i < cv.groups.length; i++) {
                    addGroup(cv.groups[i]);
                }
            }
        });
        function addGroup(g) {
            $scope.groupItems.push(g);
            if (g.isBottomLevel) {
                for (var i = 0; i < g.items.length; i++) {
                    $scope.groupItems.push(g.items[i]);
                }
            }
            else {
                for (var i = 0; i < g.groups.length; i++) {
                    addGroup(g.groups[i]);
                }
            }
        }

        //apply the group setting
        $scope.$watch('selectedGroupOpt', function () {
            var gd,
               fieldName = $scope.selectedGroupOpt;

            gd = cv.groupDescriptions;

            if (!fieldName) {
                // clear all the group settings.
                gd.splice(0, gd.length);
                return;
            }

            if (findGroup(fieldName) >= 0) {
                return;
            }

            if (fieldName == 'amount') {
                // when grouping by amount, use ranges instead of specific values
                gd.push(new wijmo.collections.PropertyGroupDescription(fieldName, function (item, propName) {
                    var value = item[propName]; // amount
                    if (value > 1000) return 'Large Amounts';
                    if (value > 100) return 'Medium Amounts';
                    if (value > 0) return 'Small Amounts';
                    return 'Negative Amounts';
                }));
            }
            else {
                // group by specific property values
                gd.push(new wijmo.collections.PropertyGroupDescription(fieldName));
            }
        });

        // check whether the group with the specified property name already exists.
        function findGroup(propName) {
            var gd = cv.groupDescriptions;
            for (var i = 0; i < gd.length; i++) {
                if (gd[i].propertyName === propName) {
                    return i;
                }
            }
            return -1;
        }
    });
})();