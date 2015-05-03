<#-- @ftlvariable name="reviewReports" type="com.qicaima.api.model.admin.ReviewReports" -->
<style type="text/css">
    #reviewForm .tab-content {
        margin-top: 20px;
    }

    #links {
        padding: 60px;
    }
</style>

<form id="reviewForm" class="form-horizontal">
    <ul class="nav nav-pills">
        <li class="active"><a href="#basic-tab" data-toggle="tab">基本信息</a></li>
        <li><a href="#detection-tab" data-toggle="tab">检测信息</a></li>
        <li><a href="#photos-tab" data-toggle="tab">图片信息</a></li>
        <li><a href="#review-tab" data-toggle="tab">审核</a></li>
    </ul>

    <div class="tab-content">
        <!-- First tab -->
        <div class="tab-pane active" id="basic-tab">

            <div class="form-group">
                <label class="col-xs-3 control-label" for="carId">汽车编号</label>

                <div class="col-xs-5">
                    <p id="carId" class="form-control-static">${reviewReports.carId}</p>
                </div>
            </div>

            <div class="form-group">
                <label class="col-xs-3 control-label" for="ownerName">户主姓名</label>

                <div class="col-xs-5">
                    <p id="ownerName" class="form-control-static">${reviewReports.ownerName}</p>
                </div>
            </div>

            <div class="form-group">
                <label class="col-xs-3 control-label" for="ownerPhone">户主电话</label>

                <div class="col-xs-5">
                    <p id="ownerPhone" class="form-control-static">${reviewReports.ownerPhone}</p>
                </div>
            </div>

            <div class="form-group">
                <label class="col-xs-3 control-label" for="brand">汽车品牌</label>

                <div class="col-xs-5">
                    <p id="brand" class="form-control-static">${reviewReports.brand}</p>
                </div>
            </div>

            <div class="form-group">
                <label class="col-xs-3 control-label" for="model">汽车款型</label>

                <div class="col-xs-7">
                    <p id="model" class="form-control-static">${reviewReports.model} -- ${reviewReports.version}</p>
                </div>
            </div>

            <div class="form-group">
                <label class="col-xs-3 control-label" for="mileage">行驶里程</label>

                <div class="col-xs-5">
                    <p id="mileage" class="form-control-static">${reviewReports.mileage}</p>
                </div>
            </div>

            <div class="form-group">
                <label class="col-xs-3 control-label" for="transferTimes">转手次数</label>

                <div class="col-xs-5">
                    <p id="transferTimes" class="form-control-static">${reviewReports.transferTimes}</p>
                </div>
            </div>

            <div class="form-group">
                <label class="col-xs-3 control-label" for="regDate">登记日期</label>

                <div class="col-xs-5">
                    <p id="regDate" class="form-control-static">${reviewReports.regDate?date?iso_utc}</p>
                </div>
            </div>

        </div>

        <!-- Second tab -->
        <div class="tab-pane" id="detection-tab">
        <#if reviewReports.accEliDetect?has_content>
            <div class="form-group">
                <label class="col-xs-3 control-label" for="accEliDetect">事故排除检测</label>

                <div class="col-xs-7">
                    <p id="accEliDetect" class="form-control-static">${reviewReports.accEliDetect}</p>
                </div>
            </div>

            <div class="form-group">
                <label class="col-xs-3 control-label" for="appearanceDetect">外观检测</label>

                <div class="col-xs-7">
                    <p id="appearanceDetect" class="form-control-static">${reviewReports.appearanceDetect}</p>
                </div>
            </div>

            <div class="form-group">
                <label class="col-xs-3 control-label" for="interiorDetect">内饰检测</label>

                <div class="col-xs-7">
                    <p id="interiorDetect" class="form-control-static">${reviewReports.interiorDetect}</p>
                </div>
            </div>

            <div class="form-group">
                <label class="col-xs-3 control-label" for="elecEqpDetect">电气设备检测</label>

                <div class="col-xs-7">
                    <p id="elecEqpDetect" class="form-control-static">${reviewReports.elecEqpDetect}</p>
                </div>
            </div>

            <div class="form-group">
                <label class="col-xs-3 control-label" for="engineDetect">发动机检测</label>

                <div class="col-xs-7">
                    <p id="engineDetect" class="form-control-static">${reviewReports.engineDetect}</p>
                </div>
            </div>

            <div class="form-group">
                <label class="col-xs-3 control-label" for="roadTest">路试检测</label>

                <div class="col-xs-7">
                    <p id="roadTest" class="form-control-static">${reviewReports.roadTest}</p>
                </div>
            </div>

            <div class="form-group">
                <label class="col-xs-3 control-label" for="expPrice">客户预期价格</label>

                <div class="col-xs-5">
                    <p id="expPrice" class="form-control-static">${reviewReports.expPrice}</p>
                </div>
            </div>

            <div class="form-group">
                <label class="col-xs-3 control-label" for="minPrice">最低价</label>

                <div class="col-xs-5">
                    <p id="minPrice" class="form-control-static">${reviewReports.minPrice}</p>
                </div>
            </div>

            <div class="form-group">
                <label class="col-xs-3 control-label" for="maxPrice">最高价</label>

                <div class="col-xs-5">
                    <p id="maxPrice" class="form-control-static">${reviewReports.maxPrice}</p>
                </div>
            </div>
        <#else>
            <div class="alert alert-danger" role="alert">
                <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                <span class="sr-only">错误:</span>
                汽车检测未完成
            </div>
        </#if>
        </div>

        <!-- Third tab -->
        <div class="tab-pane" id="photos-tab">
            <!-- The Bootstrap Image Gallery lightbox, should be a child element of the document body -->
            <div id="blueimp-gallery" class="blueimp-gallery">
                <!-- The container for the modal slides -->
                <div class="slides"></div>
                <!-- Controls for the borderless lightbox -->
                <h3 class="title"></h3>
                <a class="prev">‹</a>
                <a class="next">›</a>
                <a class="close">×</a>
                <a class="play-pause"></a>
                <ol class="indicator"></ol>
                <!-- The modal dialog, which will be used to wrap the lightbox content -->
                <div class="modal fade">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" aria-hidden="true">&times;</button>
                                <h4 class="modal-title"></h4>
                            </div>
                            <div class="modal-body next"></div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default pull-left prev">
                                    <i class="glyphicon glyphicon-chevron-left"></i>
                                    前一张
                                </button>
                                <button type="button" class="btn btn-primary next">
                                    后一张
                                    <i class="glyphicon glyphicon-chevron-right"></i>
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div id="links">
            <#list reviewReports.photoIds as photoId>
                <a href="/api/get-photo/${photoId}" title="图片${photoId_index + 1}" data-gallery>
                    <img src="/api/get-photo-thumb/${photoId}" alt="${photoId_index + 1}">
                </a>
            </#list>
            </div>
        </div>

        <!-- Fourth tab -->
        <div class="tab-pane" id="review-tab">

            <div class="form-group">
                <label class="col-xs-3 control-label">审核意见</label>

                <div class="col-xs-5">
                    <label class="radio-inline">
                        <input type="radio" name="process" value="UNDER REVIEW">待审核
                    </label>
                    <label class="radio-inline">
                        <input type="radio" name="process" value="PASSED">通过
                    </label>
                    <label class="radio-inline">
                        <input type="radio" name="process" value="REJECTED">拒绝
                    </label>
                </div>
            </div>

            <div class="form-group">
                <label class="col-xs-3 control-label">审核评价</label>

                <div class="col-xs-7">
                    <textarea class="form-control" name="message" id="message"
                              rows="6">${reviewReports.message}</textarea>
                </div>
            </div>

            <div class="form-group">
                <label class="col-xs-3 control-label">积分</label>

                <div class="col-xs-5">
                    <input type="number" class="form-control" name="integral" id="integral"
                           data-fv-integer-message="输入的不是整数">
                </div>
            </div>

        </div>

        <!-- Previous/Next buttons -->
        <ul class="pager wizard">
            <li class="previous"><a href="javascript: void(0);">上一步</a></li>
            <li class="next"><a href="javascript: void(0);">下一步</a></li>
        </ul>
    </div>
</form>

<div class="modal fade" id="completeModal" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">完成</h4>
            </div>

            <div class="modal-body">
                <p class="text-center">谢谢, 您已完成审核</p>
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-success" data-dismiss="modal">完成</button>
            </div>
        </div>
    </div>
</div>

<script>
    var selectProcess = function () {
        var $radios = $('input:radio[name=process]');
        var process = "'${reviewReports.process}'";
        $radios.filter('[value=' + process + ']').prop('checked', true);
    };

    $(function () {
        $("#reviewForm").submit(false);

        selectProcess();

        var integral = "${reviewReports.integral}";
        integral = ('' + integral).replace(',', '');
        $('#integral').val(integral);

        // You don't need to care about this function
        // It is for the specific demo
        function adjustIframeHeight() {
            var $body = $('body'),
                    $iframe = $body.data('iframe.fv');
            if ($iframe) {
                // Adjust the height of iframe
                $iframe.height($body.height());
            }
        }

        $('#reviewForm')
                .formValidation({
                    framework: 'bootstrap',
                    icon: {
                        valid: 'glyphicon glyphicon-ok',
                        invalid: 'glyphicon glyphicon-remove',
                        validating: 'glyphicon glyphicon-refresh'
                    },
                    // This option will not ignore invisible fields which belong to inactive panels
                    exclude: ':disabled',
                    fields: {
                        message: {
                            validators: {
                                notEmpty: {
                                    message: '审核意见不能为空'
                                }
                            }
                        },
                        integral: {
                            validators: {
                                notEmpty: {
                                    message: '积分不能为空'
                                }
                            }
                        }
                    }
                })
                .bootstrapWizard({
                    tabClass: 'nav nav-pills',
                    onTabClick: function (tab, navigation, index) {
                        return validateTab(index);
                    },
                    onNext: function (tab, navigation, index) {
                        var numTabs = $('#reviewForm').find('.tab-pane').length,
                                isValidTab = validateTab(index - 1);
                        if (!isValidTab) {
                            return false;
                        }

                        if (index === numTabs) {
                            // We are at the last tab
                            if ($("#accEliDetect").length == 0) {
                                BootstrapDialog.alert({
                                    title: '警告',
                                    message: '汽车检测信息还未录入, 不能完成审核',
                                    type: BootstrapDialog.TYPE_WARNING,
                                    buttonLabel: '确定'
                                });
                                return false;
                            }

                            if ($("#links").children().length == 0) {
                                BootstrapDialog.alert({
                                    title: '警告',
                                    message: '汽车图片还未上传, 不能完成审核',
                                    type: BootstrapDialog.TYPE_WARNING,
                                    buttonLabel: '确定'
                                });
                                return false;
                            }

                            var regId = ${reviewReports.regId};
                            var message = $("#message").val();
                            var integral = $("#integral").val();
                            var process = $("input:radio[name=process]:checked").val();
                            var data = {};
                            data.regId = regId;
                            data.message = message;
                            data.integral = integral;
                            data.process = process;
                            var username = new Auth2Header().getUsername();
                            data.auditor = username;

                            $.ajax({
                                url: "/admin/add-review",
                                type: "POST",
                                data: JSON.stringify(data),
                                contentType: "application/json; charset=utf-8",
                                dataType: "json",
                                success: function (response) {
                                    $('#completeModal').modal();
                                    reviewDT.fnDraw();
                                    new Auth2Header().publishReview(regId);
                                },
                                error: function (request, status, error) {
                                    BootstrapDialog.alert({
                                        title: '警告',
                                        message: '系统错误, 请联系系统管理员',
                                        type: BootstrapDialog.TYPE_WARNING,
                                        buttonLabel: '确定'
                                    });
                                }
                            });

                            // Uncomment the following line to submit the form using the defaultSubmit() method
                            // $('#reviewForm').formValidation('defaultSubmit');

                            // For testing purpose
                            // $('#completeModal').modal();
                        }

                        return true;
                    },
                    onPrevious: function (tab, navigation, index) {
                        return validateTab(index + 1);
                    },
                    onTabShow: function (tab, navigation, index) {
                        // Update the label of Next button when we are at the last tab
                        var numTabs = $('#reviewForm').find('.tab-pane').length;
                        $('#reviewForm')
                                .find('.next')
                                .removeClass('disabled')    // Enable the Next button
                                .find('a')
                                .html(index === numTabs - 1 ? '提交' : '下一步');

                        // You don't need to care about it
                        // It is for the specific demo
                        adjustIframeHeight();
                    }
                });

        function validateTab(index) {
            var fv = $('#reviewForm').data('formValidation'), // FormValidation instance
            // The current tab
                    $tab = $('#reviewForm').find('.tab-pane').eq(index);

            // Validate the container
            fv.validateContainer($tab);

            var isValidStep = fv.isValidContainer($tab);
            if (isValidStep === false || isValidStep === null) {
                // Do not jump to the target tab
                return false;
            }

            return true;
        }
    });
</script>