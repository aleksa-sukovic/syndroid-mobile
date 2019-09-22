package com.aleksa.syndroid.library.sockets;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WebSocket extends okhttp3.WebSocketListener
{
    public static final String TAG = "WebSocket";

    private WebSocketListener listener;
    private OkHttpClient      okHttpClient;
    private Request           request;
    private okhttp3.WebSocket client;
    private String            ip;
    private int               port;

    public WebSocket(String ip, int port, WebSocketListener listener)
    {
        this.listener = listener;
        this.okHttpClient = new OkHttpClient();
        this.port = port;
        this.ip = ip;

        request = new Request.Builder().url("ws://" + ip + ":" + port).build();
    }

    public void connect()
    {
        if (client != null) {
            return;
        }

        client = okHttpClient.newWebSocket(request, this);
    }

    public void disconnect()
    {
        if (client != null) {
            client.cancel();
        }
    }

    public void sendMessage(String message)
    {
        if (client == null) {
            return;
        }

        client.send(message);
    }

    @Override
    public void onOpen(okhttp3.WebSocket webSocket, Response response)
    {
        listener.onOpen();
    }

    @Override
    public void onMessage(okhttp3.WebSocket webSocket, String text)
    {
        listener.onMessage(text);
    }

    @Override
    public void onClosed(okhttp3.WebSocket webSocket, int code, String reason)
    {
        client = null;
        listener.onClosed();
    }

    @Override
    public void onFailure(okhttp3.WebSocket webSocket, Throwable t, Response response)
    {
        client = null;
        listener.onFailure();
    }
}
