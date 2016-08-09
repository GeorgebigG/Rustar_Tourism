package com.rustaronline.mobile.rustartourism.Helper;

import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import static android.view.MotionEvent.*;

/**
 * Created by gio on 6/26/16.
 */
public class AnimationClass {
    public AnimationClass(Button view, final int defaultColor, final int clickColor) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        view.setBackgroundColor(clickColor);
                        break;

                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        view.setBackgroundColor(defaultColor);

                }

                return false;
            }
        });
    }

    public static void setAnimation(Button view, int defaultColor, int clickColor) {
        new AnimationClass(view, defaultColor, clickColor);
    }
}
