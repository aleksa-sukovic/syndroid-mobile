package com.aleksa.syndroid.objects.battery;

import com.aleksa.syndroid.library.providers.ServiceProvider;
import com.aleksa.syndroid.library.router.Route;

import java.util.Arrays;
import java.util.List;

public class BatteryServiceProvider implements ServiceProvider
{
    @Override
    public List<Route> getRoutes()
    {
        return Arrays.asList(
            new Route("/battery/percentage", "com.aleksa.syndroid.objects.battery.controllers.BatteryController", "batteryPercentage")
        );
    }
}
