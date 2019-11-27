package com.dakshata.mentor.adapter;

/**
 * Created by Rakesh Prajapat on 17/01/19
 * Copyright (c) 2019. All rights reserved by mobikode studio.
 * Last modified 3:01 PM
 */
import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dakshata.mentor.R;
import com.dakshata.mentor.listener.RadioButtonSelection;
import com.dakshata.mentor.models.QuestListDto;

import java.util.List;

public class QuestionListAdapter extends RecyclerView.Adapter<QuestionListAdapter.MyViewHolder> {

    private List<QuestListDto> questionList;
    private InnerSubQuestionListAdapter mAdapter;
    private Context context;
    private RadioButtonSelection listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView qTitle;
        public RecyclerView recyclerView;

        public MyViewHolder(View view) {
            super(view);
            qTitle = (TextView) view.findViewById(R.id.qTitle);
            recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        }
    }


    public QuestionListAdapter(Context context, List<QuestListDto> questionList, RadioButtonSelection listener) {
        this.context = context;
        this.questionList = questionList;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.question_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        QuestListDto questListDto = questionList.get(position);
        holder.qTitle.setText("Q."+(position+1)+"- "+questListDto.getQ_text());
        mAdapter = new InnerSubQuestionListAdapter(position, questListDto.getSub_ques_array(), listener);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        holder.recyclerView.setLayoutManager(mLayoutManager);
        holder.recyclerView.setItemAnimator(new DefaultItemAnimator());
        holder.recyclerView.setAdapter(mAdapter);



    }


    @Override
    public int getItemCount() {
        return questionList.size();
    }
}