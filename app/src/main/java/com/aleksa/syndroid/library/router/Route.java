package com.aleksa.syndroid.library.router;
import androidx.annotation.NonNull;
import com.aleksa.syndroid.library.controllers.BaseController;
import java.util.HashMap;
import java.util.Map;

public class Route
{
    private String controllerName;
    private String handlerName;
    private String path;
    private Map<String, String> params;

    public Route()
    {
        path = "";
        controllerName = "";
        handlerName = "";
        this.params = new HashMap<>();
    }

    public Route(String path)
    {
        this.path = path.split("\\?")[0];
        this.controllerName = "";
        this.handlerName = "";
        this.params = RouteParser.parseParams(path);
    }

    public Route(String path, String controllerName, String handlerName)
    {
        this.path = path.split("\\?")[0];
        this.controllerName = controllerName;
        this.handlerName = handlerName;
        this.params = RouteParser.parseParams(path);
    }

    public boolean match(Route route)
    {
        return this.path.equals(route.path);
    }

    public void addParam(String key, String value)
    {
        this.params.put(key, value);
    }

    public Map<String, String> getParams()
    {
        return this.params;
    }

    public BaseController getController()
    {
        Class<?> clazz;

        try {
            clazz = Class.forName(controllerName);
            Object controller = clazz.newInstance();
            return (BaseController) controller;
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getPath()
    {
        return path;
    }

    public String getHandler()
    {
        return handlerName;
    }

    public String getControllerName()
    {
        return this.controllerName;
    }

    @NonNull
    @Override
    public String toString() {
        String params = "";
        for (Map.Entry<String, String> entry: this.params.entrySet()) {
            params += entry.getKey() + "=" + entry.getValue() + "&";
        }

        if (params.length() == 0) {
            return this.path;
        } else {
            return this.path + "?" + params.substring(0, params.length() - 1);
        }
    }
}
