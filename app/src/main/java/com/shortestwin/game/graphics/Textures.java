package com.shortestwin.game.graphics;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;

import com.shortestwin.game.GameView;

import static android.content.Context.MODE_PRIVATE;

public class Textures {
    SharedPreferences prefs;

    public Textures(Context context) {
        this.prefs = context.getSharedPreferences(GameView.MY_PREFS_NAME, MODE_PRIVATE);
    }

    public int getPlayerColor() {
        return this.prefs.getInt("color_player", Color.CYAN);
    }

    public int getBotColor() {
        return this.prefs.getInt("color_bot", Color.YELLOW);
    }

    public int getBlockColor() {
        return this.prefs.getInt("color_block", Color.GRAY);
    }

    public int getBackgroundColor() {
        return this.prefs.getInt("color_background", Color.BLUE);
    }
}
