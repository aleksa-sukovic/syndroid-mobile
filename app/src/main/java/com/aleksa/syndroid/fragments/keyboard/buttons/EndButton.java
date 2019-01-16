package com.aleksa.syndroid.fragments.keyboard.buttons;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class EndButton extends KeyboardButton
{
    public EndButton(@NonNull Context context)
    {
        super(context);
    }

    public EndButton(@NonNull Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
    }

    public EndButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    public EndButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected String getKeyCode()
    {
        return "end";
    }
}
