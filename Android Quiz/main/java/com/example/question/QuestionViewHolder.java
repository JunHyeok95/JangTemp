package com.example.question;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class QuestionViewHolder extends RecyclerView.ViewHolder {

    public TextView textViewQuestion;
    public TextView textViewType;
    public TextView textViewTime;

    public QuestionViewHolder(@NonNull View itemView) {
        super(itemView);

        textViewQuestion = itemView.findViewById(R.id.textViewQuestion);
        textViewType = itemView.findViewById(R.id.textViewType);
        textViewTime = itemView.findViewById(R.id.textViewTime);
    }
}
