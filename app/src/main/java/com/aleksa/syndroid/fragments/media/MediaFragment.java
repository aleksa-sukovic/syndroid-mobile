package com.aleksa.syndroid.fragments.media;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aleksa.syndroid.R;
import com.aleksa.syndroid.library.router.request.OutgoingRequest;

import org.greenrobot.eventbus.EventBus;

public class MediaFragment extends Fragment
{
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_media, container, false);

        initializeVolumeButtons(view);

        return view;
    }

    private void initializeVolumeButtons(View view)
    {
        view.findViewById(R.id.volume_down).setOnClickListener(v -> EventBus.getDefault().post(new OutgoingRequest.Builder().setRoutePath("/media/volume/down").build()));

        view.findViewById(R.id.volume_up).setOnClickListener(v -> EventBus.getDefault().post(new OutgoingRequest.Builder().setRoutePath("/media/volume/up").build()));
    }


}
