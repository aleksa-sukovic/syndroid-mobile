package com.aleksa.syndroid.fragments;

import android.support.v4.app.Fragment;

import com.aleksa.syndroid.fragments.files.FilesFragment;
import com.aleksa.syndroid.fragments.keyboard.KeyboardFragment;
import com.aleksa.syndroid.fragments.media.MediaFragment;
import com.aleksa.syndroid.fragments.mouse.MouseFragment;
import com.aleksa.syndroid.fragments.notifications.NotificationFragment;
import com.aleksa.syndroid.fragments.state.StateFragment;

public class FragmentFactory
{
    public static Fragment initializeFromTag(String tag) {
        switch(tag) {
            case "mouse": return new MouseFragment();
            case "media" : return new MediaFragment();
            case "state" : return new StateFragment();
            case "keyboard" : return new KeyboardFragment();
            case "files" : return new FilesFragment();
            case "notifications": return new NotificationFragment();
            default: return null;
        }
    }
}
