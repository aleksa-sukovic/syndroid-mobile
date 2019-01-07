package com.aleksa.syndroid.fragments.mouse.gesture_manager;

public interface GestureListener
{
    void onMove(float xOffset, float yOffset);
    void onScroll(float yOffset);
    void onLongPress();
    void onTapUp();
    void onDoubleTap();
    void onTwoFingerTap();
}
