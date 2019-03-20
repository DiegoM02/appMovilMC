package com.e.appmc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class PersonalSummaryActivity extends AppCompatActivity {
    private ArrayList<QuestionPersonal> questions;
    private ArrayList<QuestionRating> questionsRating;
    private TextView textPoint;
    private RecyclerView questionList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_summary);
        textPoint = findViewById(R.id.textPunto);
        questionList = (RecyclerView) findViewById(R.id.questionList);
        if(getIntent().getExtras().getInt("rating")==0)
        {
            inicializarModo0();
        }
        else
        {
            inicializarModo1();
        }




    }

    public void inicializarModo0()
    {
        textPoint.setText(getIntent().getExtras().getString("point"));
        rellenarQuestions();
        setQuestionsListAdapter();
    }

    public void inicializarModo1()
    {
        textPoint.setText("Resumen Notas");
        rellenarQuestionsRating();
        setQuestionsRatingListAdapter();

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

    public void rellenarQuestionsRating()
    {
        this.questionsRating = new ArrayList<>();
        HashMap<String,Float> resume = (HashMap<String,Float>) getIntent().getSerializableExtra("hash");
        ArrayList<String> keys = new ArrayList<>(resume.keySet());
        for(String question: keys)
        {
            float puntuacion =resume.get(question);
            QuestionRating nuevo= new QuestionRating(question,puntuacion);
            questionsRating.add(nuevo);

        }
    }

    public void setQuestionsRatingListAdapter()
    {
        QuestionRatingListAdapter questions = new QuestionRatingListAdapter(this.questionsRating,this,this);
        questionList.setItemAnimator(new DefaultItemAnimator());
        questionList.setLayoutManager(new LinearLayoutManager(this));
        questionList.setAdapter(questions);
    }




}
