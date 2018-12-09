package com.aleksa.syndroid.library.bootstrap;

import com.aleksa.syndroid.library.providers.ServiceProvider;
import com.aleksa.syndroid.library.providers.ServiceProviderContainer;
import com.aleksa.syndroid.library.router.Route;

import java.util.LinkedList;
import java.util.List;

public class Bootstrapper
{
    private Bootstrappable instance;

    public Bootstrapper(Bootstrappable instance)
    {
        this.instance = instance;
    }

    public void bootstrap()
    {
        bootstrapRoutes();
    }

    private void bootstrapRoutes()
    {
        List<ServiceProvider> providers = ServiceProviderContainer.getProvider();
        List<Route> routes = new LinkedList<>();

        for (ServiceProvider provider : providers) {
            routes.addAll(provider.getRoutes());
        }

        instance.serviceProviderData().put(Data.ROUTES, routes);
    }

    public enum Data
    {
        ROUTES
    }
}
