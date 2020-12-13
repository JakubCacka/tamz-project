package com.shortestwin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void startGame(View view) {
        startActivity(new Intent(this, GameActivity.class));
    }


    public void menuItemClick(View view) {
        Intent nextActivity;

        switch (view.getId()) {
            case R.id.settingsLink: {
                nextActivity = new Intent(this, SettingsActivity.class);
                break;
            }
            case R.id.scoresLink: {
                nextActivity = new Intent(this, ScoresActivity.class);
                break;
            }
            default: {
                nextActivity = new Intent(this, this.getClass());
            }
        }

        startActivity(nextActivity);
    }
}