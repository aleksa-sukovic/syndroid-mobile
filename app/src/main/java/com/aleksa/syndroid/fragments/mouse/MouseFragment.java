package com.aleksa.syndroid.fragments.mouse;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aleksa.syndroid.R;
import com.aleksa.syndroid.fragments.mouse.mouse_pad.MousePad;
import com.aleksa.syndroid.fragments.mouse.mouse_pad.MousePadListener;
import com.aleksa.syndroid.library.events.Event;
import com.aleksa.syndroid.library.router.request.Request;

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
        initializeMouseButtons(view);

        return view;
    }

    private void initializeMousePad(View view)
    {
        mousePad = view.findViewById(R.id.mouse_pad);
        mousePad.setMousePadListener(this);
    }

    private void initializeMouseButtons(View view)
    {
        view.findViewById(R.id.mouse_left_click).setOnClickListener(v -> onLeftClick());
        view.findViewById(R.id.mouse_right_click).setOnClickListener(v -> onRightClick());
    }

    @Override
    public void onMove(float xOffset, float yOffset)
    {
        EventBus.getDefault().post(new Request.Builder()
            .setRouteByPath("/mouse/move")
            .addParam("x", Float.toString(xOffset))
            .addParam("y", Float.toString(yOffset))
            .setType("request")
            .expectResponse(false)
            .autoincrement()
            .build());
    }

    @Override
    public void onDragStart()
    {
        EventBus.getDefault().post(new Request.Builder()
            .setRouteByPath("/mouse/drag/start")
            .setType("request")
            .autoincrement()
            .build());
    }

    @Override
    public void onDragEnd()
    {
        EventBus.getDefault().post(new Request.Builder()
            .setRouteByPath("/mouse/drag/end")
            .setType("request")
            .autoincrement()
            .build());
    }

    @Override
    public void onLeftClick()
    {
        EventBus.getDefault().post(new Request.Builder()
            .setRouteByPath("/mouse/left-click")
            .setType("request")
            .autoincrement()
            .build());
    }

    @Override
    public void onRightClick()
    {
        EventBus.getDefault().post(new Request.Builder()
            .setRouteByPath("/mouse/right-click")
            .setType("request")
            .autoincrement()
            .build());
    }

    @Override
    public void onScroll(float yOffset)
    {
        EventBus.getDefault().post(new Request.Builder()
            .setRouteByPath("/mouse/scroll")
            .setType("request")
            .autoincrement()
            .addParam("amount", Float.toString(yOffset))
            .build());
    }

    @Override
    public void onDoubleTap()
    {
        EventBus.getDefault().post(new Request.Builder()
            .setRouteByPath("/mouse/double-left-click")
            .setType("request")
            .autoincrement()
            .build());
    }
}
