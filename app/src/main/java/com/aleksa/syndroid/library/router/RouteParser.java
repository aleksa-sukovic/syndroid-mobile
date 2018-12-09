package com.aleksa.syndroid.library.router;

import java.util.HashMap;
import java.util.Map;

public class RouteParser
{
    public Map<String, String> parseParams(Route route, String routeString)
    {
        Map<String, String> params = new HashMap<>();

        params = parseGetParams(params, routeString);
        params = parseEmbeddedParams(params, route.getPath(), routeString);

        return params;
    }

    private Map<String, String> parseGetParams(Map<String, String> params, String routeString)
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

    private Map<String, String> parseEmbeddedParams(Map<String, String> params, String routePath, String routeString)
    {
        routeString = routeString.split("\\?")[0];

        String[] routePathParts = routePath.split("/");
        String[] routeStringParts = routeString.split("/");

        for (int i = 0; i < routePathParts.length; i++) {
            if (!routePathParts[i].matches("\\{[a-zA-Z0-9_]+\\}")) {
                continue;
            }

            String paramName = routePathParts[i].substring(1, routePathParts[i].length() - 1); // removing {} from param
            params.put(paramName, routeStringParts[i]);
        }

        return params;
    }
}
