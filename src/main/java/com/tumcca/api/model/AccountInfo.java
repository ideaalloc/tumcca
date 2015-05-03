package com.tumcca.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Title.
 * <p>
 * Description.
 *
 * @author Bill Lv {@literal <billcc.lv@hotmail.com>}
 * @version 1.0
 * @since 2015-03-14
 */
public class AccountInfo {
    Long shopId;
    @Length(max = 32)
    String username;
    @NotBlank
    String password;
    Integer credit;

    public AccountInfo() {
    }

    public AccountInfo(Long shopId, String username, String password, Integer credit) {
        this.shopId = shopId;
        this.username = username;
        this.password = password;
        this.credit = credit;
    }

    @JsonProperty
    public Long getShopId() {
        return shopId;
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
    public Integer getCredit() {
        return credit;
    }
}
