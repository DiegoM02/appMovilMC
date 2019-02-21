package com.e.appmc;

public class QuestionRating
{
    private String name;
    private float point;

    public QuestionRating(String name, float point) {
        this.name = name;
        this.point = point;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPoint() {
        return point;
    }

    public void setPoint(float point) {
        this.point = point;
    }
}
