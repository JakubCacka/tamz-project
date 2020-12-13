package com.shortestwin.game.player;

import android.content.Context;
import android.graphics.Rect;
import android.media.MediaPlayer;

import com.shortestwin.R;
import com.shortestwin.game.level.Level;
import com.shortestwin.game.player.score.LevelStats;
import com.shortestwin.game.player.score.Score;
import com.shortestwin.game.utils.Cell;

public class Player extends APlayer {

    private Context context;
    private Score score;

    public Player(String name, int color, Context context) {
        super(name, color, true);

        this.context = context;
        this.score = new Score(context);
    }


    public void update(Level level) {
        if(this.moveDir != null) {
            MediaPlayer mp = MediaPlayer.create(this.context, R.raw.move);
            mp.start();

            this.score.addMoveToLevel(level.getLevelNum());
        }

        super.update(level);
    }

    public void positionInLevel(Cell startCell, Rect startRect, int levelNum) {
        super.positionInLevel(startCell, startRect);

        this.score.storeLastLevelToDB();

        LevelStats newLevelStats = new LevelStats(levelNum);
        this.score.addNewLevelStats(newLevelStats);
    }

    public Context getContext() {
        return context;
    }
}
