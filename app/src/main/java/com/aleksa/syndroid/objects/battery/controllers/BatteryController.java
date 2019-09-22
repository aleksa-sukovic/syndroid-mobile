package com.aleksa.syndroid.objects.battery.controllers;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

import com.aleksa.syndroid.library.controllers.BaseController;
import com.aleksa.syndroid.library.router.request.Request;
import com.aleksa.syndroid.objects.battery.validators.BatteryValidator;

import java.util.HashMap;
import java.util.Map;

public class BatteryController extends BaseController
{
    {
        validator = new BatteryValidator();
    }

    public String batteryPercentage(Context context, Request request)
    {
        int batteryLevel = getBatteryPercentage(context);

        Map<String, String> params = new HashMap<>();
        params.put("percentage", Integer.toString(batteryLevel));

        return this.respond(request, params, 200);
    }

    private int getBatteryPercentage(Context context)
    {
        IntentFilter iFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, iFilter);

        int level = batteryStatus != null ? batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) : -1;
        int scale = batteryStatus != null ? batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1) : -1;

        float batteryPct = level / (float) scale;

        return (int) (batteryPct * 100);
    }


}
