package com.aleksa.syndroid.fragments.keyboard;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aleksa.syndroid.R;
import com.aleksa.syndroid.fragments.Wakable;
import com.aleksa.syndroid.fragments.keyboard.buttons.KeyboardButton;
import com.aleksa.syndroid.library.events.KeyboardModifierEvent;
import com.aleksa.syndroid.managers.KeyboardManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

public class KeyboardFragment extends Fragment implements Wakable
{
    private Map<KeyboardButton, Boolean> modifiers;

    {
        modifiers = new HashMap<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view =  inflater.inflate(R.layout.fragment_keyboard, container, false);

        KeyboardManager.showKeyboard(getContext());

        return view;
    }

    public Map<KeyboardButton, Boolean> getModifiers()
    {
        return modifiers;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleModifierStateChange(KeyboardModifierEvent event)
    {
        Pair<KeyboardButton, Boolean> pair = event.getData();

        modifiers.put(pair.first, pair.second);
    }

    @Override
    public void onStart()
    {
        EventBus.getDefault().register(this);

        super.onStart();
    }

    @Override
    public void onPause()
    {
        EventBus.getDefault().unregister(this);

        super.onPause();
    }

    @Override
    public void handleWakeUp()
    {
        KeyboardManager.showKeyboard(getContext());
    }
}
