package com.e.appmc;

public class Assessment {

    private int position;
    private float assessment;
    private String question;

    public Assessment(int position, float assessment, String question) {

        this.position = position;
        this.assessment = assessment;
        this.question = question;
    }

    public int getPosition() {
        return position;
    }

    public float getAssessment() {
        return assessment;
    }

    public void setAssessment(float assessment) {
        this.assessment = assessment;
    }

    public String getQuestion() {
        return question;
    }


}
