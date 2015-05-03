package com.tumcca.api.model.admin;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Title.
 * <p>
 * Description.
 *
 * @author Bill Lv {@literal <billcc.lv@hotmail.com>}
 * @version 1.0
 * @since 2015-03-22
 */
public class Review {
    Long regId;
    String process;
    String message;
    Integer integral;
    String auditor;
    Date createTime;

    public Review() {
    }

    public Review(Long regId, String process, String message, Integer integral, String auditor, Date createTime) {
        this.regId = regId;
        this.process = process;
        this.message = message;
        this.integral = integral;
        this.auditor = auditor;
        this.createTime = createTime;
    }

    @JsonProperty
    public Long getRegId() {
        return regId;
    }

    @JsonProperty
    public String getProcess() {
        return process;
    }

    @JsonProperty
    public String getMessage() {
        return message;
    }

    @JsonProperty
    public Integer getIntegral() {
        return integral;
    }

    @JsonProperty
    public String getAuditor() {
        return auditor;
    }

    @JsonProperty
    public Date getCreateTime() {
        return createTime;
    }
}
