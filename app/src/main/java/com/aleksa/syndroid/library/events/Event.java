package com.aleksa.syndroid.library.events;

public class Event<EventType, EventData>
{
    private EventType eventType;
    private EventData data;

    Event(EventType eventType, EventData data)
    {
        this.eventType = eventType;
        this.data   = data;
    }

    public EventType getEventType()
    {
        return eventType;
    }

    public void setEventType(EventType eventType)
    {
        this.eventType = eventType;
    }

    public EventData getData()
    {
        return data;
    }

    public void setData(EventData data)
    {
        this.data = data;
    }
}
