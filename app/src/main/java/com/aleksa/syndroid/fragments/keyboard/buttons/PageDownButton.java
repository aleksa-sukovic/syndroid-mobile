package com.aleksa.syndroid.fragments.keyboard.buttons;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class PageDownButton extends KeyboardButton
{
    public PageDownButton(@NonNull Context context)
    {
        super(context);
    }

    public PageDownButton(@NonNull Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
    }

    public PageDownButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    public PageDownButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected String getKeyCode()
    {
        return "pagedown";
    }
}
