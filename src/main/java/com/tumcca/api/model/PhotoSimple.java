package com.tumcca.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Title.
 * <p>
 * Description.
 *
 * @author Bill Lv {@literal <billcc.lv@hotmail.com>}
 * @version 1.0
 * @since 2015-03-12
 */
public class PhotoSimple {
    Long regId;
    Long picId; // 照片编码

    public PhotoSimple() {
    }

    public PhotoSimple(Long regId, Long picId) {
        this.regId = regId;
        this.picId = picId;
    }

    @JsonProperty
    public Long getRegId() {
        return regId;
    }

    @JsonProperty
    public Long getPicId() {
        return picId;
    }
}
