package com.aleksa.syndroid.library.events;

public class ApplicationEvent extends Event<ApplicationEvent.EventCode>
{
    public ApplicationEvent(EventCode eventCode, String message)
    {
        super(eventCode, message);
    }

    public enum EventCode
    {
        SERVER_CREATION_ERROR, SERVER_CONNECTION_ERROR
    }
}
