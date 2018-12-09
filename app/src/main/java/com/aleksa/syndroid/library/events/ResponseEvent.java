package com.aleksa.syndroid.library.events;

import com.aleksa.syndroid.library.router.response.Response;

public class ResponseEvent extends Event<ResponseEvent.EventCode, Response>
{
    public ResponseEvent(EventCode eventCode, Response response)
    {
        super(eventCode, response);
    }

    public enum EventCode
    {
        INCOMING_RESPONSE
    }
}
