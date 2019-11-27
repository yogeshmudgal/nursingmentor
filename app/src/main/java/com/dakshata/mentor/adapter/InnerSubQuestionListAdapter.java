package com.dakshata.mentor.adapter;

/**
 * Created by Rakesh Prajapat on 17/01/19
 * Copyright (c) 2019. All rights reserved by mobikode studio.
 * Last modified 3:01 PM
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.dakshata.mentor.R;
import com.dakshata.mentor.listener.RadioButtonSelection;
import com.dakshata.mentor.models.QuestListDto;

import java.util.List;

public class InnerSubQuestionListAdapter extends RecyclerView.Adapter<InnerSubQuestionListAdapter.MyViewHolder> {

    private List<QuestListDto> questionList;
    private RadioButtonSelection listener;
    private int parentQuestionPosition;
    //    final static int scoreView = 1;
    final static int mainView = 0;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_sq, tv_score;
        public RecyclerView recyclerView;
        public RadioGroup radioGroup;
        public RadioButton r_yes, r_no;

        public MyViewHolder(View view) {
            super(view);
            tv_sq = (TextView) view.findViewById(R.id.tv_sq);
            tv_score = (TextView) view.findViewById(R.id.tv_score);
            radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
            r_yes = (RadioButton) view.findViewById(R.id.r_yes);
            r_no = (RadioButton) view.findViewById(R.id.r_no);
        }
    }

    @Override
    public int getItemViewType(int position) {
//        if ((questionList.size()) == position) {
//            return scoreView;
//        } else {
        return mainView;
//        }
    }

    public InnerSubQuestionListAdapter(int parentQuestionPosition, List<QuestListDto> questionList, RadioButtonSelection listener) {
        this.questionList = questionList;
        this.listener = listener;
        this.parentQuestionPosition = parentQuestionPosition;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;
        switch (viewType) {
//            case scoreView:
//                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.inner_score_row, parent, false);
//                break;
            case mainView:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.inner_row, parent, false);
                break;
        }
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        QuestListDto questListDto = questionList.get(position);
        holder.tv_sq.setText(questListDto.getS_q_text());

        if (questListDto.isSelected()) {
            questionList.get(position).setSelected(true);
            if (questListDto.getS_q_ans().equalsIgnoreCase("")) {
                holder.r_no.setChecked(false);
                holder.r_yes.setChecked(false);
            } else if (questListDto.getS_q_ans().equalsIgnoreCase("Yes")) {
                holder.r_no.setChecked(false);
                holder.r_yes.setChecked(true);
            } else {
                holder.r_no.setChecked(true);
                holder.r_yes.setChecked(false);
            }
        } else {
            holder.r_no.setChecked(false);
            holder.r_yes.setChecked(false);
        }

        holder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (radioGroup.getCheckedRadioButtonId()) {
                    case R.id.r_yes:
                        questionList.get(position).setSelected(true);
                        listener.onSelected(parentQuestionPosition, position, 1);
                        break;

                    case R.id.r_no:
                        questionList.get(position).setSelected(true);
                        listener.onSelected(parentQuestionPosition, position, 2);
                        break;
                    default:
                        questionList.get(position).setSelected(false);
                        listener.onSelected(parentQuestionPosition, position, 0);
                        break;
                }
            }
        });

        if ((questionList.size() - 1) == position) {
            int tempTotalSelected = 0;
            for (int i = 0; i < (questionList.size()); i++) {
                if (questionList.get(i).getS_q_ans().equalsIgnoreCase("Yes")) {
                    tempTotalSelected++;
                    listener.parentQuestionListSelectionMark(parentQuestionPosition, 1, tempTotalSelected);
                } else if (questionList.get(i).getS_q_ans().equalsIgnoreCase("No")) {
                    tempTotalSelected++;
                    listener.parentQuestionListSelectionMark(parentQuestionPosition, 2, tempTotalSelected);
                } else {
                    listener.parentQuestionListSelectionMark(parentQuestionPosition, 0, tempTotalSelected);
                }
            }
            listener.parentQuestionListSelectionMark(parentQuestionPosition, 0, tempTotalSelected);
        }

    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }
}