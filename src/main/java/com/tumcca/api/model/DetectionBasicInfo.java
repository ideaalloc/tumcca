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
public class DetectionBasicInfo {
    Long regId;
    String accidentEliminationDetection; // 事故排除检测
    String appearanceDetection; // 外观检测
    String interiorDetection; // 内饰检测
    String electricalEquipmentDetection; // 电气设备检测
    String engineDetection; // 发动机检测
    String roadTest; // 路试检测
    Double expectedPrice; // 客户预期价格
    Double minPrice; // 公平价min * 1.05
    Double maxPrice; // 公平价max * 1.05

    public DetectionBasicInfo() {
    }

    public DetectionBasicInfo(Long regId, String accidentEliminationDetection, String appearanceDetection, String interiorDetection, String electricalEquipmentDetection, String engineDetection, String roadTest, Double expectedPrice, Double minPrice, Double maxPrice) {
        this.regId = regId;
        this.accidentEliminationDetection = accidentEliminationDetection;
        this.appearanceDetection = appearanceDetection;
        this.interiorDetection = interiorDetection;
        this.electricalEquipmentDetection = electricalEquipmentDetection;
        this.engineDetection = engineDetection;
        this.roadTest = roadTest;
        this.expectedPrice = expectedPrice;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    @JsonProperty
    public Long getRegId() {
        return regId;
    }

    @JsonProperty
    public String getAccidentEliminationDetection() {
        return accidentEliminationDetection;
    }

    @JsonProperty
    public String getAppearanceDetection() {
        return appearanceDetection;
    }

    @JsonProperty
    public String getInteriorDetection() {
        return interiorDetection;
    }

    @JsonProperty
    public String getElectricalEquipmentDetection() {
        return electricalEquipmentDetection;
    }

    @JsonProperty
    public String getEngineDetection() {
        return engineDetection;
    }

    @JsonProperty
    public String getRoadTest() {
        return roadTest;
    }

    @JsonProperty
    public Double getExpectedPrice() {
        return expectedPrice;
    }

    @JsonProperty
    public Double getMinPrice() {
        return minPrice;
    }

    @JsonProperty
    public Double getMaxPrice() {
        return maxPrice;
    }

    @Override
    public String toString() {
        return "DetectionBasicInfo{" +
                "regId=" + regId +
                ", accidentEliminationDetection='" + accidentEliminationDetection + '\'' +
                ", appearanceDetection='" + appearanceDetection + '\'' +
                ", interiorDetection='" + interiorDetection + '\'' +
                ", electricalEquipmentDetection='" + electricalEquipmentDetection + '\'' +
                ", engineDetection='" + engineDetection + '\'' +
                ", roadTest='" + roadTest + '\'' +
                ", expectedPrice=" + expectedPrice +
                ", minPrice=" + minPrice +
                ", maxPrice=" + maxPrice +
                '}';
    }
}
