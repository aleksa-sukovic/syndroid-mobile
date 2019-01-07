package com.aleksa.syndroid.fragments.mouse.mouse_pad;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.aleksa.syndroid.fragments.mouse.gesture_manager.GestureListener;
import com.aleksa.syndroid.fragments.mouse.gesture_manager.GestureManager;

public class MousePad extends android.support.v7.widget.AppCompatTextView implements GestureListener, View.OnTouchListener
{
    private GestureManager       gestureManager;
    private MousePadListener     listener;
    private boolean              clickNdrag;
    private int                  naturalScrolling;

    public MousePad(Context context)
    {
        super(context);
        gestureManager = new GestureManager(context, this);
        setOnTouchListener(this);
        setNaturalScrolling(true);
    }

    public MousePad(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        gestureManager = new GestureManager(context, this);
        setOnTouchListener(this);
        setNaturalScrolling(true);
    }

    public MousePad(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        gestureManager = new GestureManager(context, this);
        setOnTouchListener(this);
        setNaturalScrolling(true);
    }

    public void setMousePadListener(MousePadListener listener)
    {
        this.listener = listener;
    }

    @Override
    public void onMove(float xOffset, float yOffset)
    {
        listener.onMove(-xOffset, -yOffset);
    }

    @Override
    public void onScroll(float yOffset)
    {
        listener.onScroll(naturalScrolling * yOffset);
    }

    @Override
    public void onLongPress()
    {
        clickNdrag = !clickNdrag;

        if (clickNdrag) {
            listener.onDragStart();
        } else {
            listener.onDragEnd();
        }
    }

    @Override
    public void onTapUp()
    {
        if (clickNdrag) {
            listener.onDragEnd();
            clickNdrag = false;
        } else {
            listener.onLeftClick();
        }
    }

    @Override
    public void onDoubleTap()
    {
        listener.onDoubleTap();
    }

    @Override
    public void onTwoFingerTap()
    {
        listener.onRightClick();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        gestureManager.onTouchEvent(event);

        return true;
    }

    public void setNaturalScrolling(boolean enabled)
    {
        this.naturalScrolling = enabled ? -1 : 1;
    }

    public boolean usesNaturalScrolling()
    {
        return naturalScrolling == -1;
    }
}
