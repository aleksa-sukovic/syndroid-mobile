package com.aleksa.syndroid.activities.dashboard.navigation.listeners;

import com.aleksa.syndroid.objects.unit_item.models.Unit;

public interface UnitSelectListener
{
    void onUnitSelect(Unit unit);

    void onUnitOrderChange(Unit one, Unit two);
}
