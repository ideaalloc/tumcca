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
public class UserInfo {
    String credentials;
    Integer credit;
    Long shopId;
    String shopName;

    public UserInfo() {
    }

    public UserInfo(String credentials, Integer credit, Long shopId, String shopName) {
        this.credentials = credentials;
        this.credit = credit;
        this.shopId = shopId;
        this.shopName = shopName;
    }

    @JsonProperty
    public String getCredentials() {
        return credentials;
    }

    @JsonProperty
    public Integer getCredit() {
        return credit;
    }

    @JsonProperty
    public Long getShopId() {
        return shopId;
    }

    @JsonProperty
    public String getShopName() {
        return shopName;
    }
}
