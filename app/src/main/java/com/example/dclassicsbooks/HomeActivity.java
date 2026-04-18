package com.example.dclassicsbooks;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        TextView greeting = findViewById(R.id.tvGreeting);

        String username = getIntent().getStringExtra("username");
        username = (username == null) ? "Tester" : username;

        greeting.setText(getString(R.string.greet, username.toUpperCase()));
    }
}