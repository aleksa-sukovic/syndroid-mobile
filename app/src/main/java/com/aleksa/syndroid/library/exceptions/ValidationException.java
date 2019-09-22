package com.aleksa.syndroid.library.exceptions;

import com.aleksa.syndroid.library.router.request.Request;
import org.json.JSONObject;
import java.util.Map;

public class ValidationException extends BaseException
{
    private Map<String, Map<String, String>> errors;

    public ValidationException(Map<String, Map<String, String>> errors, Request request)
    {
        super("Validation failed", 422, request);
        this.errors = errors;
    }

    @Override
    public Request render()
    {
        Request exception = super.render();

        exception.addParam("errors", new JSONObject(errors).toString());

        return exception;
    }
}
