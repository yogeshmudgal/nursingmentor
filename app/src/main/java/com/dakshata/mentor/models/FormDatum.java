
package com.dakshata.mentor.models;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FormDatum implements Serializable
{

    @SerializedName("q_code")
    @Expose
    private String qCode;
    @SerializedName("ans")
    @Expose
    private String ans;
    @SerializedName("loc_code")
    @Expose
    private String loc_code;

    private final static long serialVersionUID = 9037510033968374987L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public FormDatum() {
    }

    public String getLoc_code() {
        return loc_code;
    }

    public void setLoc_code(String loc_code) {
        this.loc_code = loc_code;
    }

    /**
     * 
     * @param qCode
     * @param ans
     */
    public FormDatum(String qCode, String ans,String loc_code) {
        super();
        this.qCode = qCode;
        this.ans = ans;
        this.loc_code=loc_code;
    }

    public String getQCode() {
        return qCode;
    }

    public void setQCode(String qCode) {
        this.qCode = qCode;
    }

    public FormDatum withQCode(String qCode) {
        this.qCode = qCode;
        return this;
    }

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        if (ans==null){
            ans="";
        }
        this.ans = ans;
    }

    public FormDatum withAns(String ans) {
        this.ans = ans;
        return this;
    }

}
