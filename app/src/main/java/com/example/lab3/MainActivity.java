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
     //   LoadPreferences();
        email = findViewById(R.id.edittext);
        login = findViewById(R.id.button7);
        Context context = MainActivity.this;
        prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        Intent goToProfile = new Intent(MainActivity.this, ProfileActivity.class);
        goToProfile.putExtra("email", email.getText().toString());
        login.setOnClickListener(bt -> startActivity(goToProfile));
        goToProfile.putExtra("email", email.getText().toString());

//        LoadPreferences();
    }

    protected  void onPause(){
        super.onPause();
        String savedValue = prefs.getString("Email", "");
        email.setText(savedValue);
        login = findViewById(R.id.button7);
        login.setOnClickListener(bt -> saveSharedPreferences(email.getText().toString()));
    }
    private void saveSharedPreferences(String valueToSave) {
        em = email.getText().toString();
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