package com.aleksa.syndroid.library.managers;

import android.content.Context;
import android.view.KeyEvent;
import com.aleksa.syndroid.fragments.keyboard.buttons.KeyboardButton;
import com.aleksa.syndroid.fragments.keyboard.buttons.ShiftButton;
import com.aleksa.syndroid.library.router.request.Request;
import java.util.Map;

public class KeyPressManager
{
    public Request constructRequest(Context context, int keyCode, KeyEvent event, Map<KeyboardButton, Boolean> modifiers)
    {
        int code = event.getUnicodeChar();

        Request.Builder requestBuilder = new Request.Builder();
        requestBuilder.setRouteByPath("/keyboard/type");
        requestBuilder.autoincrement();
        requestBuilder.setType("request");
        requestBuilder.addParam("key", Integer.toString(code));

        if (keyCode == KeyEvent.KEYCODE_SHIFT_LEFT || keyCode == KeyEvent.KEYCODE_SHIFT_RIGHT) {
            return null;
        }

        if (keyCode == KeyEvent.KEYCODE_DEL) {
            requestBuilder.addParam("key", "backspace");
            requestBuilder.addParam("modifierInput", "true");

            return requestBuilder.build();
        }

        addShiftKey(context, event, modifiers);
        addModifiers(requestBuilder, modifiers);

        return requestBuilder.build();
    }

    private void addShiftKey(Context context, KeyEvent event, Map<KeyboardButton,Boolean> modifiers)
    {
        ShiftButton shiftButton = new ShiftButton(context);
        Boolean shifted = modifiers.get(shiftButton);

        if (shifted == null && event.isShiftPressed()) {
            modifiers.put(shiftButton, true);

            return;
        }

        modifiers.remove(shiftButton);
    }

    private void addModifiers(Request.Builder requestBuilder, Map<KeyboardButton,Boolean> modifiers)
    {
        String modifiersString = constructModifiersString(modifiers);

        if (modifiersString != null) {
            requestBuilder.addParam("modifiers", modifiersString);
        }
    }

    private String constructModifiersString(Map<KeyboardButton, Boolean> modifiers)
    {
        if (modifiers.keySet().size() < 1) {
            return null;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<KeyboardButton, Boolean> entry : modifiers.entrySet()) {
            KeyboardButton button = entry.getKey();
            Boolean toggled = entry.getValue();

            if (toggled) {
                stringBuilder.append(button.getKeyCode());
                stringBuilder.append("|");
            }
        }

        if (stringBuilder.length() == 0) {
            return null;
        }

        return stringBuilder.length() != 0 ? stringBuilder.toString().substring(0, stringBuilder.length() - 1) : null;
    }
}
