package com.aleksa.syndroid.fragments.mouse.mouse_pad;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.aleksa.syndroid.fragments.mouse.gesture_manager.GestureListener;
import com.aleksa.syndroid.fragments.mouse.gesture_manager.GestureManager;

public class MousePad extends android.support.v7.widget.AppCompatTextView implements GestureListener, View.OnTouchListener
{
    private GestureManager gestureManager;
    private MousePadListener listener;
    private boolean clickNdrag;

    public MousePad(Context context)
    {
        super(context);
        gestureManager = new GestureManager(context, this);
        setOnTouchListener(this);
    }

    public MousePad(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        gestureManager = new GestureManager(context, this);
        setOnTouchListener(this);
    }

    public MousePad(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        gestureManager = new GestureManager(context, this);
        setOnTouchListener(this);
    }

    public void setMousePadListener(MousePadListener listener)
    {
        this.listener = listener;
    }

    @Override
    public void onMove(float xOffset, float yOffset)
    {
        listener.onMove(xOffset, yOffset);
    }

    @Override
    public void onScroll(float yOffset)
    {

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
    public boolean onTouch(View v, MotionEvent event)
    {
        gestureManager.onTouchEvent(event);
        return true;
    }
}
