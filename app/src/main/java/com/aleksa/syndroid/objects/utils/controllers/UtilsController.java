package com.aleksa.syndroid.objects.utils.controllers;

import android.content.Context;

import com.aleksa.syndroid.library.controllers.BaseController;
import com.aleksa.syndroid.library.router.request.IncomingRequest;
import com.aleksa.syndroid.objects.utils.validators.UtilsValidator;
import com.jaredrummler.android.device.DeviceName;

import java.util.HashMap;
import java.util.Map;

public class UtilsController extends BaseController
{
    {
        validator = new UtilsValidator();
    }

    public String deviceInfo(Context context, IncomingRequest request)
    {
        Map<String, String> params = new HashMap<>();

        synchronized(request) {
            DeviceName.with(context).request((info, error) -> {
                params.put("manufacturer", info.manufacturer);
                params.put("name", info.marketName);
                params.put("model", info.model);
                params.put("codename", info.codename);
                params.put("deviceName", info.getName());

                synchronized(request) {
                    request.notify();
                }
            });

            try {
                request.wait();

                return this.respond(request, params, 200);
            } catch(InterruptedException e) {
                return this.respond(request, params, 500);
            }
        }
    }

}
