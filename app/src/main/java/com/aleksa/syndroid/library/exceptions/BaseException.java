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

    public String render()
    {
        String output = "/exception?message=" + message + "&statusCode=" + statusCode;

        if (request == null) {
            return output;
        }

        output += "&type=response&request_id=" + request.getId();

        return output;
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
