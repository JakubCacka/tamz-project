package com.shortestwin.game.player;

import android.content.Context;
import android.media.MediaPlayer;

import com.shortestwin.R;
import com.shortestwin.game.level.Level;

public class Player extends APlayer {

    private Context context;

    public Player(String name, int color, Context context) {
        super(name, color, true);

        this.context = context;
    }


    public void update(Level level) {
        if(this.moveDir != null) {
            MediaPlayer mp = MediaPlayer.create(this.context, R.raw.move);
            mp.start();
        }

        super.update(level);
    }
}
