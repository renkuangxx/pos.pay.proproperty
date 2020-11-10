package com.jingpai.pos.customer.bean;

import com.blankj.utilcode.util.StringUtils;

import java.io.Serializable;

/**
 * 门禁设备表
 */
public class AccessBean implements Serializable {

  private static final long serialVersionUID = 1L;

  private String id;

  private int communityId;

  private int unitId;

  private String name;

  private String sn;
  private String appKey;
  private String mac;

  private boolean delState;

  private String createTime;

  private String modifyTime;

  private String unit;

  private String building;

  private Long buildingId;

  public static long getSerialVersionUID() {
    return serialVersionUID;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public int getCommunityId() {
    return communityId;
  }

  public void setCommunityId(int communityId) {
    this.communityId = communityId;
  }

  public int getUnitId() {
    return unitId;
  }

  public void setUnitId(int unitId) {
    this.unitId = unitId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSn() {
    return sn;
  }

  public void setSn(String sn) {
    this.sn = sn;
  }

  public boolean isDelState() {
    return delState;
  }

  public void setDelState(boolean delState) {
    this.delState = delState;
  }

  public String getCreateTime() {
    return createTime;
  }

  public void setCreateTime(String createTime) {
    this.createTime = createTime;
  }

  public String getModifyTime() {
    return modifyTime;
  }

  public void setModifyTime(String modifyTime) {
    this.modifyTime = modifyTime;
  }

  public String getUnit() {
    return unit;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }

  public String getBuilding() {
    return building;
  }

  public void setBuilding(String building) {
    this.building = building;
  }

  public Long getBuildingId() {
    return buildingId;
  }

  public void setBuildingId(Long buildingId) {
    this.buildingId = buildingId;
  }

  public String getAppKey() {
    return appKey;
  }

  public void setAppKey(String appKey) {
    this.appKey = appKey;
  }

  public String getMac() {
    return mac;
  }

  public void setMac(String mac) {
    this.mac = mac;
  }

  public String getFullName() {
    StringBuilder fullName = new StringBuilder();

    if (!StringUtils.isEmpty(building)) {
      fullName.append(building);
      fullName.append("栋");
    }

    if (!StringUtils.isEmpty(unit)) {
      fullName.append(unit);
      fullName.append("单元");
    }

    fullName.append(name);
    fullName.append("门禁");
    return fullName.toString();
  }

  @Override
  public String toString() {
    return "AccessBean{" +
            "id='" + id + '\'' +
            ", communityId=" + communityId +
            ", unitId=" + unitId +
            ", name='" + name + '\'' +
            ", sn='" + sn + '\'' +
            ", appKey='" + appKey + '\'' +
            ", mac='" + mac + '\'' +
            ", delState=" + delState +
            ", createTime='" + createTime + '\'' +
            ", modifyTime='" + modifyTime + '\'' +
            ", unit='" + unit + '\'' +
            ", building='" + building + '\'' +
            ", buildingId=" + buildingId +
            '}';
  }
}
