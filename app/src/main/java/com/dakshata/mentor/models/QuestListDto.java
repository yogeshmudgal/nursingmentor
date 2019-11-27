package com.dakshata.mentor.models;

import java.util.ArrayList;

/**
 * Created by Rakesh Prajapat on 17/01/19
 * Copyright (c) 2019. All rights reserved by mobikode studio.
 * Last modified 3:02 PM
 */
public class QuestListDto {

    private ArrayList<QuestListDto> ques_array;
    private ArrayList<QuestListDto> sub_ques_array;

    private String q_id, q_text, q_ans,q_code;
    private String s_q_id,s_q_text,s_q_ans,s_q_code;
    private int q_selection;

    public int getQ_total_selected() {
        return q_total_selected;
    }

    public void setQ_total_selected(int q_total_selected) {
        this.q_total_selected = q_total_selected;
    }

    private int q_total_selected;

    public int getQ_selection() {
        return q_selection;
    }

    public void setQ_selection(int q_selection) {
        this.q_selection = q_selection;
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

    public String getS_q_ans() {
        return s_q_ans;
    }

    public void setS_q_ans(String s_q_ans) {
        this.s_q_ans = s_q_ans;
    }

    public String getS_q_code() {
        return s_q_code;
    }

    public void setS_q_code(String s_q_code) {
        this.s_q_code = s_q_code;
    }

    public String getQ_id() {
        return q_id;
    }

    public void setQ_id(String q_id) {
        this.q_id = q_id;
    }

    public String getQ_text() {
        return q_text;
    }

    public void setQ_text(String q_text) {
        this.q_text = q_text;
    }

    public String getQ_ans() {
        return q_ans;
    }

    public void setQ_ans(String q_ans) {
        this.q_ans = q_ans;
    }

    public String getQ_code() {
        return q_code;
    }

    public void setQ_code(String q_code) {
        this.q_code = q_code;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    private boolean isSelected;



    public ArrayList<QuestListDto> getQues_array() {
        return ques_array;
    }

    public void setQues_array(ArrayList<QuestListDto> ques_array) {
        this.ques_array = ques_array;
    }

    public ArrayList<QuestListDto> getSub_ques_array() {
        return sub_ques_array;
    }

    public void setSub_ques_array(ArrayList<QuestListDto> sub_ques_array) {
        this.sub_ques_array = sub_ques_array;
    }

}