package com.aleksa.syndroid.library.managers;

import android.view.KeyEvent;

import com.aleksa.syndroid.library.router.request.OutgoingRequest;

public class KeyPressManager
{
    public OutgoingRequest constructRequest(int keyCode, KeyEvent event)
    {
        int code = event.getUnicodeChar();

        OutgoingRequest.Builder requestBuilder = new OutgoingRequest.Builder();
        requestBuilder.setRoutePath("/keyboard/type");
        requestBuilder.addParam("key", Integer.toString(code));

        if (keyCode == KeyEvent.KEYCODE_SHIFT_LEFT || keyCode == KeyEvent.KEYCODE_SHIFT_RIGHT) {
            requestBuilder.addParam("shift", "true");
        }

        if (keyCode == KeyEvent.KEYCODE_DEL) {
            requestBuilder.addParam("backspace", "true");
        }

        return requestBuilder.build();
    }
}
