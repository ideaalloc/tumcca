package com.tumcca.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Title.
 * <p/>
 * Description.
 *
 * @author Bill Lv {@literal <billcc.lv@hotmail.com>}
 * @version 1.0
 * @since 2015-03-12
 */
public class ResponseMessage<T> {
    String code;
    String msg;
    T data;

    public ResponseMessage() {
    }

    public ResponseMessage(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    @JsonProperty
    public String getCode() {
        return code;
    }

    @JsonProperty
    public String getMsg() {
        return msg;
    }

    @JsonProperty
    public T getData() {
        return data;
    }
}
