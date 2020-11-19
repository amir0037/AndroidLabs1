package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class ProfileActivity extends AppCompatActivity {
    ImageButton mImageButton;
    Button chatButton;
    EditText et1;
    Button weatherButton;

    public static final String ACTIVITY_NAME = "PROFILE_ACTIVITY";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mImageButton = findViewById(R.id.imageButtonInt);
        mImageButton.setOnClickListener(bt -> dispatchTakePictureIntent());
        et1 = findViewById(R.id.et1);
        Intent fromMain = getIntent();
        et1.setText(fromMain.getStringExtra("Email"));

        Log.e(ACTIVITY_NAME, "onCreate(Bundle savedInstanceState);");

        chatButton = findViewById(R.id.goToChat);
        Intent goToProfile = new Intent(ProfileActivity.this, ChatRoomActivity.class);
        chatButton.setOnClickListener(bt -> startActivity(goToProfile));

        weatherButton = findViewById(R.id.weather);
        Intent goToWeatherForecast = new Intent(ProfileActivity.this, WeatherForecast.class);
        weatherButton.setOnClickListener(bt -> startActivity(goToWeatherForecast));

    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageButton.setImageBitmap(imageBitmap);
        }
        Log.e(ACTIVITY_NAME, "onActivityResult()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(ACTIVITY_NAME, "onStart();");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(ACTIVITY_NAME, "onStop();");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(ACTIVITY_NAME, "onDestroy();");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(ACTIVITY_NAME, "onPause();");

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Log.e(ACTIVITY_NAME, "onResume();");

    }
}