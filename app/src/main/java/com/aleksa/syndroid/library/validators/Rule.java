package com.aleksa.syndroid.library.validators;

import androidx.annotation.NonNull;

public class Rule
{
    private String name;
    private String[] handlers;
    private String pattern;
    private boolean required;
    private boolean global;

    public Rule(String name, String handlers, String pattern, boolean required)
    {
        this.name = name;
        this.pattern = pattern;
        this.required = required;

        this.handlers = handlers.split("\\|");
        this.global = handlers.equals("*");
    }

    public String[] getHandlers()
    {
        return handlers;
    }

    public boolean isGlobal()
    {
        return global;
    }

    public String getPattern()
    {
        return pattern;
    }

    public void setPattern(String pattern)
    {
        this.pattern = pattern;
    }

    public boolean isRequired()
    {
        return required;
    }

    public String getName()
    {
        return name;
    }

    @NonNull
    @Override
    public String toString()
    {
        String handler = "";
        for (String h : handlers) {
            handler += h + ":";
        }

        return getName() + ":"+ handler + getPattern();
    }
}
