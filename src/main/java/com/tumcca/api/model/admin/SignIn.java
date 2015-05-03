package com.tumcca.api.model.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Title.
 * <p>
 * Description.
 *
 * @author Bill Lv {@literal <billcc.lv@hotmail.com>}
 * @version 1.0
 * @since 2015-03-18
 */
public class SignIn {
    @NotBlank
    String username;
    @NotBlank
    String password;

    public SignIn() {
    }

    public SignIn(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @JsonProperty
    public String getUsername() {
        return username;
    }

    @JsonProperty
    public String getPassword() {
        return password;
    }
}
