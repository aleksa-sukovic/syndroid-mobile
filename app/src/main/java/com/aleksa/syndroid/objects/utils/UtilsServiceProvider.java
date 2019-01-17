package com.aleksa.syndroid.objects.utils;

import com.aleksa.syndroid.library.providers.ServiceProvider;
import com.aleksa.syndroid.library.router.Route;

import java.util.Arrays;
import java.util.List;

public class UtilsServiceProvider implements ServiceProvider
{
    @Override
    public List<Route> getRoutes()
    {
        return Arrays.asList(
            new Route("/device/info", "com.aleksa.syndroid.objects.utils.controllers.UtilsController", "deviceInfo"),
            new Route("/device/ping", "com.aleksa.syndroid.objects.utils.controllers.UtilsController", "ping")
        );
    }
}
