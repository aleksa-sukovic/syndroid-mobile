package com.aleksa.syndroid.library.managers;

import com.aleksa.syndroid.library.controllers.exceptions.HandlerNotFoundException;
import com.aleksa.syndroid.library.exceptions.InvalidRouteException;
import com.aleksa.syndroid.library.exceptions.RouteNotFoundException;
import com.aleksa.syndroid.library.router.Route;
import com.aleksa.syndroid.library.router.RouteParser;
import com.aleksa.syndroid.library.router.request.OutgoingRequest;
import com.aleksa.syndroid.library.validators.exceptions.ValidationException;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SocketManager implements Manager<String, String>
{
    private RequestManager requestManager;
    private ResponseManager responseManager;
    private RouteParser routeParser;

    public SocketManager(List<Route> availableRoutes)
    {
        requestManager = new RequestManager(availableRoutes);
        responseManager = new ResponseManager();
        routeParser = new RouteParser();
    }

    @Override
    public String manage(String message) throws InvalidRouteException, RouteNotFoundException, HandlerNotFoundException, ValidationException
    {
        if (isResponse(message)) {
            return responseManager.manage(message);
        }

        return requestManager.manage(message);
    }

    public String prepareOutgoingRequest(OutgoingRequest request)
    {
        if (request.expectsResponse()) {
            responseManager.expectResponseFor(request);
        }

        return request.getRoute().getPath();
    }

    private boolean isResponse(String message)
    {
        try
        {
            Map<String, String> params = routeParser.parseParams(new Route(), message);

            if (params.get("type") == null || params.get("request_id") == null) {
                return false;
            }

            return Objects.equals(params.get("type"), "response");
        }
        catch(Exception e)
        {
            return false;
        }
    }
}
