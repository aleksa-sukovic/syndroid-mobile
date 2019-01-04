package com.aleksa.syndroid.activities.dashboard;

import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.AttrRes;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;

import com.aleksa.syndroid.R;
import com.aleksa.syndroid.activities.dashboard.navigation.listeners.UnitSelectListener;
import com.aleksa.syndroid.objects.unit_item.models.Unit;
import com.aleksa.syndroid.objects.unit_item.repositories.UnitRepository;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

import java.util.List;

import androidx.annotation.ColorInt;

class BottomNavigation implements AHBottomNavigation.OnTabSelectedListener
{
    private UnitRepository unitRepository;
    private List<Unit>     unitList;
    private AHBottomNavigation bottomNavigation;

    private AppCompatActivity  activity;
    private UnitSelectListener listener;

    public BottomNavigation(AppCompatActivity activity)
    {
        this.activity = activity;
        this.listener = (UnitSelectListener) activity;

        bottomNavigation = activity.findViewById(R.id.bottom_navigation);
        unitRepository = new UnitRepository(activity.getApplication());

        fetchNavigationItems();
        configureNavigationView();
    }

    private void fetchNavigationItems()
    {
        unitRepository.getNavigationMenuUnits().then(data -> {
            unitList = (List<Unit>) data;

            new Handler(Looper.getMainLooper()).post(() -> {
                bottomNavigation.removeAllItems();

                for (Unit unit : unitList) {
                    bottomNavigation.addItem(new AHBottomNavigationItem(unit.getTitle(), unit.getDrawable()));
                }

                bottomNavigation.refresh();
                listener.onUnitSelect(unitList.get(0));
            });
        });
    }

    private void configureNavigationView()
    {
        bottomNavigation.setDefaultBackgroundColor(getAttributeColor(R.attr.primary));
        bottomNavigation.setAccentColor(getAttributeColor(R.attr.primary_dark));
        bottomNavigation.setInactiveColor(getAttributeColor(R.attr.icons));
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.SHOW_WHEN_ACTIVE);
        bottomNavigation.setForceTint(false);
        bottomNavigation.setOnTabSelectedListener(this);
    }

    private @ColorInt int getAttributeColor(@AttrRes int attributeId)
    {
        TypedValue typedValue = new TypedValue();

        Resources.Theme theme = activity.getTheme();
        theme.resolveAttribute(attributeId, typedValue, true);

        return typedValue.data;
    }

    @Override
    public boolean onTabSelected(int position, boolean wasSelected)
    {
        listener.onUnitSelect(unitList.get(position));

        return true;
    }

    public void refreshItems()
    {
        fetchNavigationItems();

        bottomNavigation.setSelected(false);
    }
}
