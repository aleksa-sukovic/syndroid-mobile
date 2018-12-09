package com.aleksa.syndroid.library.router.response;

import com.aleksa.syndroid.library.router.Route;

import java.util.Map;

public class Response
{
    private Route route;
    private Map<String, String> data;

    public Response(Route route, Map<String, String> data)
    {
        this.route = route;
        this.data = data;
    }

    public Route getRoute()
    {
        return route;
    }

    public Map<String, String> getData()
    {
        return data;
    }
}
