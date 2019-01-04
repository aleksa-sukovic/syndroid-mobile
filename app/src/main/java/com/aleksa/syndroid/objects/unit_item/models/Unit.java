package com.aleksa.syndroid.objects.unit_item;

import androidx.annotation.DrawableRes;

public class Unit
{
    private String title;
    private @DrawableRes int drawable;

    public Unit(String title, int drawable)
    {
        this.title = title;
        this.drawable = drawable;
    }

    public String getTitle()
    {
        return title;
    }

    public int getDrawable()
    {
        return drawable;
    }
}
