package com.aleksa.syndroid.library.providers;

import java.util.ArrayList;
import java.util.List;

public class ServiceProviderContainer
{
    private static List<ServiceProvider> serviceProviders;

    static {
        serviceProviders = new ArrayList<>();
    }

    public static List<ServiceProvider> getProvider()
    {
        return serviceProviders;
    }
}
