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
public class User {
    Long userId;
    Long shopId;
    String username;
    Integer credit;

    public User() {
    }

    public User(Long userId, Long shopId, String username, Integer credit) {
        this.userId = userId;
        this.shopId = shopId;
        this.username = username;
        this.credit = credit;
    }

    @JsonProperty
    public Long getUserId() {
        return userId;
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
    public Integer getCredit() {
        return credit;
    }
}
