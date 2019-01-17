package com.aleksa.syndroid.library.router;

import android.util.Log;

import com.aleksa.syndroid.library.exceptions.InvalidRouteException;
import com.aleksa.syndroid.library.exceptions.RouteNotFoundException;
import com.aleksa.syndroid.library.router.request.IncomingRequest;

import java.util.List;
import java.util.Map;

public class Router
{
    private List<Route> routes;
    private RouteMatcher matcher;
    private RouteParser parser;

    public Router(List<Route> routes)
    {
        this.routes = routes;
        this.matcher = new RouteMatcher(routes);
        this.parser = new RouteParser();
    }

    public IncomingRequest handle(String routeString) throws InvalidRouteException, RouteNotFoundException
    {
        if (!matcher.isValidRoute(routeString)) {
            throw new InvalidRouteException();
        }

        Route route = matcher.match(routeString);
        if (route == null) {
            route = new Route();
            throw new RouteNotFoundException(new IncomingRequest(route, parser.parseParams(route, routeString)));
        }

        Map<String, String> params = parser.parseParams(route, routeString);

        return new IncomingRequest(route, params);
    }
}
