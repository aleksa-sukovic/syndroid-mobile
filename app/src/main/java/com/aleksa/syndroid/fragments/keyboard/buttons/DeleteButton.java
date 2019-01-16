package com.aleksa.syndroid.fragments.keyboard.buttons;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DeleteButton extends KeyboardButton
{
    public DeleteButton(@NonNull Context context)
    {
        super(context);
    }

    public DeleteButton(@NonNull Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
    }

    public DeleteButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    public DeleteButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected String getKeyCode()
    {
        return "delete";
    }
}
