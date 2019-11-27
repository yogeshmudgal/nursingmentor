package com.dakshata.mentor.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FormDataSubQuestion implements Serializable {

    @SerializedName("s_q_id")
    @Expose
    String s_q_id;
    @SerializedName("s_q_text")
    @Expose
    String s_q_text;
    @SerializedName("s_q_code")
    @Expose
    String s_q_code;
    @SerializedName("s_q_ans")
    @Expose
    String s_q_ans;

    public FormDataSubQuestion(){

    }

    public FormDataSubQuestion(String s_q_id, String s_q_text, String s_q_code, String s_q_ans) {
        this.s_q_id = s_q_id;
        this.s_q_text = s_q_text;
        this.s_q_code = s_q_code;
        this.s_q_ans = s_q_ans;
    }

    public String getS_q_id() {
        return s_q_id;
    }

    public void setS_q_id(String s_q_id) {
        this.s_q_id = s_q_id;
    }

    public String getS_q_text() {
        return s_q_text;
    }

    public void setS_q_text(String s_q_text) {
        this.s_q_text = s_q_text;
    }

    public String getS_q_code() {
        return s_q_code;
    }

    public void setS_q_code(String s_q_code) {
        this.s_q_code = s_q_code;
    }

    public String getS_q_ans() {
        return s_q_ans;
    }

    public void setS_q_ans(String s_q_ans) {
        this.s_q_ans = s_q_ans;
    }
}
