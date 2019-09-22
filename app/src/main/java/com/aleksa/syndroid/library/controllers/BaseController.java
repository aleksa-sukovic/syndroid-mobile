package com.aleksa.syndroid.library.controllers;

import android.content.Context;
import com.aleksa.syndroid.library.exceptions.HandlerNotFoundException;
import com.aleksa.syndroid.library.router.request.Request;
import com.aleksa.syndroid.library.validators.BaseValidator;
import com.aleksa.syndroid.library.exceptions.ValidationException;
import org.json.JSONObject;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

abstract public class BaseController
{
    protected BaseValidator validator;

    public String handle(Context context, Request request) throws HandlerNotFoundException, ValidationException
    {
        Method handler = getHandler(request.getRoute().getHandler());
        if (handler == null) {
            throw new HandlerNotFoundException(request.getRoute().getHandler(), getClass().getName(), request);
        }

        this.validator.validate(request);

        try {
            return (String) handler.invoke(this, context, request);
        }
        catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Method getHandler(String handler)
    {
        try {
            return getClass().getMethod(handler, Context.class, Request.class);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    protected String respond(Request request, Map<String, String> data, int statusCode)
    {
        return new Request.Builder()
            .addParam("id", Long.toString(request.getID()))
            .addParam("type", "response")
            .addParam("status_code", Integer.toString(statusCode))
            .addParam("data", new JSONObject(data).toString())
            .build()
            .toString();
    }
}
