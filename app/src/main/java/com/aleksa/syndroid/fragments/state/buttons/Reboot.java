package com.aleksa.syndroid.fragments.state.buttons;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Reboot extends StateButton
{
    public Reboot(@NonNull Context context)
    {
        super(context);
    }

    public Reboot(@NonNull Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
    }

    public Reboot(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    public Reboot(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected String getStateTag()
    {
        return "reboot";
    }
}
