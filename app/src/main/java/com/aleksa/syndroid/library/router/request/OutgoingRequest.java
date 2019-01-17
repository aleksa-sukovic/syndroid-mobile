package com.aleksa.syndroid.library.router.request;

import com.aleksa.syndroid.library.router.Route;
import com.aleksa.syndroid.library.router.response.Response;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;

public class OutgoingRequest implements Request
{

    private long id;
    private Map<String, String> params;
    private Route route;
    private ResponseCallback onResponse;

    private OutgoingRequest(Route route, long id, Map<String, String> params, ResponseCallback onResponse)
    {
        this.id = id;
        this.params = params;
        this.route = route;
        this.onResponse = onResponse;
    }

    public void fireResponseCallback(Response response)
    {
        if (onResponse != null) {
            onResponse.consume(response);
        }
    }

    @NonNull
    @Override
    public String toString()
    {
        return route.getPath();
    }

    @Override
    public String getType()
    {
        return "response";
    }

    @Override
    public long getId()
    {
        return id;
    }

    @Override
    public Route getRoute()
    {
        return route;
    }

    @Override
    public Map<String, String> getParams()
    {
        return params;
    }

    @Override
    public boolean expectsResponse()
    {
        String result = params.get("expects_response");

        return result != null && result.equals("true");
    }

    public interface ResponseCallback
    {
        void consume(Response response);
    }

    public static class Builder
    {
        private String routePath;
        private Map<String, String> params;
        private long id;
        private ResponseCallback onResponse;

        public Builder()
        {
            params = new HashMap<>();

            id = Calendar.getInstance().getTimeInMillis();
            params.put("request_id", Long.toString(id));
        }

        public Builder addParam(String key, String value)
        {
            params.put(key, value);
            return this;
        }

        public Builder setRoutePath(String routePath)
        {
            this.routePath = routePath;
            return this;
        }

        public Builder setExpectsResponse(boolean expects)
        {
            params.put("expects_response", Boolean.toString(expects));
            return this;
        }

        public Builder setOnResponseCallback(ResponseCallback onResponse)
        {
            this.onResponse = onResponse;
            return this;
        }

        public OutgoingRequest build()
        {
            Route route = constructRoute();

            return new OutgoingRequest(route, id, params, onResponse);
        }

        private Route constructRoute()
        {
            StringBuilder builder = new StringBuilder(routePath);

            if (builder.charAt(builder.length() - 1) == '/') {
                builder.deleteCharAt(builder.length() - 1);
            }

            if (params.keySet().size() < 1) {
                return new Route(builder.toString());
            }

            builder.append('?');
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.append(entry.getKey());
                builder.append('=');
                builder.append(entry.getValue());
                builder.append('&');
            }

            return new Route(builder.substring(0, builder.length() - 1));
        }

    }
}
