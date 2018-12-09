package com.aleksa.syndroid.library.providers;

import com.aleksa.syndroid.objects.file.FileServiceProvider;

import java.util.ArrayList;
import java.util.List;

public class ServiceProviderContainer
{
    private static List<ServiceProvider> serviceProviders;

    static {
        serviceProviders = new ArrayList<>();

        serviceProviders.add(new FileServiceProvider());
    }

    public static List<ServiceProvider> getProvider()
    {
        return serviceProviders;
    }
}
