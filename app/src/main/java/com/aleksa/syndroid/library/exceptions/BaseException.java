package com.aleksa.syndroid.library.exceptions;

import com.aleksa.syndroid.library.router.request.Request;

public class BaseException extends Exception
{
    private String message;
    private int statusCode;
    private Request request;

    public BaseException()
    {
        this.message = "";
        this.statusCode = 500;
        this.request = null;
    }

    public BaseException(String message, int statusCode, Request request)
    {
        this.message = message;
        this.statusCode = statusCode;
        this.request = request;
    }

    public Request render()
    {
        Request.Builder builder = new Request.Builder()
            .setRouteByPath("/exception")
            .addParam("message", message)
            .addParam("statusCode", Integer.toString(statusCode));

        if (this.request == null) {
            return builder.build();
        }

        return builder.addParam("type", "response")
            .addParam("id", Long.toString(request.getID()))
            .build();
    }

    @Override
    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public int getStatusCode()
    {
        return statusCode;
    }

    public void setStatusCode(int statusCode)
    {
        this.statusCode = statusCode;
    }

    public Request getRequest()
    {
        return request;
    }

    public void setRequest(Request request)
    {
        this.request = request;
    }
}
