package com.aleksa.syndroid.library.router.request;

import com.aleksa.syndroid.library.router.Route;

import java.util.Map;

public class IncomingRequest implements Request
{
    private long id;
    private Route route;
    private Map<String, String> params;

    public IncomingRequest(Route route, Map<String,String> params)
    {
        this.route = route;
        this.params = params;
        this.id = initializeId(params);
    }

    private long initializeId(Map<String,String> params)
    {
        if (params.get("request_id") == null) {
            return 0;
        }

        try
        {
            return Long.parseLong(params.get("request_id"));
        }
        catch(Exception e)
        {
            return 0;
        }
    }

    public Route getRoute()
    {
        return route;
    }

    @Override
    public Map<String, String> getParams()
    {
        return params;
    }

    @Override
    public boolean expectsResponse()
    {
        String expectsResponse = params.get("expects_response");

        return expectsResponse != null && expectsResponse.equals("true");
    }

    @Override
    public String getType()
    {
        return "request";
    }

    @Override
    public long getId()
    {
        return id;
    }
}
