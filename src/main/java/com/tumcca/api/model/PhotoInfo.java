package com.tumcca.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Title.
 * <p>
 * Description.
 *
 * @author Bill Lv {@literal <billcc.lv@hotmail.com>}
 * @version 1.0
 * @since 2015-03-21
 */
public class PhotoInfo {
    Long id;
    Long regId;
    String name;
    String storageName;
    Boolean status;

    public PhotoInfo() {
    }

    public PhotoInfo(Long id, Long regId, String name, String storageName, Boolean status) {
        this.id = id;
        this.regId = regId;
        this.name = name;
        this.storageName = storageName;
        this.status = status;
    }

    @JsonProperty
    public Long getId() {
        return id;
    }

    @JsonProperty
    public Long getRegId() {
        return regId;
    }

    @JsonProperty
    public String getName() {
        return name;
    }

    @JsonProperty
    public String getStorageName() {
        return storageName;
    }

    @JsonProperty
    public Boolean getStatus() {
        return status;
    }
}
