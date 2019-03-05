package com.e.appmc;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.e.appmc.sync.SyncDatabase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static java.lang.System.out;


public class MainMenuActivity extends AppCompatActivity {
    private static final String SESSION_ESTADO_RECORDAR = "estado_recordado";
    private TextView nombreUsuario;
    private Bundle datosUsuario;
    private ImageView imageProfile;
    private static final String  GUARDAR_IMAGE_PROFILE = "guardar_image_profile";
    private static final String RUTA_IMAGE = "ruta_image";
    private SyncDatabase sincroniza;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        nombreUsuario= (TextView)findViewById(R.id.userText);
        datosUsuario = getIntent().getExtras();
        nombreUsuario.setText(datosUsuario.getString("name"));
        this.sincroniza = new SyncDatabase(this);
        this.sincroniza.syncAspectWebsite();
        sincroniza.syncFacilityWebsite();
        imageProfile = (ImageView) findViewById(R.id.imageProfile);




    }


    public void evaluar(View view)
    {
        Intent goToEvaluation = new Intent(MainMenuActivity.this,EvaluationActivity.class);
        goToEvaluation.putExtra("id",datosUsuario.getInt("id"));
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
