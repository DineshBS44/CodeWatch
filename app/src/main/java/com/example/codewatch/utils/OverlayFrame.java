package com.example.codewatch.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;

import com.example.codewatch.R;

public class OverlayFrame extends CoordinatorLayout {
    private boolean overlay = false;
    private ProgressBar progressBar;
    private ColorDrawable overlayDrawable, plainDrawable;

    public OverlayFrame(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public OverlayFrame(@NonNull Context context) {
        super(context);
    }

    public OverlayFrame(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void displayOverlay(boolean isVisible) {
        overlay = isVisible;

        progressBar = ((Activity) getContext()).findViewById(R.id.progress_bar_overlay);
        overlayDrawable = new ColorDrawable(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
        plainDrawable = new ColorDrawable(ContextCompat.getColor(getContext(), android.R.color.transparent));

        if (isVisible) {
            setForeground(overlayDrawable);
            progressBar.setVisibility(VISIBLE);
        } else {
            setForeground(plainDrawable);
            progressBar.setVisibility(INVISIBLE);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return overlay;
    }
}