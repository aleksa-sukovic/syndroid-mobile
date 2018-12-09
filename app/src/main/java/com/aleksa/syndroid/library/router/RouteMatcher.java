package com.aleksa.syndroid.library.router;

import java.util.List;

public class RouteMatcher
{
    private List<Route> routes;
    private String routeRegEx = "^(/[a-zA-Z0-9{}\\-_]+)+(\\?([a-zA-Z0-9_]+=[^?&]+)(&[a-zA-Z0-9_]+=[^?&]+)*)*$";

    public RouteMatcher(List<Route> routes)
    {
        this.routes = routes;
    }

    public Route match(String routeString)
    {
        routeString = routeString.split("\\?")[0];

        for (Route route : routes) {
            if (routeString.matches(route.getRegExPath())) {
                return route;
            }
        }

        return null;
    }

    public boolean matchWith(String routeString, Route route)
    {
        routeString = routeString.split("\\?")[0];

        return routeString.matches(route.getRegExPath());
    }

    public boolean isValidRoute(String routeString)
    {
        if (routeString == null) {
            return false;
        }

        return routeString.matches(routeRegEx);
    }
}
