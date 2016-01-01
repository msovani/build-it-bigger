package com.sovani.jokesandroidlibary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JokeDisplay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_display);
        if (getIntent().getExtras() != null) {
            String joke = getIntent().getExtras().getString("Joke");
            if (joke != null) {
                TextView textView = (TextView) findViewById(R.id.joketextview);
                textView.setText(joke);
            }
        }

    }
}
