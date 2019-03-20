package com.e.appmc;

public class VisitModel {

    private String date;
    private String enter;
    private String exit;

    public VisitModel(String date,String enter,String exit) {
        this.date = date;
        this.enter = enter;
        this.exit = exit;
    }


    public String getDate() {
        return date;
    }

    public String getEnter() {
        return enter;
    }

    public String getExit() {
        return exit;
    }
}
