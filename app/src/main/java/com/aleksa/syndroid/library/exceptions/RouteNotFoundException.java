package com.aleksa.syndroid.library.exceptions;

import com.aleksa.syndroid.library.router.request.Request;

public class RouteNotFoundException extends BaseException
{
    public RouteNotFoundException(Request request)
    {
        super("Route not found", 404, request);
    }
}
