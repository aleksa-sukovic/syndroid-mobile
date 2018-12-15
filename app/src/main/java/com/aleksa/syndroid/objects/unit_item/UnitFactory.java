package com.aleksa.syndroid.objects.unit_item;

import com.aleksa.syndroid.R;

import java.util.ArrayList;
import java.util.List;

public class UnitFactory
{
    public List<Unit> getUnitList()
    {
        List<Unit> units =  new ArrayList<>();

        units.add(new Unit("Mouse", R.drawable.ic_mouse));
        units.add(new Unit("Media", R.drawable.ic_volume));
        units.add(new Unit("State", R.drawable.ic_power_settings));
        units.add(new Unit("Keyboard", R.drawable.ic_keyboard_navigation));

        return units;
    }
}
