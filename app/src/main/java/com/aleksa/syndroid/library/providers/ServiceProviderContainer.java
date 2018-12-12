package com.aleksa.syndroid.library.providers;

import java.util.Arrays;
import java.util.List;

public class ServiceProviderContainer
{
    private List<Class<?>> serviceProviders;

    {
        serviceProviders = Arrays.asList(
            //
        );
    }

    public List<Class<?>> getProviders()
    {
        return serviceProviders;
    }
}
