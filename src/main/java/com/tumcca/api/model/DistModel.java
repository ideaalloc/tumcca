package com.tumcca.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Title.
 * <p/>
 * Description.
 *
 * @author Bill Lv {@literal <billcc.lv@hotmail.com>}
 * @version 1.0
 * @since 2015-03-17
 */
public class DistModel {
    Long key;
    String value;

    public DistModel() {
    }

    public DistModel(Long key, String value) {
        this.key = key;
        this.value = value;
    }

    @JsonProperty
    public Long getKey() {
        return key;
    }

    @JsonProperty
    public String getValue() {
        return value;
    }
}
