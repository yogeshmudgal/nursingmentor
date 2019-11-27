package com.dakshata.mentor.listener;

public interface RadioButtonSelection {

    void onSelected (int parentQuestionPosition, int subQuestionPosition, int isSelected);
    void parentQuestionListSelectionMark(int parentQuestionPosition, int isSelected, int tempTotalSelected);
}
