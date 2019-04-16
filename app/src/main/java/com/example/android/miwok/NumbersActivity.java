package com.example.android.miwok;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.Arrays;
import java.util.List;

public class NumbersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);

        List<String> words = Arrays.asList("one", "two", "three", "four", "five", "six", "seven",
            "eight", "nine", "ten");
        int wordsLength = words.size();
        for (int i = 0; i < wordsLength; i++) {
            Log.v("NumbersActivity", "Word at index "+ i + ": " + words.get(i));
        }
    }
}
