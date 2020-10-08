package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    SharedPreferences prefs;
    EditText email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email = findViewById(R.id.edittext);
        prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE);
//        String savedValue = prefs.getString("Email", "");
//        email.setText(savedValue);
//        Button login = findViewById(R.id.button7);
//        login.setOnClickListener(bt -> saveSharedPreferences(email.getText().toString()));
//        LoadPreferences();
    }

    protected  void onPause(){
        super.onPause();
        String savedValue = prefs.getString("Email", "");
        email.setText(savedValue);
        Button login = findViewById(R.id.button7);
        login.setOnClickListener(bt -> saveSharedPreferences(email.getText().toString()));
    }
    private void saveSharedPreferences(String valueToSave) {
        String em = email.getText().toString();
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("Email", em);
        editor.commit();

    }

    private void LoadPreferences(){
        if (prefs.contains("Email")) {
            email.setText(prefs.getString("Email", ""));
        }
       // String savedEmail = prefs.getString("Email", "");
    }
}