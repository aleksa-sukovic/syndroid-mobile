package com.aleksa.syndroid.library.bootstrap;

import com.aleksa.syndroid.library.providers.ServiceProvider;
import com.aleksa.syndroid.library.providers.ServiceProviderContainer;
import com.aleksa.syndroid.library.router.Route;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Bootstrapper
{
    private Bootstrappable instance;
    private ServiceProviderContainer container;

    public Bootstrapper(Bootstrappable instance)
    {
        this.instance = instance;
        this.container = new ServiceProviderContainer();
    }

    public void bootstrap()
    {
        bootstrapRoutes();
    }

    private void bootstrapRoutes()
    {
        List<ServiceProvider> providers = initializeProviders();
        List<Route> routes = new LinkedList<>();

        for (ServiceProvider provider : providers) {
            routes.addAll(provider.getRoutes());
        }

        instance.serviceProviderData().put(Data.ROUTES, routes);
    }

    private List<ServiceProvider> initializeProviders()
    {
        List<ServiceProvider> providers = new ArrayList<>();

        for (Class<?> classInstance : container.getProviders()) {
            try
            {
                providers.add((ServiceProvider) classInstance.newInstance());
            }
            catch(IllegalAccessException | InstantiationException e)
            {
                //
            }
        }

        return providers;
    }

    public enum Data
    {
        ROUTES
    }
}
