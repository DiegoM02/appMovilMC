package com.e.appmc;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class QuestionRatingListAdapter extends RecyclerView.Adapter<QuestionRatingListAdapter.ViewHolder> {

    private ArrayList<QuestionRating> questions;
    private PersonalSummaryActivity activity;
    private Context context;

    public QuestionRatingListAdapter(ArrayList<QuestionRating> questions,PersonalSummaryActivity activity,Context mContext)
    {
        this.questions = questions;
        this.activity = activity;
        context = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.question_list_item,viewGroup,false);
        QuestionRatingListAdapter.ViewHolder viewHolder = new QuestionRatingListAdapter.ViewHolder(view);
        context = viewGroup.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.textQuestion.setText(questions.get(i).getName());
        viewHolder.textPersonal.setText("Calficacion: " + questions.get(i).getPoint());

    }

    @Override
    public int getItemCount() {
        return this.questions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        TextView textQuestion;
        //CardView cv;
        TextView textPersonal;

        public ViewHolder(View itemView)
        {
            super(itemView);
            textQuestion = (TextView)itemView.findViewById(R.id.textQuestion);
            //cv = (CardView)itemView.findViewById(R.id.cardView);
            textPersonal = (TextView) itemView.findViewById(R.id.textPersonal);
        }
    }
}
