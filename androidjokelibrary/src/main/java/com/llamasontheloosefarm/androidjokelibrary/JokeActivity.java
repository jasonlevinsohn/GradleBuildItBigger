package com.llamasontheloosefarm.androidjokelibrary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JokeActivity extends AppCompatActivity {

    private TextView tvJokeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);

        tvJokeText = findViewById(R.id.tv_joke_text);

        Intent fromIntent = getIntent();
        String joke;

        // Set the joke text retrieved from the main activity
        if (fromIntent.hasExtra("jokeString")) {
            joke = fromIntent.getStringExtra("jokeString");
            tvJokeText.setText(joke);


        }
    }
}
