package com.aleksa.syndroid.activities.dashboard.navigation.toolbar;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;

import com.aleksa.syndroid.R;
import com.balysv.materialmenu.MaterialMenuDrawable;

public class Toolbar
{
    private MaterialMenuDrawable materialMenu;

    public Toolbar(AppCompatActivity activity)
    {
        android.support.v7.widget.Toolbar toolbar = activity.findViewById(R.id.toolbar);
        toolbar.setTitle("");
        activity.setSupportActionBar(toolbar);

        materialMenu = new MaterialMenuDrawable(activity, Color.WHITE, MaterialMenuDrawable.Stroke.THIN);
        toolbar.setNavigationIcon(materialMenu);
    }

    public void setTransformationOffset(boolean isDrawerOpened, float slideOffset)
    {
        materialMenu.setTransformationOffset(
            MaterialMenuDrawable.AnimationState.BURGER_ARROW,
            isDrawerOpened ? 2 - slideOffset : slideOffset
        );
    }

    public void setArrowIcon()
    {
        materialMenu.setIconState(MaterialMenuDrawable.IconState.ARROW);
    }

    public void setBurgerIcon()
    {
        materialMenu.setIconState(MaterialMenuDrawable.IconState.BURGER);
    }
}
