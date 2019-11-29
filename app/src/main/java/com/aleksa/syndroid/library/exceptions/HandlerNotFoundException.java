package com.aleksa.syndroid.library.exceptions;

import com.aleksa.syndroid.library.router.request.Request;

public class HandlerNotFoundException extends BaseException
{
    public HandlerNotFoundException(String handler, String className, Request request)
    {
        super("Handler '" + handler + "' not found in " + className, 501, request);
    }
}
