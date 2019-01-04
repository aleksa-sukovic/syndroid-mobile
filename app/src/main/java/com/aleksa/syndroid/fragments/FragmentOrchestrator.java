package com.aleksa.syndroid.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.aleksa.syndroid.objects.unit_item.models.Unit;

public class FragmentOrchestrator
{
    private FragmentManager fragmentManager;
    private int containerId;

    public FragmentOrchestrator(AppCompatActivity activity, int containerId)
    {
        this.fragmentManager = activity.getSupportFragmentManager();
        this.containerId = containerId;
    }

    public void navigateToFragment(Unit unit)
    {
        navigateToFragment(FragmentFactory.initializeFromTag(unit.getTag()), unit.getTag());
    }

    public void navigateToFragment(Fragment fragment, String tag)
    {
        fragmentManager.beginTransaction()
            .replace(containerId, fragment)
            .commit();
    }
}
