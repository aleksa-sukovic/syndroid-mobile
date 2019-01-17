package com.aleksa.syndroid.library.validators.exceptions;

import android.util.Log;

import com.aleksa.syndroid.library.exceptions.BaseException;
import com.aleksa.syndroid.library.router.request.Request;

import org.json.JSONArray;
import org.json.JSONException;
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
    public String render()
    {
        Log.d("Application", "render: errors map -> " + errors.toString());
        String exception = super.render();

        String jsonErrors = getJsonErrors();
        if (jsonErrors != null) {
            exception += "&errors=" + jsonErrors;
        }

        return exception;
    }

    private String getJsonErrors()
    {
        JSONObject jsonObject = new JSONObject(errors);
        return jsonObject.toString();
    }
}
