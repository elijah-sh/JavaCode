package com.fssc;

import java.util.Date;

public class Snapshot {
    private Long snapshotId;

    /**
     * 报账单ID
     */
    private Long claimHeaderId;

    /**
     * 区域编码 存表名
     */
    private String regionCode;

    /**
     * 区域行ID
     */
    private Long regionLineId;

    /**
     * 字段名称
     */
    private String componentCode;

    /**
     * 字段值
     */
    private String componentValue;

    private Long programApplicationId;

    private Date programUpdateDate;


    public Long getSnapshotId() {
        return snapshotId;
    }

    public void setSnapshotId(Long snapshotId) {
        this.snapshotId = snapshotId;
    }

    public Long getClaimHeaderId() {
        return claimHeaderId;
    }

    public void setClaimHeaderId(Long claimHeaderId) {
        this.claimHeaderId = claimHeaderId;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public Long getRegionLineId() {
        return regionLineId;
    }

    public void setRegionLineId(Long regionLineId) {
        this.regionLineId = regionLineId;
    }

    public String getComponentCode() {
        return componentCode;
    }

    public void setComponentCode(String componentCode) {
        this.componentCode = componentCode;
    }

    public String getComponentValue() {
        return componentValue;
    }

    public void setComponentValue(String componentValue) {
        this.componentValue = componentValue;
    }

    public Long getProgramApplicationId() {
        return programApplicationId;
    }

    public void setProgramApplicationId(Long programApplicationId) {
        this.programApplicationId = programApplicationId;
    }

    public Date getProgramUpdateDate() {
        return programUpdateDate;
    }


    public void setProgramUpdateDate(Date programUpdateDate) {
        this.programUpdateDate = programUpdateDate;
    }

    @Override
    public String toString() {
        return "Snapshot{" +
                ", regionCode='" + regionCode + '\'' +
                ", regionLineId=" + regionLineId +
                ", componentCode='" + componentCode + '\'' +
                ", componentValue='" + componentValue + '\'' +
                '}';
    }
}
