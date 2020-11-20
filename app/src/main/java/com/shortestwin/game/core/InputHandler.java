package com.shortestwin.game.core;

import android.view.MotionEvent;
import com.shortestwin.game.GameView;
import com.shortestwin.game.utils.Direction;

public class InputHandler {
    private GameView game;

    public boolean isTouchDown, isTouchUp;

    public InputHandler(GameView context) {
        this.game = context;
        this.isTouchDown = false;
        this.isTouchUp = false;
    }

    public boolean handlePlayerTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                if(event.getY() < (int)(game.screenHeight / 2)) {
                    this.isTouchUp = true;
                    this.isTouchDown = false;
                } else {
                    this.isTouchUp = false;
                    this.isTouchDown = true;
                }

                this.game.level.setRegenerate(true);

                return true;
            }
            case MotionEvent.ACTION_UP: {
                this.isTouchUp = false;
                this.isTouchDown = false;

                break;
            }
        }

        return false;
    }

    public void handlePlayerMove(Direction direction) {
        this.game.setPlayerMoveDir(direction);
    }
}
