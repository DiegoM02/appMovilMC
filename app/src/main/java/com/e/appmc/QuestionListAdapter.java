package com.e.appmc;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class QuestionListAdapter extends RecyclerView.Adapter<QuestionListAdapter.ViewHolder>
{
    private ArrayList<QuestionPersonal> questions;
    private PersonalSummaryActivity activity;
    private Context context;

    public QuestionListAdapter(ArrayList<QuestionPersonal> questions,PersonalSummaryActivity activity)
    {
        this.questions = questions;
        this.activity = activity;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.question_list_item,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        context = viewGroup.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.textQuestion.setText(questions.get(i).getQuestionName());
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        TextView textQuestion;
        CardView cv;
        TextView textPersonal;

        public ViewHolder(View itemView)
        {
            super(itemView);
            textQuestion = (TextView)itemView.findViewById(R.id.textQuestion);
            cv = (CardView)itemView.findViewById(R.id.cardView);
            textPersonal = (TextView) itemView.findViewById(R.id.textPersonal);
        }
    }
}
