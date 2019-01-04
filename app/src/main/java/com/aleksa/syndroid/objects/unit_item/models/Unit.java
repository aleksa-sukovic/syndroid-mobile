package com.aleksa.syndroid.objects.unit_item.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import androidx.annotation.DrawableRes;

@Entity(tableName = "units")
public class Unit
{
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String tag;
    private String title;
    private int position;
    private String description;
    private @DrawableRes int drawable;

    public Unit(@NonNull String tag, String title, int position, String description, int drawable)
    {
        this.tag = tag;
        this.title = title;
        this.description = description;
        this.drawable = drawable;

        if (position < 0) {
            position = 999999;
        }
        this.position = position;
    }

    public String getTag()
    {
        return tag;
    }

    public void setTag(String tag)
    {
        this.tag = tag;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public int getPosition()
    {
        return position;
    }

    public void setPosition(int position)
    {
        this.position = position;
    }

    public int getDrawable()
    {
        return drawable;
    }

    public void setDrawable(int drawable)
    {
        this.drawable = drawable;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    @Override
    public boolean equals(@Nullable Object obj)
    {
        if (!(obj instanceof Unit)) {
            return false;
        }

        return ((Unit) obj).tag.equals(this.tag);
    }
}
