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
public class CarVersion {
    String brand;
    String model;
    String version;

    public CarVersion() {
    }

    public CarVersion(String brand, String model, String version) {
        this.brand = brand;
        this.model = model;
        this.version = version;
    }

    @JsonProperty
    public String getBrand() {
        return brand;
    }

    @JsonProperty
    public String getModel() {
        return model;
    }

    @JsonProperty
    public String getVersion() {
        return version;
    }
}
