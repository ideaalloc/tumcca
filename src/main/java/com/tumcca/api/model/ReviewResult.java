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
public class ReviewResult {
    Boolean status; // 审核通过的状态 false审核不通过，true审核通过
    String message; // 审核不通过
    Integer integral; // 审核通过返回积分，以后会有积分规则

    public ReviewResult() {
    }

    public ReviewResult(Boolean status, String message, Integer integral) {
        this.status = status;
        this.message = message;
        this.integral = integral;
    }

    @JsonProperty
    public Boolean getStatus() {
        return status;
    }

    @JsonProperty
    public String getMessage() {
        return message;
    }

    @JsonProperty
    public Integer getIntegral() {
        return integral;
    }
}
