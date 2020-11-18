package com.shortestwin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.shortestwin.game.GameView;

public class GameActivity extends AppCompatActivity {

    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gameView = findViewById(R.id.gameView);

    }

    @Override
    protected void onPause() {
        super.onPause();
        this.gameView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.gameView.resume();
    }
}