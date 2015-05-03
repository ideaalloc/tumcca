var __extends = this.__extends || function (d, b) {
    for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p];
    function __() { this.constructor = d; }
    __.prototype = b.prototype;
    d.prototype = new __();
};
var wijmo;
(function (wijmo) {
    (function (_data) {
        /**
        * Extends @see:CollectionView to support OData service.
        *
        * OData is a standardized protocol for creating and consuming data APIs.
        * OData builds on core protocols like HTTP and commonly accepted methodologies like REST.
        * The result is a uniform way to expose full-featured data APIs. (http://www.odata.org/)
        *
        * You can use the result objects from the OData service as data sources for any Wijmo controls,
        * and in addition to full CRUD support and real-time updates you automatically get
        * CollectionView features including sorting, filtering, paging, and grouping.
        */
        var ODataCollectionView = (function (_super) {
            __extends(ODataCollectionView, _super);
            /**
            * Initializes a new instance of an @see:ODataCollectionView.
            *
            * @param url Base url for the OData service.
            * @param keys Array of field names used as keys for this table.
            * @param options Object with query options (such as take, skip, sort, etc).
            * @param dataTypes Array with field names and data type overrides.
            * @param notify Function called when the data is loaded.
            * @param loadAllData Whether to load all data from the table.
            * @param sortOnServer Whether to sort on server.
            * @param pageOnServer Whether to page on server.
            */
            function ODataCollectionView(url, keys, options, dataTypes, notify, loadAllData, sortOnServer, pageOnServer) {
                var user = options && options.serverSettings ? options.serverSettings.user : null, password = options && options.serverSettings ? options.serverSettings.password : null;

                if (user && password) {
                    this._encodeCreds = window.btoa(user + ':' + password);
                }

                this._ajaxSettings = {
                    jsonp: '$callback',
                    dataType: 'json',
                    data: {
                        $format: 'verbosejson'
                    },
                    timeout: 60 * 1000
                };

                if (this._encodeCreds) {
                    this._ajaxSettings['headers'] = {
                        Authorization: 'Basic ' + this._encodeCreds
                    };
                }

                this._items = new wijmo.collections.ObservableArray();
                this._serviceUrl = url.serviceUrl + ((url.serviceUrl.lastIndexOf('/') === url.serviceUrl.length - 1) ? '' : '/');
                this._entityName = url.entityName;
                this._keys = keys;
                this._dataTypes = dataTypes;
                this._callback = notify;
                this._loadAllData = !!loadAllData;
                this._sortOnServer = !!sortOnServer;
                this._pageOnServer = !!pageOnServer;
                this._isRequesting = false;

                _super.call(this, this._items);

                if (options) {
                    this._ajaxSettings = $.extend({}, this._ajaxSettings, options.ajax);
                    this._ajaxSettings.data = $.extend({}, this._ajaxSettings.data);

                    if (options.serverSettings) {
                        if (options.serverSettings.filters) {
                            this._ajaxSettings.data.$filter = ODataCollectionView._parseFilter(options.serverSettings.filters);
                        }

                        if (options.serverSettings.selects) {
                            this._ajaxSettings.data.$select = options.serverSettings.selects.join(', ');
                        }
                    }
                }

                this.sortDescriptions.collectionChanged.removeAllHandlers();
                this.sortDescriptions.collectionChanged.addHandler(this._sortDescHandler.bind(this));
                this._queryData();
            }
            /**
            * Override _getPageView to get the list that corresponds to the current page
            */
            ODataCollectionView.prototype._getPageView = function () {
                if (!this._pageOnServer) {
                    return _super.prototype._getPageView.call(this);
                }
                return this._view;
            };

            Object.defineProperty(ODataCollectionView.prototype, "pageCount", {
                /**
                * Override pageCount to get the total number pages.
                */
                get: function () {
                    var totalCount = this.pageOnServer ? this.TotalItemCount : this._view.length;
                    return this.pageSize ? Math.ceil(totalCount / this.pageSize) : 1;
                },
                enumerable: true,
                configurable: true
            });

            Object.defineProperty(ODataCollectionView.prototype, "pageSize", {
                /**
                * Override pageSize to get or set the number of items to display on a page.
                */
                get: function () {
                    return this._pgSz;
                },
                set: function (value) {
                    if (value != this._pgSz) {
                        this._pgSz = wijmo.asInt(value);
                        if (this.pageOnServer) {
                            this._queryData();
                        } else {
                            this.refresh();
                        }
                    }
                },
                enumerable: true,
                configurable: true
            });

            /**
            * Override to move to the page at the specified index.
            *
            * @param index Index of the page to move to.
            * @return True if the page index was changed successfully.
            */
            ODataCollectionView.prototype.moveToPage = function (index) {
                if (!this.pageOnServer) {
                    return _super.prototype.moveToPage.call(this, index);
                }
                var newIndex = wijmo.clamp(index, 0, this.pageCount - 1);
                if (newIndex != this.pageIndex) {
                    // honor canChangePage
                    if (!this.canChangePage) {
                        wijmo.assert(false, 'Changing pages not supported.');
                    }

                    // raise pageChanging
                    var e = new wijmo.collections.PageChangingEventArgs(newIndex);
                    if (this.onPageChanging(e)) {
                        // change the page
                        this._pgIdx = newIndex;
                        this._idx = 0;
                        this._queryData();
                    }
                }
                return this._pgIdx == index;
            };

            /**
            * Override commitNew to add the new item to the database.
            */
            ODataCollectionView.prototype.commitNew = function () {
                this._requestWrite('Add', this.currentAddItem);
                _super.prototype.commitNew.call(this);
            };

            /**
            * Override commitEdit to modify the item in the database.
            */
            ODataCollectionView.prototype.commitEdit = function () {
                var sameContent = this._sameContent(this.currentEditItem, this._edtClone);
                if (!sameContent && !this.currentAddItem) {
                    this._requestWrite('Update', this.currentEditItem);
                }
                _super.prototype.commitEdit.call(this);
            };

            /**
            * Override remove to remove the item from the database.
            */
            ODataCollectionView.prototype.remove = function (item) {
                this._requestWrite('Delete', this.currentItem);
                _super.prototype.remove.call(this, item);
            };

            Object.defineProperty(ODataCollectionView.prototype, "sortOnServer", {
                /**
                * Gets or sets a value indicating whether to page on server or client.
                */
                get: function () {
                    return this._sortOnServer;
                },
                set: function (value) {
                    var bValue = wijmo.asBoolean(value);
                    if (this.sortOnServer == bValue) {
                        return;
                    }
                    this._sortOnServer = bValue;
                    if (this.sortDescriptions.length > 0 && this.canSort) {
                        this._queryData();
                    }
                },
                enumerable: true,
                configurable: true
            });

            Object.defineProperty(ODataCollectionView.prototype, "pageOnServer", {
                /**
                * Gets or sets a value indicating whether to sort on server or client.
                */
                get: function () {
                    return this._pageOnServer;
                },
                set: function (value) {
                    var bValue = wijmo.asBoolean(value);
                    if (this.pageOnServer == bValue) {
                        return;
                    }
                    this._pageOnServer = bValue;
                    if (this.pageSize) {
                        this._queryData();
                    }
                },
                enumerable: true,
                configurable: true
            });

            // send request for data
            ODataCollectionView.prototype._queryData = function () {
                if (this._isRequesting) {
                    window.setTimeout(this._queryData.bind(this), 100);
                    return;
                }
                this._updateAjaxSettings();
                this._requestData(this._serviceUrl + this._entityName, this._ajaxSettings);
            };

            // callback for query success
            ODataCollectionView.prototype._continueSuccess = function (res) {
                this._querySuccess(res, true);
            };
            ODataCollectionView.prototype._querySuccess = function (res, isContinued) {
                var resData = [], self = this, nextLink = res['odata.nextLink'];
                if (res.value) {
                    resData = res.value;
                } else if (res.d) {
                    resData = res.d;
                    if (res.d.results) {
                        resData = res.d.results;
                    }
                } else if ($.isArray(res)) {
                    resData = res;
                }

                if (!isContinued) {
                    this._items.clear();
                }

                $.each(resData, function (_, item) {
                    self._convertItem(item, self._dataTypes);
                    if (item.__metadata && item.__metadata.etag) {
                        item.etag = item.__metadata.etag;
                    }
                    delete item.__metadata;
                    self._items.push(item);
                });

                if (this._loadAllData && nextLink) {
                    this._requestData(this._serviceUrl + nextLink, {
                        data: {
                            $format: 'verbosejson'
                        }
                    }, true);
                } else {
                    if (res['odata.count']) {
                        this._totalItemCount = parseInt(res['odata.count']);
                    } else if (res.d && res.d.__count) {
                        this._totalItemCount = parseInt(res.d.__count);
                    }
                    this.moveCurrentToFirst();

                    this.onCallback();
                }
            };
            ODataCollectionView.prototype._success = function (res) {
                this._querySuccess(res, false);
            };

            // callback for query fail
            ODataCollectionView.prototype._fail = function (xhr, textStatus, errorThrown) {
                this._isRequesting = false;
                throw textStatus + errorThrown;
            };

            ODataCollectionView.prototype.onCallback = function () {
                this._isRequesting = false;
                if (this._callback) {
                    this._callback();
                }
            };

            // update the query ajax settings
            ODataCollectionView.prototype._updateAjaxSettings = function () {
                this._updateSortAjaxSettings();
                this._upatePageAjaxSettings();
            };

            // update ajax setting for page
            ODataCollectionView.prototype._upatePageAjaxSettings = function () {
                if (!this._ajaxSettings.data) {
                    this._ajaxSettings.data = {};
                }

                if (this.pageOnServer && this.pageSize) {
                    this._ajaxSettings.data.$top = this.pageSize;
                    this._ajaxSettings.data.$skip = this.pageIndex * this.pageSize;
                    this._ajaxSettings.data.$inlinecount = "allpages";
                } else {
                    this._ajaxSettings.data.$top = undefined;
                    this._ajaxSettings.data.$skip = undefined;
                    this._ajaxSettings.data.$inlinecount = "none";
                }
            };

            // update ajax setting for sort
            ODataCollectionView.prototype._updateSortAjaxSettings = function () {
                if (!this._ajaxSettings.data) {
                    this._ajaxSettings.data = {};
                }
                if (this.canSort && this.sortOnServer && this.sortDescriptions && this.sortDescriptions.length) {
                    this._ajaxSettings.data.$orderby = this._getOrderByExpression();
                } else {
                    this._ajaxSettings.data.$orderby = undefined;
                }
            };
            ODataCollectionView.prototype._getOrderByExpression = function () {
                var strSort = "", sdCount = this.sortDescriptions.length;
                for (var i = 0; i < sdCount; i++) {
                    var sd = this.sortDescriptions[i];
                    strSort += sd.property;
                    if (sd.ascending) {
                        strSort += " asc";
                    } else {
                        strSort += " desc";
                    }
                    if (i != sdCount - 1) {
                        strSort += ",";
                    }
                }
                return strSort;
            };

            // Get Data from OData service
            ODataCollectionView.prototype._requestData = function (url, ajaxSettings, isContinued) {
                if (typeof isContinued === "undefined") { isContinued = false; }
                if (url) {
                    this._isRequesting = true;
                    $.ajax(url, $.extend({}, ajaxSettings, {
                        success: isContinued ? this._continueSuccess.bind(this) : this._success.bind(this),
                        error: this._fail.bind(this)
                    }));
                }
            };

            // convert the properties of an item to the proper types
            ODataCollectionView.prototype._convertItem = function (item, dataTypes) {
                for (var k in item) {
                    if (dataTypes) {
                        for (var i = 0; i < dataTypes.length; i++) {
                            var name = dataTypes[i].name, type = dataTypes[i].type;
                            if ((wijmo.isString(name) && name == k) || (wijmo.tryCast(name, RegExp) && name.test(k))) {
                                var value = item[k];

                                // convert json date to javascript date
                                if (type === 4 /* Date */ && value) {
                                    value = value.replace("/Date(", "").replace(")/", "");
                                    value = new Date(parseInt(value, 10));
                                }
                                item[k] = wijmo.changeType(value, type, null);
                                break;
                            }
                        }
                    }
                }
            };

            // Send write request to the OData service for updating the database
            ODataCollectionView.prototype._requestWrite = function (operationType, item) {
                var self = this, updateValid = true, paras = [], writeUrl = self._serviceUrl + self._entityName, requestMethodType = wijmo.data.ODataCollectionView._requestMethodType[operationType], jsonEntity = operationType === 'Delete' ? null : JSON.stringify(self._preProcessData(item)), dataDTO, etag;

                if (jsonEntity === '{}') {
                    return;
                }

                if (item) {
                    if (operationType !== 'Add') {
                        if (self._keys) {
                            $.each(self._keys, function (_, key) {
                                var val = operationType === 'Update' ? self._edtClone[key] : item[key];
                                if (!!val) {
                                    paras.push(key + '=' + val);
                                } else {
                                    updateValid = false;
                                    return false;
                                }
                            });
                        }
                        if (updateValid) {
                            writeUrl += '(' + paras.join(', ') + ')';
                        }
                    }

                    if (updateValid) {
                        dataDTO = JSON.stringify(item);

                        if (operationType != "Add") {
                            dataDTO = JSON.parse(jsonEntity);

                            etag = !dataDTO ? item.etag : dataDTO.etag;

                            if (dataDTO) {
                                delete dataDTO.etag;
                                dataDTO.Created = new Date(dataDTO.Created);
                                dataDTO.Modified = new Date();

                                dataDTO = !jsonEntity ? null : JSON.stringify(dataDTO);
                            }
                            ;
                        }
                        if (self._isRequesting) {
                            return;
                        }
                        self._isRequesting = true;
                        $.ajax(writeUrl, {
                            type: requestMethodType,
                            contentType: 'application/json;',
                            beforeSend: function (jqXHR, settings) {
                                if (settings.type == "PUT" || settings.type == "DELETE") {
                                    if (etag) {
                                        jqXHR.setRequestHeader("If-Match", etag);
                                    }
                                }

                                if (settings.type != "DELETE") {
                                    jqXHR.setRequestHeader("Accept", "application/atomsvc+xml;q=0.8, application/json;odata=fullmetadata;q=0.7, application/json;q=0.5, */*;q=0.1");
                                }

                                if (self._encodeCreds) {
                                    jqXHR.setRequestHeader('Authorization', 'Basic ' + self._encodeCreds);
                                }
                            },
                            datatype: 'json',
                            data: jsonEntity,
                            success: function (data) {
                                if (operationType === 'Add') {
                                    if (self._keys) {
                                        $.each(self._keys, function (_, key) {
                                            if (item[key] === null || item[key] === undefined) {
                                                item[key] = data[key];
                                                item.RowVersion = data["RowVersion"];
                                                if (data["odata.etag"]) {
                                                    item.etag = data["odata.etag"];
                                                }
                                            }
                                        });
                                    }
                                    self.commitEdit();
                                }
                                self._isRequesting = false;
                            },
                            error: self._fail.bind(self)
                        });
                    }
                }
            };

            // To avoid the error 'Cannot convert a primitive value to the expected type' from the OData service.
            // We shall parse the number value to string first, when add or update data.
            ODataCollectionView.prototype._preProcessData = function (item) {
                var cloneItem = {};
                if (item) {
                    $.each(item, function (key, val) {
                        if (typeof val === 'number') {
                            cloneItem[key] = val.toString();
                        } else {
                            cloneItem[key] = val;
                        }
                    });
                }
                return cloneItem;
            };

            // Add a new handler when sortDescriptions is changed for sorting on server.
            ODataCollectionView.prototype._sortDescHandler = function () {
                var arr = this.sortDescriptions;
                for (var i = 0; i < arr.length; i++) {
                    var sd = wijmo.tryCast(arr[i], wijmo.collections.SortDescription);
                    if (!sd) {
                        throw 'sortDescriptions array must contain SortDescription objects.';
                    }
                }
                if (this.canSort) {
                    if (this.sortOnServer) {
                        this._queryData();
                    } else {
                        this.refresh();
                    }
                }
            };

            Object.defineProperty(ODataCollectionView.prototype, "TotalItemCount", {
                // Return the total item count of the OData service
                get: function () {
                    return isNaN(this._totalItemCount) ? 0 : this._totalItemCount;
                },
                enumerable: true,
                configurable: true
            });

            // Parse the filters property of the ODataCollectionViewSettings to
            // OData filter expressions
            ODataCollectionView._parseFilter = function (filters) {
                var _this = this;
                var expressions = [];

                $.each(filters, function (_, filter) {
                    var property = filter.property, value = filter.value, op = filter.operator, args = '(' + property + ', \'' + value + '\')', quote = true, expression = '';

                    if (!op) {
                        op = 'eq';
                    } else {
                        op = ODataCollectionView._filterOperators[filter.operator];
                    }

                    if (value instanceof Date) {
                        quote = false;
                        value = _this._convertDate(value);
                    }

                    switch (op) {
                        case 'contains':
                            expression = 'indexof' + args + ' ge 0';
                            break;
                        case 'notcontains':
                            expression = 'indexof' + args + ' lt 0';
                            break;
                        case 'beginswith':
                            expression = 'startswith' + args + ' eq true';
                            break;
                        case 'endswith':
                            expression = 'endswith' + args + ' eq true';
                            break;
                        default:
                            expression = filter.property + ' ' + op + ' ' + ((isNaN(filter.value) && quote) ? ('\'' + filter.value + '\'') : filter.value);
                            break;
                    }

                    expressions.push(expression);
                });

                return expressions.join(' and ');
            };

            // Convert the javascript Date to the date that OData protocol can accept.
            ODataCollectionView._convertDate = function (date, toUTC) {
                var pad = function (val) {
                    return (val < 10) ? '0' + val : val + '';
                };

                if (date) {
                    var dateStr = toUTC ? date.getUTCFullYear() + '-' + pad(date.getUTCMonth() + 1) + '-' + pad(date.getUTCDate()) + 'T' + pad(date.getUTCHours()) + ':' + pad(date.getUTCMinutes()) + ':' + pad(date.getUTCSeconds()) + 'Z' : date.getFullYear() + '-' + pad(date.getMonth() + 1) + '-' + pad(date.getDate()) + 'T' + pad(date.getHours()) + ':' + pad(date.getMinutes()) + ':' + pad(date.getSeconds());

                    return 'datetime\'' + dateStr + '\'';
                }

                return '';
            };
            ODataCollectionView._filterOperators = {
                '<': 'lt',
                '<=': 'le',
                '>': 'gt',
                '>=': 'ge',
                '==': 'eq',
                '!=': 'ne',
                less: 'lt',
                lessorequal: 'le',
                greater: 'gt',
                greaterorequal: 'ge',
                equals: 'eq',
                notequal: 'ne',
                contains: true,
                notcontains: true,
                beginswith: true,
                endswith: true
            };

            ODataCollectionView._requestMethodType = {
                Add: 'Post',
                Update: 'Put',
                Delete: 'Delete'
            };
            return ODataCollectionView;
        })(wijmo.collections.CollectionView);
        _data.ODataCollectionView = ODataCollectionView;
    })(wijmo.data || (wijmo.data = {}));
    var data = wijmo.data;
})(wijmo || (wijmo = {}));
//# sourceMappingURL=ODataCollectionView.js.map
