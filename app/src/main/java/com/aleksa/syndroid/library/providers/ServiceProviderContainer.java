package com.aleksa.syndroid.library.providers;

import com.aleksa.syndroid.objects.file.FileServiceProvider;
import com.aleksa.syndroid.objects.file.SoundServiceProvider;

import java.util.Arrays;
import java.util.List;

public class ServiceProviderContainer
{
    private List<Class<?>> serviceProviders;

    {
        serviceProviders = Arrays.asList(
            FileServiceProvider.class,
            SoundServiceProvider.class
        );
    }

    public List<Class<?>> getProviders()
    {
        return serviceProviders;
    }
}
