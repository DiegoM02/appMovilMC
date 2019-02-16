package com.e.appmc;

import java.util.ArrayList;

public class QuestionPersonal
{
    private String questionName;
    private ArrayList<String> personal;

    public QuestionPersonal(String questionName) {
        this.questionName = questionName;
        this.personal = new ArrayList<>();
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
}
