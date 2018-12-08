package com.aleksa.syndroid.library.application;

import android.support.annotation.Nullable;
import android.util.Log;

import com.aleksa.syndroid.library.sockets.WebSocket;
import com.aleksa.syndroid.library.sockets.WebSocketListener;

public class Application implements WebSocketListener
{
    private static final String TAG = "Application";
    private static Application instance;

    private String ip;
    private int port;
    private WebSocket webSocket;

    public static Application getInstance(String ip, int port) {
        if (instance == null) {
            instance = new Application(ip, port);
        }

        return instance;
    }

    public static @Nullable Application getInstance() {
        return instance;
    }

    private Application(String ip, int port)
    {
        this.ip = ip;
        this.port = port;

        this.webSocket = new WebSocket(ip, port,this);
    }

    public void start()
    {
        this.webSocket.connect();
    }

    public void stop()
    {
        this.webSocket.disconnect();
    }

    public void sendMessage(String message)
    {
        this.webSocket.sendMessage(message);
    }

    @Override
    public void onOpen()
    {
        Log.d(TAG, "onOpen: ");
    }

    @Override
    public void onMessage(String message)
    {
        Log.d(TAG, "onMessage: " + message);
    }

    @Override
    public void onClosed()
    {
        Log.d(TAG, "onClosed: ");
    }

    @Override
    public void onFailure()
    {
        Log.d(TAG, "onFailure: ");
    }
}
