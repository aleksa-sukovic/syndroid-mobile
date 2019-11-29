package com.aleksa.syndroid.fragments.media;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aleksa.syndroid.R;
import com.aleksa.syndroid.library.router.request.Request;

import org.greenrobot.eventbus.EventBus;

public class MediaFragment extends Fragment
{
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_media, container, false);

        initializeButtons(view);

        return view;
    }

    private void initializeButtons(View view)
    {
        view.findViewById(R.id.volume_down).setOnClickListener(v -> EventBus.getDefault().post(
                new Request.Builder()
                    .setRouteByPath("/media/volume/down")
                    .setType("request")
                    .autoincrement()
                    .build()
            ));

        view.findViewById(R.id.volume_up).setOnClickListener(v -> EventBus.getDefault().post(
            new Request.Builder()
                .setRouteByPath("/media/volume/up")
                .setType("request")
                .autoincrement()
                .build()
        ));

        view.findViewById(R.id.play_button).setOnClickListener(v -> EventBus.getDefault().post(
            new Request.Builder()
                .setRouteByPath("/media/play-pause")
                .setType("request")
                .autoincrement()
                .build()
        ));

        view.findViewById(R.id.media_back).setOnClickListener(v -> EventBus.getDefault().post(
            new Request.Builder()
                .setRouteByPath("/media/back")
                .setType("request")
                .autoincrement()
                .build()
        ));

        view.findViewById(R.id.media_forward).setOnClickListener(v -> EventBus.getDefault().post(
            new Request.Builder()
                .setRouteByPath("/media/forward")
                .setType("request")
                .autoincrement()
                .build()
        ));

        view.findViewById(R.id.media_up).setOnClickListener(v -> EventBus.getDefault().post(
            new Request.Builder()
                .setRouteByPath("/media/up")
                .setType("request")
                .autoincrement()
                .build()
        ));

        view.findViewById(R.id.media_down).setOnClickListener(v -> EventBus.getDefault().post(
            new Request.Builder()
                .setRouteByPath("/media/down")
                .setType("request")
                .autoincrement()
                .build()
        ));
    }


}
