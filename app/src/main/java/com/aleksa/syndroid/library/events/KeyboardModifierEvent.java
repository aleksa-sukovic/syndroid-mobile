package com.aleksa.syndroid.library.events;

import android.util.Pair;

import com.aleksa.syndroid.fragments.keyboard.buttons.KeyboardButton;

public class KeyboardModifierEvent extends Event<KeyboardModifierEvent.Type, Pair<KeyboardButton, Boolean>>
{
    public KeyboardModifierEvent(Type keyboardModifierType, Pair<KeyboardButton, Boolean> keyboardButtonBooleanPair)
    {
        super(keyboardModifierType, keyboardButtonBooleanPair);
    }

    public enum Type
    {
        MODIFIER_STATE_CHANGE
    }
}
