package com.e.appmc;

public class QuestionAnswered
{
    private String questionName;
    private int position;
    private int answer;

    public QuestionAnswered(String questionName, int position, int answer) {
        this.questionName = questionName;
        this.position = position;
        this.answer = answer;
    }

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }
}
