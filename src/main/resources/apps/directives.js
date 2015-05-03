(function () {
    'use strict';

    var app = angular.module('app');

    // Bootstrap Tabs directive
    app.directive('appTab', function () {
        return {
            restrict: 'E',
            transclude: true,
            scope: {},
            controller: function ($scope, $element) {
                var panes = $scope.panes = [];

                $scope.select = function (pane) {
                    angular.forEach(panes, function (pane) {
                        pane.selected = false;
                    });
                    pane.selected = true;
                }

                this.addPane = function (pane) {
                    if (panes.length == 0) $scope.select(pane);
                    panes.push(pane);
                }
            },
            template:
              '<div class="tabbable">' +
                '<ul class="nav nav-tabs">' +
                  '<li ng-repeat="pane in panes" ng-class="{active:pane.selected}">' +
                    '<a href="" ng-click="select(pane)">{{pane.title}}</a>' +
                  '</li>' +
                '</ul>' +
                '<div class="tab-content" ng-transclude></div>' +
              '</div>',
            replace: true
        };
    });

    app.directive('appTabPane', function () {
        return {
            require: '^appTab',
            restrict: 'E',
            transclude: true,
            scope: { title: '@' },
            link: function (scope, element, attrs, tabsCtrl) {
                tabsCtrl.addPane(scope);
            },
            template:
              '<div class="tab-pane" ng-class="{active: selected}" ng-transclude>' +
              '</div>',
            replace: true
        };
    });

    // format the value in ng-model for input
    app.directive('formattedModel', function () {
        return {
            require: 'ngModel',
            link: function (scope, element, attrs, ngModelController) {
                ngModelController.$parsers.push(function (data) {
                    return data;
                });

                ngModelController.$formatters.push(function (data) {
                    return wijmo.Globalize.format(data);
                });
            }
        }
    });

    app.directive('numberInput', function () {
        return {
            require: 'ngModel',
            link: function (scope, element, attrs, ngModelController) {
                ngModelController.$parsers.push(function (data) {
                    var num = wijmo.Globalize.parseInt(data);
                    if (isNaN(num)) {
                        num = 0;
                    }
                    return num;
                });

                ngModelController.$formatters.push(function (data) {
                    return data;
                });
            }
        }
    });
})();