package com.shortestwin.game.core;

import android.view.MotionEvent;

import com.shortestwin.game.GameView;

public class InputHandler {
    private GameView context;

    public boolean isTouchDown, isTouchUp;

    public InputHandler(GameView context) {
        this.context = context;
        this.isTouchDown = false;
        this.isTouchUp = false;
    }

    public boolean handlePlayerTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                if(event.getY() < (int)(context.screenHeight / 2)) {
                    this.isTouchUp = true;
                    this.isTouchDown = false;
                } else {
                    this.isTouchUp = false;
                    this.isTouchDown = true;
                }

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
}
