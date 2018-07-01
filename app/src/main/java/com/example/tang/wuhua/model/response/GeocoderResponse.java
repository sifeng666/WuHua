package com.example.tang.wuhua.model.response;

import com.google.gson.annotations.SerializedName;

/**
 * 根据经纬度查找地址的 返回model
 * @author z1ycheng
 */

public class GeocoderResponse {

    // 状态码
    private int status;

    // 结构化地址信息
    @SerializedName("formatted_address")
    private int formattedAddress;

    // 可信度
    private int confidence;

    // 坐标所在商圈信息
    private String business;

    // 国家
    private String country;

    // 省名
    private String province;

    // 城市名
    private String city;

    // 区县名
    private String district;

    // 乡镇名
    private String town;

    // 街道名
    private String street;

    // 街道门牌号
    @SerializedName("street_number")
    private String streetNumber;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(int formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public int getConfidence() {
        return confidence;
    }

    public void setConfidence(int confidence) {
        this.confidence = confidence;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    @Override
    public String toString() {
        return "GeocoderResponse{" +
                "status=" + status +
                ", formattedAddress=" + formattedAddress +
                ", confidence=" + confidence +
                ", business='" + business + '\'' +
                ", country='" + country + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", town='" + town + '\'' +
                ", street='" + street + '\'' +
                ", streetNumber='" + streetNumber + '\'' +
                '}';
    }
}
