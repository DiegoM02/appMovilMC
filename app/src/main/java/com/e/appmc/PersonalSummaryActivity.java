package com.e.appmc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.e.bd.appmc.Question;

import java.util.ArrayList;
import java.util.HashMap;

public class PersonalSummaryActivity extends AppCompatActivity {
    private ArrayList<QuestionPersonal> questions;
    private TextView textPoint;
    private RecyclerView questionList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_summary);
        textPoint = findViewById(R.id.textPunto);
        questionList = (RecyclerView) findViewById(R.id.questionList);
        textPoint.setText(getIntent().getExtras().getString("point"));
        rellenarQuestions();
        setQuestionsListAdapter();



    }

    public void rellenarQuestions()
    {
        this.questions = new ArrayList<>();
        Intent intent = getIntent();
        HashMap<String,ArrayList<String>> resume = (HashMap<String, ArrayList<String>>) intent.getSerializableExtra("hash");
        ArrayList<String> descriptions = new ArrayList<>(resume.keySet());
        for(String objct: descriptions)
        {
            questions.add(new QuestionPersonal(objct,resume.get(objct)));
        }
    }

    public void setQuestionsListAdapter()
    {
        QuestionListAdapter questions = new QuestionListAdapter(this.questions,this,this);
        questionList.setItemAnimator(new DefaultItemAnimator());
        questionList.setLayoutManager(new LinearLayoutManager(this));
        questionList.setAdapter(questions);
    }



}
