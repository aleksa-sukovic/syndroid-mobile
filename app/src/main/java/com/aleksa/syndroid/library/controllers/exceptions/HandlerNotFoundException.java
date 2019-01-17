package com.aleksa.syndroid.library.controllers.exceptions;

import com.aleksa.syndroid.library.exceptions.BaseException;
import com.aleksa.syndroid.library.router.request.Request;

public class HandlerNotFoundException extends BaseException
{
    public HandlerNotFoundException(String handler, String className, Request request)
    {
        super("Handler '" + handler + "' not found in " + className, 501, request);
    }
}
