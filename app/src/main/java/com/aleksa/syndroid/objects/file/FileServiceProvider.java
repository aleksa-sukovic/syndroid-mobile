package com.aleksa.syndroid.objects.file;

import com.aleksa.syndroid.library.providers.ServiceProvider;
import com.aleksa.syndroid.library.router.Route;

import java.util.Arrays;
import java.util.List;

public class FileServiceProvider implements ServiceProvider
{
    @Override
    public List<Route> getRoutes()
    {
        return Arrays.asList(
            new Route("/files/list", "com.aleksa.syndroid.objects.file.controllers.BatteryController", "list"),
            new Route("/files/show", "com.aleksa.syndroid.objects.file.controllers.BatteryController", "show")
        );
    }
}
