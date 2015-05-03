package com.tumcca.api.model.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Title.
 * <p>
 * Description.
 *
 * @author Bill Lv {@literal <billcc.lv@hotmail.com>}
 * @version 1.0
 * @since 2015-03-18
 */
public class SignUp {
    @NotEmpty
    String username;
    @NotEmpty
    String password;
    Boolean status;

    public SignUp() {
    }

    public SignUp(String username, String password, Boolean status) {
        this.username = username;
        this.password = password;
        this.status = status;
    }

    @JsonProperty
    public String getUsername() {
        return username;
    }

    @JsonProperty
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public Boolean getStatus() {
        return status;
    }
}
