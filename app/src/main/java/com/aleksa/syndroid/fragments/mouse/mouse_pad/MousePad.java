package com.aleksa.syndroid.fragments.mouse.mouse_pad;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MousePad extends android.support.v7.widget.AppCompatTextView implements View.OnTouchListener
{

    private MousePadListener listener;

    {
        setOnTouchListener(this);
    }

    public MousePad(Context context)
    {
        super(context);
    }

    public MousePad(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public MousePad(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        if (event.getHistorySize() < 2) {
            return true;
        }

        float x = event.getHistoricalX(0) - event.getX();
        float y = event.getHistoricalY(0) - event.getY();

        if (listener != null) {
            listener.onMove(x, y);
        }

        return true;
    }

    public void setMousePadListener(MousePadListener listener)
    {
        this.listener = listener;
    }

}
