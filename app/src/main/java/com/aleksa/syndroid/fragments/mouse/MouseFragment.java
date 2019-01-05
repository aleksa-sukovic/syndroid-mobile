package com.aleksa.syndroid.fragments.mouse;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aleksa.syndroid.R;
import com.aleksa.syndroid.fragments.mouse.mouse_pad.MousePad;
import com.aleksa.syndroid.fragments.mouse.mouse_pad.MousePadListener;
import com.aleksa.syndroid.library.router.request.OutgoingRequest;

import org.greenrobot.eventbus.EventBus;

public class MouseFragment extends Fragment implements MousePadListener
{
    private MousePad mousePad;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_mouse, container, false);

        initializeMousePad(view);

        return view;
    }

    private void initializeMousePad(View view)
    {
        mousePad = view.findViewById(R.id.mouse_pad);
        mousePad.setMousePadListener(this);
    }

    @Override
    public void onMove(float xOffset, float yOffset)
    {
        EventBus.getDefault().post(new OutgoingRequest.Builder()
            .setRoutePath("/mouse/move")
            .addParam("x", Float.toString(xOffset))
            .addParam("y", Float.toString(yOffset))
            .setExpectsResponse(false)
            .build());
    }

    @Override
    public void onDragStart()
    {
        EventBus.getDefault().post(new OutgoingRequest.Builder()
            .setRoutePath("/mouse/drag/start").build());
    }

    @Override
    public void onDragEnd()
    {
        EventBus.getDefault().post(new OutgoingRequest.Builder()
            .setRoutePath("/mouse/drag/end").build());
    }

    @Override
    public void onLeftClick()
    {
        EventBus.getDefault().post(new OutgoingRequest.Builder()
            .setRoutePath("/mouse/left-click").build());
    }
}
