package com.katysh.groceryguru.util;

import android.view.MotionEvent;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;

public abstract class GgActivity extends AppCompatActivity {
    private SwipeManager swipeManager;
    private NoArgKnob onLeftSwipe;
    private NoArgKnob onRightSwipe;
    private NoArgKnob onStart;
    private boolean immersiveStickyMode = false;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if ((onLeftSwipe != null || onRightSwipe != null) && swipeManager == null) {
            createSwipeManager();
        }

        if (swipeManager != null) {
            Boolean result = swipeManager.dispatchTouchEvent(ev);
            return result == null ? super.dispatchTouchEvent(ev) : result;
        }
        return super.dispatchTouchEvent(ev);
    }

    private void createSwipeManager() {
        swipeManager = new SwipeManager(this);
        if (onLeftSwipe != null)
            swipeManager.setLeftSwipeListener(onLeftSwipe);
        if (onRightSwipe != null)
            swipeManager.setRightSwipeListener(onRightSwipe);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (onStart != null) {
            onStart.execute();
        }
    }

    @Override
    protected void onResume() {
        setImstModeIfNeeded();
        super.onResume();
    }

    public void setImstModeIfNeeded() {
        if (immersiveStickyMode) {
            setImmersiveStickyMode(getWindow());
        }
    }

    public void setOnLeftSwipe(NoArgKnob onLeftSwipe) {
        this.onLeftSwipe = onLeftSwipe;
    }

    public void setOnRightSwipe(NoArgKnob onRightSwipe) {
        this.onRightSwipe = onRightSwipe;
    }

    public void setOnStart(NoArgKnob onStart) {
        this.onStart = onStart;
    }

    public void setImmersiveStickyMode(boolean immersiveStickyMode) {
        this.immersiveStickyMode = immersiveStickyMode;
    }

    private void setImmersiveStickyMode(Window window) {
        View decorView = window.getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);
    }
}
