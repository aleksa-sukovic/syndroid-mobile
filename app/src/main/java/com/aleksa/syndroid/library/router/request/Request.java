package com.aleksa.syndroid.library.router.request;

import com.aleksa.syndroid.library.router.Route;

import java.util.Map;

public interface Request
{
    String getType();
    long getId();
    Route getRoute();
    Map<String, String> getParams();
    boolean expectsResponse();
}
