package com.aleksa.syndroid.library.application;

import com.aleksa.syndroid.library.sockets.WebSocket;
import com.aleksa.syndroid.library.sockets.WebSocketListener;

public class Application implements WebSocketListener
{
    private String    ip;
    private int       port;
    private WebSocket webSocket;

    public Application(String ip, int port)
    {
        this.ip = ip;
        this.port = port;

        this.webSocket = new WebSocket(this);
    }

    public void start()
    {
        this.webSocket.connect();
    }

    public String getIp()
    {
        return ip;
    }
}
