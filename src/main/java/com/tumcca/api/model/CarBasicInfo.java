package com.tumcca.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;

/**
 * Title.
 * <p>
 * Description.
 *
 * @author Bill Lv {@literal <billcc.lv@hotmail.com>}
 * @version 1.0
 * @since 2015-03-12
 */
public class CarBasicInfo {
    Long regId; // 注册ID
    @NotBlank
    String carId; // 所提交车辆的唯一标识
    @NotBlank
    String ownerName; // 车主姓名
    String ownerPhone; // 车主电话
    Long versionId; // 款号ID
    Double mileage; // 里程
    Integer transferTimes; // 过户次数
    Date regDate; // 上牌时间

    public CarBasicInfo() {
    }

    public CarBasicInfo(Long regId, String carId, String ownerName, String ownerPhone, Long versionId, Double mileage, Integer transferTimes, Date regDate) {
        this.regId = regId;
        this.carId = carId;
        this.ownerName = ownerName;
        this.ownerPhone = ownerPhone;
        this.versionId = versionId;
        this.mileage = mileage;
        this.transferTimes = transferTimes;
        this.regDate = regDate;
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
    public Long getVersionId() {
        return versionId;
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

    @Override
    public String toString() {
        return "CarBasicInfo{" +
                "regId=" + regId +
                ", carId='" + carId + '\'' +
                ", ownerName='" + ownerName + '\'' +
                ", ownerPhone='" + ownerPhone + '\'' +
                ", versionId=" + versionId +
                ", mileage=" + mileage +
                ", transferTimes=" + transferTimes +
                ", regDate=" + regDate +
                '}';
    }
}
