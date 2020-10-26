package com.shortestwin.game.core;

import android.view.MotionEvent;

import com.shortestwin.game.GameView;

public class InputHandler {
    private GameView context;

    public InputHandler(GameView context) {
        this.context = context;
    }

    public boolean handlePlayerTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                if(event.getY() < (int)(context.screenHeight / 2)) {
                    context.rectUp = true;
                    context.rectDown = false;
                } else {
                    context.rectDown = true;
                    context.rectUp = false;
                }

                return true;
            }
            case MotionEvent.ACTION_UP: {
                context.rectDown = false;
                context.rectUp = false;

                break;
            }
        }

        return false;
    }
}
