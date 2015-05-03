package com.tumcca.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Title.
 * <p>
 * Description.
 *
 * @author Bill Lv {@literal <billcc.lv@hotmail.com>}
 * @version 1.0
 * @since 2015-03-20
 */
public class UserSecret {
    Long userId;
    String password;

    public UserSecret() {
    }

    public UserSecret(Long userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    @JsonProperty
    public Long getUserId() {
        return userId;
    }

    @JsonProperty
    public String getPassword() {
        return password;
    }
}
