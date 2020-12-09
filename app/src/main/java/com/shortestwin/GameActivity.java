package com.shortestwin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.shortestwin.game.GameView;
import com.shortestwin.game.utils.Direction;

public class GameActivity extends AppCompatActivity {

    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gameView = findViewById(R.id.gameView);

    }

    public void movePlayer(View view) {
        Direction direction;

        switch (view.getId()) {
            case R.id.dirN: {
                direction = Direction.N;
                break;
            }
            case R.id.dirNE: {
                direction = Direction.NE;
                break;
            }
            case R.id.dirE: {
                direction = Direction.E;
                break;
            }
            case R.id.dirSE: {
                direction = Direction.SE;
                break;
            }
            case R.id.dirS: {
                direction = Direction.S;
                break;
            }
            case R.id.dirSW: {
                direction = Direction.SW;
                break;
            }
            case R.id.dirW: {
                direction = Direction.W;
                break;
            }
            case R.id.dirNW: {
                direction = Direction.NW;
                break;
            }
            default: return;
        }

        this.gameView.handlePlayerMove(direction);
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

    @Override
    public void onBackPressed() {
        this.gameView.pause();
        Intent setIntent = new Intent(this, MenuActivity.class);
        startActivity(setIntent);
    }
}