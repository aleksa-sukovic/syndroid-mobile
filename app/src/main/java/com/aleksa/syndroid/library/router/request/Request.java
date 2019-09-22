package com.aleksa.syndroid.library.router.request;

import com.aleksa.syndroid.library.router.Route;
import com.aleksa.syndroid.library.router.Router;

import java.util.HashMap;
import java.util.Map;
import androidx.annotation.NonNull;

public class Request
{
    private Route route;
    private boolean expectsResponse;
    private Map<String, String> params;
    private Router.ResponseCallback responseCallback;

    public Request(Route route)
    {
        this.params = new HashMap<>();
        this.route = route;
        this.expectsResponse = this.has("expectsResponse") && this.input("expectsResponse").equals("yes");
    }

    public boolean typeRequest()
    {
        return has("type") && input("type").equals("request");
    }

    public boolean typeResponse()
    {
        return has("type") && input("type").equals("response");
    }

    @NonNull
    @Override
    public String toString()
    {
        return route.toString();
    }

    public String getType()
    {
        return input("type");
    }

    public String getStatus()
    {
        return input("status");
    }

    public long getID()
    {
        return has("id") ? Long.parseLong(input("id")) : -1;
    }

    public Route getRoute()
    {
        return route;
    }

    public boolean has(String param)
    {
        return this.params.containsKey(param);
    }

    public String input(String param)
    {
        return this.params.get(param);
    }

    public void addParam(String key, String value)
    {
        this.route.addParam(key, value);
    }

    public void setRoute(Route route)
    {
        this.route = route;
    }

    public Map<String, String> getParams()
    {
        return params;
    }

    public Router.ResponseCallback getResponseCallback()
    {
        return this.responseCallback;
    }

    public boolean expectsResponse()
    {
        return this.expectsResponse;
    }

    public static class Builder {
        private static int ID = 0;
        protected Request request;

        public Builder() {
            request = new Request(new Route());
        }

        public Builder addParam(String key, String value)
        {
            request.addParam(key, value);
            return this;
        }

        public Builder setRoute(Route route) {
            request.route = route;
            return this;
        }

        public Builder setRouteByPath(String path) {
            request.route = new Route(path);
            return this;
        }

        public Builder setType(String type) {
            request.addParam("type", type);
            return this;
        }

        public Builder setStatus(String status) {
            request.addParam("status", status);
            return this;
        }

        public Builder expectResponse(boolean expects) {
            request.addParam("expectsResponse", expects ? "yes" : "no");
            return this;
        }

        public Builder withID(Long id)
        {
            request.addParam("id", Long.toString(id));
            return this;
        }

        public Builder autoincrement()
        {
            request.addParam("id", Long.toString(++Builder.ID));
            return this;
        }

        public Builder addResponseCallback(Router.ResponseCallback listener)
        {
            request.responseCallback = listener;
            return this;
        }

        public Request build()
        {
            return request;
        }
    }
}
