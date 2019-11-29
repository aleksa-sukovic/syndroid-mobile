package com.aleksa.syndroid.library.events;

import com.aleksa.syndroid.library.router.request.Request;

public class ResponseEvent extends Event<ResponseEvent.EventCode, Request>
{
    public ResponseEvent(EventCode eventCode, Request response)
    {
        super(eventCode, response);
    }

    public enum EventCode
    {
        INCOMING_RESPONSE
    }
}
