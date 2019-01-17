package com.aleksa.syndroid.library.router;

import com.aleksa.syndroid.library.controllers.BaseController;

public class Route
{
    private String regExPath;
    private String controllerName;
    private String handlerName;
    private String path;

    public Route()
    {
        path = "";
        controllerName = "";
        handlerName = "";
        regExPath = "";
    }

    public Route(String path)
    {
        this.path = path;
        this.controllerName = "";
        this.handlerName = "";
        regExPath = "";

        this.constructRegExPath(path);
    }

    public Route(String path, String controllerName, String handlerName)
    {
        this.path = path;
        this.controllerName = controllerName;
        this.handlerName = handlerName;
        this.regExPath = "";

        this.constructRegExPath(path);
    }

    private void constructRegExPath(String path)
    {
        path = path.split("\\?")[0];
        makeRegExPath(path);
        this.regExPath = "^" + this.regExPath + "$";
    }

    private void makeRegExPath(String path)
    {
        int openParamBracket = path.indexOf('{');
        int closeParamBracket = path.indexOf('}');

        if (openParamBracket == -1 || closeParamBracket == -1) {
            this.regExPath += path;
            return;
        }

        this.regExPath = path.substring(0, openParamBracket);
        this.regExPath += "[a-zA-Z0-9_]+";

        makeRegExPath(path.substring(closeParamBracket + 1));
    }

    public BaseController getController()
    {
        Class<?> clazz;

        try {
            clazz = Class.forName(controllerName);
            Object controller = clazz.newInstance();
            return (BaseController) controller;
        } catch(IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean isExceptionRoute()
    {
        return getBasePath().matches("^/exception");
    }

    public String getRegExPath()
    {
        return regExPath;
    }

    public String getPath()
    {
        return path;
    }

    public String getBasePath()
    {
        return path.split("\\?")[0];
    }

    public String getHandler()
    {
        return handlerName;
    }
}
