package com.aleksa.syndroid.library.application;

import java.util.List;
import java.util.LinkedList;
import com.aleksa.syndroid.library.router.Route;
import com.aleksa.syndroid.library.providers.ServiceProvider;

public class Bootstrapper
{
    private List<Class<?>> providers;

    public Bootstrapper(List<Class<?>> providers)
    {
        this.providers = providers;
    }

    public Bootstrapped bootstrap()
    {
        Bootstrapped result = new Bootstrapped();

        result.routes = bootstrapRoutes();

        return result;
    }

    private List<Route> bootstrapRoutes()
    {
        List<ServiceProvider> providers = initializeProviders();
        List<Route> routes = new LinkedList<>();

        for (ServiceProvider provider : providers) {
            routes.addAll(provider.getRoutes());
        }

        return routes;
    }

    private List<ServiceProvider> initializeProviders()
    {
        List<ServiceProvider> initialized = new LinkedList<>();

        for (Class<?> classInstance : providers) {
            try {
                initialized.add((ServiceProvider) classInstance.newInstance());
            } catch(IllegalAccessException | InstantiationException e) {
                //
            }
        }

        return initialized;
    }

    public class Bootstrapped
    {
        public List<Route> routes;
    }
}
