/**
 * Author: Bill Lv<ideaalloc@gmail.com>
 * Date: 2015-03-18
 */
(function () {
    'use strict';

    /* define the controller for paging */
    var app = angular.module('app');
    app.controller('userCtrl', function ($scope, DTOptionsBuilder, DTColumnBuilder) {
        $scope.dtOptions = DTOptionsBuilder.newOptions()
            .withOption('ajax', {
                // Either you specify the AjaxDataProp here
                // dataSrc: 'data',
                url: '/angular-datatables/data/serverSideProcessing',
                type: 'POST'
            })
            // or here
            .withDataProp('data')
            .withOption('serverSide', true)
            .withPaginationType('full_numbers');
        $scope.dtColumns = [
            DTColumnBuilder.newColumn('id').withTitle('ID'),
            DTColumnBuilder.newColumn('firstName').withTitle('First name'),
            DTColumnBuilder.newColumn('lastName').withTitle('Last name').notVisible()
        ];
    });
})();