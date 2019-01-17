package com.aleksa.syndroid.fragments.mouse.gesture_manager;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class GestureManager implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener
{
    private GestureDetectorCompat gestureDetector;
    private GestureListener listener;

    public GestureManager(Context context, GestureListener listener)
    {
        this.gestureDetector = new GestureDetectorCompat(context, this);
        this.listener = listener;
    }

    @Override
    public boolean onDown(MotionEvent e)
    {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e)
    {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e)
    {
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
    {
        if (e2.getPointerCount() == 1) {
            listener.onMove(distanceX, distanceY);
        } else if (e2.getPointerCount() == 2) {
            listener.onScroll(distanceY);
        } else {
            listener.onTwoFingerTap();
        }

        return true;
    }

    @Override
    public void onLongPress(MotionEvent e)
    {
        listener.onLongPress();
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
    {
        return false;
    }

    public void onTouchEvent(MotionEvent event)
    {
        gestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e)
    {
        listener.onTapUp();

        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e)
    {
        listener.onDoubleTap();

        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e)
    {
        return false;
    }
}
