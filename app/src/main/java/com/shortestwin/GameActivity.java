package com.shortestwin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.shortestwin.game.GameView;

public class GameActivity extends AppCompatActivity {

    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayMetrics display = Resources.getSystem().getDisplayMetrics();

        this.gameView = new GameView(this, display.widthPixels, display.heightPixels);

        setContentView(gameView);
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