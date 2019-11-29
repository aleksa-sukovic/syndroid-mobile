package com.aleksa.syndroid.activities.dashboard.navigation.listeners;

import com.aleksa.syndroid.objects.unit_item.models.Unit;

public interface UnitSelectListener
{
    void handleUnitSelect(Unit unit);
    void handleUnitOrderChange(Unit one, Unit two);
}
