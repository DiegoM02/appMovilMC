package com.e.bd.appmc;

import android.content.ContentValues;

import java.util.Date;

public class User {

    private int id;
    private String name;
    private String userName;
    private String password;
    private String crated;
    private String rut;
    private String email;
    private String phone;
    private int role;


    public User(int id, String name, String userName, String password, String crated, String rut, String email, String phone, int role) {
        this.id = id;
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.crated = crated;
        this.rut = rut;
        this.email = email;
        this.phone = phone;
        this.role = role;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getCrated() {
        return crated;
    }

    public String getRut() {
        return rut;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public int getRole()
    {
        return role;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(UserContract.UserEntry.ID, id);
        values.put(UserContract.UserEntry.NAME, name);
        values.put(UserContract.UserEntry.USERNAME, userName);
        values.put(UserContract.UserEntry.PASSWORD, password);
        values.put(UserContract.UserEntry.CREATED, crated);
        values.put(UserContract.UserEntry.RUT, rut);
        values.put(UserContract.UserEntry.EMAIL, email);
        values.put(UserContract.UserEntry.PHONE, phone);
        values.put(UserContract.UserEntry.ROLE,role);

        return values;
    }
}
