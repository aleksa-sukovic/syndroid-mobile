package com.aleksa.syndroid.library.router;

import java.util.HashMap;
import java.util.Map;

public class RouteParser
{
    public static Map<String, String> parseParams(String routePath)
    {
        Map<String, String> params = new HashMap<>();

        params = parseGetParams(params, routePath);

        return params;
    }

    private static Map<String, String> parseGetParams(Map<String, String> params, String routeString)
    {
        String[] helper = routeString.split("\\?");

        if (helper.length < 2) {
            return params;
        }

        helper = helper[1].split("&");
        for (String element : helper) {
            String[] data = element.split("=");

            if (data.length >= 2) {
                params.put(data[0], data[1]);
            }
        }

        return params;
    }
}
