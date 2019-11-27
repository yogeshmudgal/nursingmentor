package com.dakshata.mentor;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * Created by Umesh on 2/20/2018.
 */
public class LocationMaster {

    @SerializedName("district")
    @Expose
    private String district;
    @SerializedName("block_code")
    @Expose
    private String blockCode;
    @SerializedName("facility")
    @Expose
    private String facility;
    @SerializedName("facility_type")
    @Expose
    private String facilityType;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("facility_code")
    @Expose
    private String facilityCode;
    @SerializedName("block")
    @Expose
    private String block;
    @SerializedName("district_code")
    @Expose
    private String districtCode;
    @SerializedName("state_code")
    @Expose
    private String stateCode;
    @SerializedName("subfacility_code")
    @Expose
    private String subfacilityCode;
    @SerializedName("subfacility")
    @Expose
    private String subfacility;

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getBlockCode() {
        return blockCode;
    }

    public void setBlockCode(String blockCode) {
        this.blockCode = blockCode;
    }

    public String getFacility() {
        return facility;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }

    public String getFacilityType() {
        return facilityType;
    }

    public void setFacilityType(String facilityType) {
        this.facilityType = facilityType;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getFacilityCode() {
        return facilityCode;
    }

    public void setFacilityCode(String facilityCode) {
        this.facilityCode = facilityCode;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getSubfacilityCode() {
        return subfacilityCode;
    }

    public void setSubfacilityCode(String subfacilityCode) {
        this.subfacilityCode = subfacilityCode;
    }

    public String getSubfacility() {
        return subfacility;
    }

    public void setSubfacility(String subfacility) {
        this.subfacility = subfacility;
    }

}

