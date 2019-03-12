package com.e.appmc;


import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.e.appmc.sync.SyncDatabase;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static java.lang.System.out;


public class MainMenuActivity extends AppCompatActivity {
    private static final String SESSION_ESTADO_RECORDAR = "estado_recordado";
    private TextView nombreUsuario;
    private Bundle datosUsuario;
    private ImageView imageProfile;
    private static final String GUARDAR_IMAGE_PROFILE = "guardar_image_profile";
    private static final String RUTA_IMAGE = "ruta_image";
    private SyncDatabase sincroniza;
    private PendingIntent mGeofencePendingIntent;
    private ArrayList<Geofence> mGeofenceList;
    private GeofencingClient mGeofencingClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        nombreUsuario = (TextView) findViewById(R.id.userText);
        datosUsuario = getIntent().getExtras();
        nombreUsuario.setText(datosUsuario.getString("name"));
        imageProfile = (ImageView) findViewById(R.id.imageProfile);

        mGeofenceList = new ArrayList<>();
        Geofence geofence = new Geofence.Builder().setRequestId("Mi casa").setCircularRegion(-35.07468, -71.25500, 1000).setExpirationDuration(12*60*60*1000).setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
                Geofence.GEOFENCE_TRANSITION_EXIT).build();
        mGeofenceList.add(geofence);
        mGeofencingClient = new GeofencingClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        {
            mGeofencingClient.addGeofences(getGeofencingRequest(), getGeofencePendingIntent())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(MainMenuActivity.this, "Geofence listo, ready pa todas las nenas", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainMenuActivity.this, "Error" + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

        }




    }





    @NonNull
    private GeofencingRequest getGeofencingRequest() {
        return new GeofencingRequest.Builder()
                .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
                .addGeofences(mGeofenceList)
                .build();
    }

    private PendingIntent getGeofencePendingIntent()
    {
        Intent intent = new Intent(this, GeofenceBroadcastReceiver.class);
        mGeofencePendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.
                FLAG_UPDATE_CURRENT);
        return mGeofencePendingIntent;
    }

    public void visitar(View view )
    {
        Intent goToVisit= new Intent(MainMenuActivity.this,VisitActivity.class);
        startActivity(goToVisit);
    }
    public void evaluar(View view)
    {
        Intent goToEvaluation = new Intent(MainMenuActivity.this,EvaluationActivity.class);
        goToEvaluation.putExtra("id",datosUsuario.getInt("id"));
        goToEvaluation.putExtra("name",datosUsuario.getString("name"));
        startActivity(goToEvaluation);
    }


    public void salir(View view)
    {
       MainActivity.changeEstadoSession(MainMenuActivity.this,false);
        Intent goToLogin = new Intent(MainMenuActivity.this,MainActivity.class);
        startActivity(goToLogin);
        finish();
    }

    public void subirImagen(View view) {

        Intent subirImagen = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        subirImagen.setType("image/");
        startActivityForResult(subirImagen.createChooser(subirImagen,"Elige tu aplicación preferida"),10);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK)
        {
            Uri path = data.getData();
            transformarImagePerfil(path);
        }
    }

    public void transformarImagePerfil(Uri path)
    {
        Bitmap result = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        InputStream is = null;
        try {
            is = this.getContentResolver().openInputStream(path);
            result = BitmapFactory.decodeStream(is, null, options);
            RoundedBitmapDrawable roundedDrawable = RoundedBitmapDrawableFactory.create(getResources(), result);
            roundedDrawable.setCircular(true);

            Bitmap image = roundedDrawable.getBitmap();

            saveImage(this,image,"profile","jpg");

            this.imageProfile.setImageDrawable(roundedDrawable);
            is.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveImage(Context context, Bitmap bitmap, String name, String extension){
        name = name + "." + extension;
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = context.openFileOutput(name, Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Bitmap loadImageBitmap(Context context,String name,String extension){
        name = name + "." + extension;
        FileInputStream fileInputStream;
        Bitmap bitmap = null;
        try{
            fileInputStream = context.openFileInput(name);
            bitmap = BitmapFactory.decodeStream(fileInputStream);
            fileInputStream.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

}
