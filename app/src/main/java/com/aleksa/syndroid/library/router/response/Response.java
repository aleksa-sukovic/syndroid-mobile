package com.aleksa.syndroid.library.router.response;

import com.aleksa.syndroid.library.router.Route;

import java.util.Map;

public class Response
{
    private Route route;
    private Map<String, String> data;
    private int responseCode;

    public Response(Route route, Map<String, String> data)
    {
        this.route = route;
        this.data = data;
        this.responseCode = getResponseCode(data);
    }

    private int getResponseCode(Map<String,String> data)
    {
        String responseCodeStr = data.get("responseCode");
        if (responseCodeStr == null) {
            return 200;
        }

        try {
            return Integer.parseInt(responseCodeStr);
        } catch(Exception e) {
            return 200;
        }
    }

    public Route getRoute()
    {
        return route;
    }

    public Map<String, String> getData()
    {
        return data;
    }

    public int getResponseCode()
    {
        return this.responseCode;
    }
}
