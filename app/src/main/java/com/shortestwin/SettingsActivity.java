package com.shortestwin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.shortestwin.game.GameView;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public void resetPreferences(View button) {
        SharedPreferences prefs = this.getSharedPreferences(GameView.MY_PREFS_NAME, MODE_PRIVATE);
        prefs.edit().clear().apply();
        
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}