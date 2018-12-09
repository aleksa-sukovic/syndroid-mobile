package com.aleksa.syndroid.library.controllers;

import com.aleksa.syndroid.library.controllers.exceptions.HandlerNotFoundException;
import com.aleksa.syndroid.library.router.request.IncomingRequest;
import com.aleksa.syndroid.library.router.request.Request;
import com.aleksa.syndroid.library.validators.BaseValidator;
import com.aleksa.syndroid.library.validators.exceptions.ValidationException;

import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

abstract public class BaseController
{
    protected BaseValidator validator;

    public String handle(IncomingRequest request) throws HandlerNotFoundException, ValidationException
    {
        Method handler = getHandler(request.getRoute().getHandler());
        if (handler == null) {
            throw new HandlerNotFoundException(request.getRoute().getHandler(), getClass().getName(), request);
        }

        this.validator.validate(request);

        try {
            String response = (String) handler.invoke(this, request);
            if (request.expectsResponse()) {
                return response;
            }

            return null;
        }
        catch (IllegalAccessException | InvocationTargetException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    protected String respond(Request request, Map<String, String> data, int statusCode)
    {
        Map<String, String> params = new HashMap<>();

        params.put("request_id", Long.toString(request.getId()));
        params.put("type", "response");
        params.put("status_code", Integer.toString(statusCode));
        params.put("data", new JSONObject(data).toString());

        return convertMapToString(request, params);
    }

    private String convertMapToString(Request request, Map<String,String> data)
    {
        StringBuilder builder = new StringBuilder(request.getRoute().getBasePath());

        if (data.keySet().size() < 1) {
            return builder.toString();
        }

        builder.append('?');
        for (Map.Entry<String, String> entry : data.entrySet()) {
            builder.append(entry.getKey());
            builder.append('=');
            builder.append(entry.getValue());
            builder.append('&');
        }

        return builder.substring(0, builder.length() - 1);
    }

    private Method getHandler(String handler)
    {
        try
        {
            return getClass().getMethod(handler, IncomingRequest.class);
        }
        catch(NoSuchMethodException e)
        {
            return null;
        }
    }
}
