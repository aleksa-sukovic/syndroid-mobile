package com.aleksa.syndroid.library.events;

public class ApplicationEvent extends Event<ApplicationEvent.EventCode, String>
{
    public ApplicationEvent(EventCode eventCode, String message)
    {
        super(eventCode, message);
    }

    public enum EventCode
    {
        SERVER_CREATION_ERROR, SERVER_CONNECTION_ERROR,
        SERVER_DISCONNECT, SERVER_CONNECT,
        INCOMING_RESPONSE,
        CLIENT_CONNECTED, CLIENT_DISCONNECTED,
    }
}
