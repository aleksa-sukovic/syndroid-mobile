package com.aleksa.syndroid.objects.battery.controllers;

import android.content.Context;
import android.util.Log;

import com.aleksa.syndroid.library.controllers.BaseController;
import com.aleksa.syndroid.library.router.request.IncomingRequest;
import com.aleksa.syndroid.objects.battery.validators.BatteryValidator;

import java.util.HashMap;
import java.util.Map;

public class BatteryController extends BaseController
{
    {
        validator = new BatteryValidator();
    }

    public String batteryPercentage(Context context, IncomingRequest request)
    {
        Log.d("Application", "BatteryController -> battery percentage");

        Map<String, String> params = new HashMap<>();

        return this.respond(request, params, 200);
    }
}
