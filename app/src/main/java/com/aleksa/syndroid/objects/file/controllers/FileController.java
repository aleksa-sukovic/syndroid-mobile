package com.aleksa.syndroid.objects.file.controllers;

import android.util.Log;

import com.aleksa.syndroid.library.controllers.BaseController;
import com.aleksa.syndroid.library.router.request.IncomingRequest;
import com.aleksa.syndroid.objects.file.validators.FileValidator;

import java.util.HashMap;
import java.util.Map;

public class FileController extends BaseController
{
    {
        validator = new FileValidator();
    }

    public String list(IncomingRequest request)
    {
        Log.d("Application", "BatteryController -> list method");

        Map<String, String> params = new HashMap<>();

        params.put("result", "success");
        params.put("files", "{ data: not_found }");

        return this.respond(request, params, 200);
    }

    public String show(IncomingRequest request)
    {
        Log.d("Application", "BatteryController -> show method");

        Map<String, String> params = new HashMap<>();

        params.put("result", "success");
        params.put("files", "here_goes_data_for_requested_file");

        return this.respond(request, params, 200);
    }
}
