package com.e.appmc;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.Toast;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

public class VisitActivity extends AppCompatActivity {


    private ViewPager simpleViewPager;
    private TabLayout tabLayout;
    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit);
        simpleViewPager = (ViewPager) findViewById(R.id.simpleViewPager);
        tabLayout = (TabLayout) findViewById(R.id.simpleTabLayout);
        TabLayout.Tab firstTab = tabLayout.newTab();
        firstTab.setText("Resumen");
        tabLayout.addTab(firstTab);
        TabLayout.Tab secondTab = tabLayout.newTab();
        secondTab.setText("Mis visitas");
        tabLayout.addTab(secondTab);
        int idUser = getIntent().getExtras().getInt("idUser");
        PagerAdapter adapter = new VisitPagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount(),idUser );
        simpleViewPager.setAdapter(adapter);
        simpleViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

    }




}
