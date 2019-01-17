package com.aleksa.syndroid.library.bootstrap;

import java.util.Map;

public interface Bootstrappable
{
    Map<Bootstrapper.Data, Object> serviceProviderData();
}
