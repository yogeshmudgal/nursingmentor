package com.dakshata.mentor.models;

import java.util.ArrayList;

/**
 * Created by Rakesh Prajapat on 2019-05-30
 * Copyright (c) 2019. All rights reserved by mobikode studio.
 * Last modified 15:04
 */
public class ClientFeedbackDto {

    private int selected_client;
    private int qno;
    private String qText;
    private String qAnswer;
    private String client_ipd_no;
    private String client_pcts_id;
    private ArrayList<ClientFeedbackDto> questionList;
    private String dateOfFeedback;

    public String getDateOfFeedback() {
        return dateOfFeedback;
    }

    public void setDateOfFeedback(String dateOfFeedback) {
        this.dateOfFeedback = dateOfFeedback;
    }

    public ClientFeedbackDto(int qno, String qText, String qAnswer){
        this.qno=qno;
        this.qText=qText;
        this.qAnswer=qAnswer;
    }
    public ClientFeedbackDto(){
    }
    public int getSelected_client() {
        return selected_client;
    }

    public void setSelected_client(int selected_client) {
        this.selected_client = selected_client;
    }

    public int getQno() {
        return qno;
    }

    public void setQno(int qno) {
        this.qno = qno;
    }

    public String getqText() {
        return qText;
    }

    public void setqText(String qText) {
        this.qText = qText;
    }

    public String getqAnswer() {
        return qAnswer;
    }

    public void setqAnswer(String qAnswer) {
        this.qAnswer = qAnswer;
    }

    public String getClient_ipd_no() {
        return client_ipd_no;
    }

    public void setClient_ipd_no(String client_ipd_no) {
        this.client_ipd_no = client_ipd_no;
    }

    public String getClient_pcts_id() {
        return client_pcts_id;
    }

    public void setClient_pcts_id(String client_pcts_id) {
        this.client_pcts_id = client_pcts_id;
    }

    public ArrayList<ClientFeedbackDto> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(ArrayList<ClientFeedbackDto> questionList) {
        this.questionList = questionList;
    }
}
