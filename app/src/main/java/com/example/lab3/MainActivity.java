package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    SharedPreferences prefs;
    EditText email;
    Button login;
    String em;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email = findViewById(R.id.edittext);
        login = findViewById(R.id.button7);
        Context context = MainActivity.this;
        prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        LoadPreferences();
        Intent goToProfile = new Intent(MainActivity.this, ProfileActivity.class);
        login.setOnClickListener(bt -> {goToProfile.putExtra("Email", email.getText().toString());
        startActivity(goToProfile);}
        );

    }

    protected  void onPause(){
        super.onPause();
         saveSharedPreferences(email.getText().toString());
    }
    private void saveSharedPreferences(String valueToSave) {

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("Email", valueToSave);
        editor.commit();
    }
    private void LoadPreferences(){
        email.setText(prefs.getString("Email", ""));

    }
}