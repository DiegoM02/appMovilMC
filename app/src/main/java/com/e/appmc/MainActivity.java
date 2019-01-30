package com.e.appmc;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.e.bd.appmc.SQLiteOpenHelperDataBase;

public class MainActivity extends AppCompatActivity {

    private Button buttonEnter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.buttonEnter = (Button) findViewById(R.id.buttonEnter);

        SQLiteOpenHelperDataBase dbHelper = new SQLiteOpenHelperDataBase(this, "mcapp", null, 1);
        dbHelper.createDataUser();



            Toast.makeText(MainActivity.this, "BD", Toast.LENGTH_LONG).show();



    }




    public void ingresar(View view) {

        Intent goToMenu = new Intent(MainActivity.this, MainMenuActivity.class);
        startActivity(goToMenu);
    }
}
