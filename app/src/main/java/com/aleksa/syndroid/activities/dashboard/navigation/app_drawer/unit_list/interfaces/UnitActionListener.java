package com.aleksa.syndroid.activities.dashboard.navigation.app_drawer.unit_list.interfaces;

import com.aleksa.syndroid.objects.unit_item.models.Unit;

public interface UnitActionListener
{
    void onOrderChange(Unit first, Unit second);

    void onUnitSelect(Unit unit);
}
