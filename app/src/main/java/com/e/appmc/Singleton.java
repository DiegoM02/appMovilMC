package com.e.appmc;

public class Singleton {
    private static Singleton uniqInstance;
    public int id;
    private Singleton() {
    }
    public static Singleton getInstance() {
        if (uniqInstance == null) {
            {
                if (uniqInstance == null)
                    uniqInstance = new Singleton();
            }
        }
        return uniqInstance;
    }
    public void setID(int id)
    {
        this.id = id;

    }
    public int getId()
    {
        return this.id;

    }

}
