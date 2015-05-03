package com.tumcca.api.model.admin;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Title.
 * <p>
 * Description.
 *
 * @author Bill Lv {@literal <billcc.lv@hotmail.com>}
 * @version 1.0
 * @since 2015-03-18
 */
public class Admin {
    String username;
    Boolean status;

    public Admin() {
    }

    public Admin(String username, Boolean status) {
        this.username = username;
        this.status = status;
    }

    @JsonProperty
    public String getUsername() {
        return username;
    }

    @JsonProperty
    public Boolean getStatus() {
        return status;
    }
}
