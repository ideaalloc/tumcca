package com.tumcca.api.model.admin;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Title.
 * <p/>
 * Description.
 *
 * @author Bill Lv {@literal <billcc.lv@hotmail.com>}
 * @version 1.0
 * @since 2015-03-18
 */
public class DataTable<T> {
    Integer draw;
    Integer recordsTotal;
    Integer recordsFiltered;
    T data;

    public DataTable() {
    }

    public DataTable(Integer draw, Integer recordsTotal, Integer recordsFiltered, T data) {
        this.draw = draw;
        this.recordsTotal = recordsTotal;
        this.recordsFiltered = recordsFiltered;
        this.data = data;
    }

    @JsonProperty
    public Integer getDraw() {
        return draw;
    }

    @JsonProperty
    public Integer getRecordsTotal() {
        return recordsTotal;
    }

    @JsonProperty
    public Integer getRecordsFiltered() {
        return recordsFiltered;
    }

    @JsonProperty
    public T getData() {
        return data;
    }
}
