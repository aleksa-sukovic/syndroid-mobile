package com.aleksa.syndroid.fragments.mouse.mouse_pad;

public interface MousePadListener
{
    void onMove(float xOffset, float yOffset);
    void onDragStart();
    void onDragEnd();
    void onLeftClick();
    void onRightClick();
    void onScroll(float yOffset);
    void onDoubleTap();
}
