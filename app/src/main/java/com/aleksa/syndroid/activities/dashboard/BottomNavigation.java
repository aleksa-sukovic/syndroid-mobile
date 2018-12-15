package com.aleksa.syndroid.activities.dashboard;

import android.content.res.Resources;
import android.support.annotation.AttrRes;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;

import com.aleksa.syndroid.R;
import com.aleksa.syndroid.objects.unit_item.Unit;
import com.aleksa.syndroid.objects.unit_item.UnitFactory;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.ColorInt;

class BottomNavigation implements AHBottomNavigation.OnTabSelectedListener
{
    private UnitFactory unitFactory;
    private List<Unit> unitList;

    private List<AHBottomNavigationItem> navigationItems;
    private AHBottomNavigation bottomNavigation;

    private AppCompatActivity activity;
    private BottomNavigationListener listener;

    public BottomNavigation(AppCompatActivity activity)
    {
        this.activity = activity;
        this.listener = (BottomNavigationListener) activity;

        bottomNavigation = activity.findViewById(R.id.bottom_navigation);
        unitFactory = new UnitFactory();
        unitList = unitFactory.getUnitList();
        navigationItems = getNavigationItems();

        addNavigationItems();
        configureNavigationView();
    }

    private List<AHBottomNavigationItem> getNavigationItems()
    {
        List<AHBottomNavigationItem> list = new ArrayList<>();

        for (Unit unit : unitList) {
            list.add(new AHBottomNavigationItem(unit.getTitle(), unit.getDrawable()));
        }

        return list;
    }

    private void addNavigationItems()
    {
        for (AHBottomNavigationItem item : navigationItems) {
            bottomNavigation.addItem(item);
        }
    }

    private void configureNavigationView()
    {
        bottomNavigation.setDefaultBackgroundColor(getAttributeColor(R.attr.primary));
        bottomNavigation.setAccentColor(getAttributeColor(R.attr.primary_dark));
        bottomNavigation.setInactiveColor(getAttributeColor(R.attr.icons));
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.SHOW_WHEN_ACTIVE);
        bottomNavigation.setForceTint(true);
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

    public interface BottomNavigationListener {
        void onUnitSelect(Unit unit);
    }
}
