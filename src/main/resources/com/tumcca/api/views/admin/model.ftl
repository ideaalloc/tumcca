<div ng-controller="appEditingCtrl">
    <div class="row-fluid well row">
        <div class="btn-group col-md-7" ng-show="cvPaging.pageSize > 0">
            <button type="button" class="btn btn-default"
                    ng-disabled="cvPaging.pageIndex <= 0"
                    ng-click="cvPaging.moveToFirstPage()">
                <span class="glyphicon glyphicon-fast-backward"></span>
            </button>
            <button type="button" class="btn btn-default"
                    ng-disabled="cvPaging.pageIndex <= 0"
                    ng-click="cvPaging.moveToPreviousPage()">
                <span class="glyphicon glyphicon-step-backward"></span>
            </button>
            <button
                    type="button"
                    class="btn btn-default"
                    disabled style="width:100px" >
                {[cvPaging.pageIndex + 1 | number]}
                / {[cvPaging.pageCount | number]}
            </button>
            <button type="button" class="btn btn-default"
                    ng-disabled="cvPaging.pageIndex >= cvPaging.pageCount - 1"
                    ng-click="cvPaging.moveToNextPage()">
                <span class="glyphicon glyphicon-step-forward"></span>
            </button>
            <button type="button" class="btn btn-default"
                    ng-disabled="cvPaging.pageIndex >= cvPaging.pageCount - 1"
                    ng-click="cvPaging.moveToLastPage()">
                <span class="glyphicon glyphicon-fast-forward"></span>
            </button>
        </div>
    </div>
    <div class="sGrid">
        <table class="table table-condensed table-bordered">
            <thead>
            <tr class="active">
                <th class="text-center" ng-repeat="fieldName in fieldNames">
                    {[fieldName]}
                </th>
            </tr>
            </thead>
            <tbody>
            <tr ng-repeat="item in cvEditing.items"
                ng-click="cvEditing.moveCurrentTo(item)"
                ng-class="{success: item == cvEditing.currentItem}">
                <td class="text-center" ng-repeat="name in fieldNames">
                    {[item[name] | globalize]}
                </td>
            </tr>
            </tbody>
        </table>
    </div>

    <!-- commands -->
    <div class="row-fluid well">
        <!-- edit details in a popup -->
        <button
                class="btn btn-default"
                data-toggle="modal"
                data-target="#dlgDetail"
                ng-click="cvEditing.editItem(currentItem)"
                ng-disabled="!currentItem">
            Edit Detail...
        </button>
        <button
                class="btn btn-default"
                data-toggle="modal"
                data-target="#dlgDetail"
                ng-click="cvEditing.addNew()">
            Add...
        </button>
        <button
                class="btn btn-default"
                ng-click="cvEditing.remove(currentItem)"
                ng-disabled="!currentItem">
            Delete
        </button>
    </div>

    <!-- a dialog for editing item details -->
    <div class="modal fade" id="dlgDetail">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button
                            type="button"
                            class="close"
                            data-dismiss="modal"
                            aria-hidden="true">
                        &times;</button>
                    <h4 class="modal-title">Edit Item</h4>
                </div>
                <div class="modal-body">
                    <dl class="dl-horizontal">
                        <dt>ID</dt>
                        <dd>
                            <input
                                    class="form-control"
                                    type="text"
                                    ng-model="currentItem.id"/>
                        </dd>
                        <dt>Start Date</dt>
                        <dd>
                            <input
                                    formatted-model
                                    class="form-control"
                                    type="text"
                                    ng-model="currentItem.start"/>
                        </dd>
                        <dt>End Start</dt>
                        <dd>
                            <input
                                    formatted-model
                                    class="form-control"
                                    type="text"
                                    ng-model="currentItem.end"/>
                        </dd>
                        <dt>Country</dt>
                        <dd>
                            <input
                                    class="form-control"
                                    type="text"
                                    ng-model="currentItem.country"/>
                        </dd>
                        <dt>Product</dt>
                        <dd>
                            <input
                                    class="form-control"
                                    type="text"
                                    ng-model="currentItem.product"/>
                        </dd>
                        <dt>Color</dt>
                        <dd>
                            <input
                                    class="form-control"
                                    type="text"
                                    ng-model="currentItem.color"/>
                        </dd>
                        <dt>Amount</dt>
                        <dd>
                            <input
                                    class="form-control"
                                    type="text"
                                    ng-model="currentItem.amount"/>
                        </dd>
                        <dt>Active</dt>
                        <dd>
                            <input
                                    class="form-control"
                                    type="checkbox"
                                    ng-model="currentItem.active"/>
                        </dd>
                    </dl>
                </div>
                <div class="modal-footer">
                    <button
                            type="button"
                            class="btn btn-primary"
                            data-dismiss="modal"
                            ng-click="confirmUpdate()">
                        OK
                    </button>
                    <button
                            type="button"
                            class="btn btn-warning"
                            data-dismiss="modal"
                            ng-click="cancelUpdate()">
                        Cancel
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>
