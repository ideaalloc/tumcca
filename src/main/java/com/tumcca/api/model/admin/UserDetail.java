package com.tumcca.api.model.admin;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Title.
 * <p>
 * Description.
 *
 * @author Bill Lv {@literal <billcc.lv@hotmail.com>}
 * @version 1.0
 * @since 2015-03-22
 */
public class UserDetail {
    String shopName;
    String username;
    Integer credit;

    public UserDetail() {
    }

    public UserDetail(String shopName, String username, Integer credit) {
        this.shopName = shopName;
        this.username = username;
        this.credit = credit;
    }

    @JsonProperty
    public String getShopName() {
        return shopName;
    }

    @JsonProperty
    public String getUsername() {
        return username;
    }

    @JsonProperty
    public Integer getCredit() {
        return credit;
    }
}
