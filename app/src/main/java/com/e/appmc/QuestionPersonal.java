package com.e.appmc;

import java.util.ArrayList;

public class QuestionPersonal
{
    private String questionName;
    private ArrayList<String> personal;


    public QuestionPersonal(String questionName,ArrayList<String> personal) {
        this.questionName = questionName;
        this.personal = personal;
    }

    public int size() {
        return personal.size();
    }

    public boolean isEmpty() {
        return personal.isEmpty();
    }

    public String get(int index) {
        return personal.get(index);
    }

    public boolean add(String s) {
        return personal.add(s);
    }

    public void clear() {
        personal.clear();
    }

    public String getQuestionName() {
        return questionName;
    }

    public ArrayList<String> getPersonal() {
        return personal;
    }
}
