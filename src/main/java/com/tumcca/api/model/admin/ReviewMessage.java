package com.tumcca.api.model.admin;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Title.
 * <p>
 * Description.
 *
 * @author Bill Lv {@literal <billcc.lv@hotmail.com>}
 * @version 1.0
 * @since 2015-03-24
 */
public class ReviewMessage {
    String username;
    Long regId;
    String process;

    public ReviewMessage() {
    }

    public ReviewMessage(String username, Long regId, String process) {
        this.username = username;
        this.regId = regId;
        this.process = process;
    }

    @JsonProperty
    public String getUsername() {
        return username;
    }

    @JsonProperty
    public Long getRegId() {
        return regId;
    }

    @JsonProperty
    public String getProcess() {
        return process;
    }

    @Override
    public String toString() {
        return "ReviewMessage{" +
                "username='" + username + '\'' +
                ", regId=" + regId +
                ", process='" + process + '\'' +
                '}';
    }
}
