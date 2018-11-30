package com.aleksa.syndroid.library.events;

public class Event<EventType>
{
    private EventType eventType;
    private String message;

    public Event(EventType eventType, String message) {
        this.eventType = eventType;
        this.message   = message;
    }

    public EventType getEventType()
    {
        return eventType;
    }

    public void setEventType(EventType eventType)
    {
        this.eventType = eventType;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }
}
