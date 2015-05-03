package com.tumcca.api.model.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

/**
 * Title.
 * <p>
 * Description.
 *
 * @author Bill Lv {@literal <billcc.lv@hotmail.com>}
 * @version 1.0
 * @since 2015-03-22
 */
public class ReviewReports {
    Long regId;
    String carId;
    String ownerName;
    String ownerPhone;
    String brand;
    String model;
    String version;
    Double mileage;
    Integer transferTimes;
    Date regDate;
    String accEliDetect;
    String appearanceDetect;
    String interiorDetect;
    String elecEqpDetect;
    String engineDetect;
    String roadTest;
    Double expPrice;
    Double minPrice;
    Double maxPrice;
    List<Long> photoIds;
    String process;
    String message;
    Integer integral;
    String auditor;

    public ReviewReports() {
    }

    public ReviewReports(Long regId, String carId, String ownerName, String ownerPhone, String brand, String model, String version, Double mileage, Integer transferTimes, Date regDate, String accEliDetect, String appearanceDetect, String interiorDetect, String elecEqpDetect, String engineDetect, String roadTest, Double expPrice, Double minPrice, Double maxPrice, List<Long> photoIds, String process, String message, Integer integral, String auditor) {
        this.regId = regId;
        this.carId = carId;
        this.ownerName = ownerName;
        this.ownerPhone = ownerPhone;
        this.brand = brand;
        this.model = model;
        this.version = version;
        this.mileage = mileage;
        this.transferTimes = transferTimes;
        this.regDate = regDate;
        this.accEliDetect = accEliDetect;
        this.appearanceDetect = appearanceDetect;
        this.interiorDetect = interiorDetect;
        this.elecEqpDetect = elecEqpDetect;
        this.engineDetect = engineDetect;
        this.roadTest = roadTest;
        this.expPrice = expPrice;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.photoIds = photoIds;
        this.process = process;
        this.message = message;
        this.integral = integral;
        this.auditor = auditor;
    }

    @JsonProperty
    public Long getRegId() {
        return regId;
    }

    @JsonProperty
    public String getCarId() {
        return carId;
    }

    @JsonProperty
    public String getOwnerName() {
        return ownerName;
    }

    @JsonProperty
    public String getOwnerPhone() {
        return ownerPhone;
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

    @JsonProperty
    public Double getMileage() {
        return mileage;
    }

    @JsonProperty
    public Integer getTransferTimes() {
        return transferTimes;
    }

    @JsonProperty
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    public Date getRegDate() {
        return regDate;
    }

    @JsonProperty
    public String getAccEliDetect() {
        return accEliDetect;
    }

    @JsonProperty
    public String getAppearanceDetect() {
        return appearanceDetect;
    }

    @JsonProperty
    public String getInteriorDetect() {
        return interiorDetect;
    }

    @JsonProperty
    public String getElecEqpDetect() {
        return elecEqpDetect;
    }

    @JsonProperty
    public String getEngineDetect() {
        return engineDetect;
    }

    @JsonProperty
    public String getRoadTest() {
        return roadTest;
    }

    @JsonProperty
    public Double getExpPrice() {
        return expPrice;
    }

    @JsonProperty
    public Double getMinPrice() {
        return minPrice;
    }

    @JsonProperty
    public Double getMaxPrice() {
        return maxPrice;
    }

    @JsonProperty
    public List<Long> getPhotoIds() {
        return photoIds;
    }

    @JsonProperty
    public String getProcess() {
        return process;
    }

    @JsonProperty
    public String getMessage() {
        return message;
    }

    @JsonProperty
    public Integer getIntegral() {
        return integral;
    }

    @JsonProperty
    public String getAuditor() {
        return auditor;
    }
}
