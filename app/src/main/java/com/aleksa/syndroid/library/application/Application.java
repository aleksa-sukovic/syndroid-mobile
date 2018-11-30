package com.aleksa.syndroid.library.application;

import com.aleksa.syndroid.library.events.ApplicationEvent;
import com.aleksa.syndroid.library.server.WebSocketServer;
import com.aleksa.syndroid.library.server.WebSocketServerListener;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

public class Application implements WebSocketServerListener
{
    private String ip;
    private WebSocketServer webSocketServer;

    public Application(String ip) {
        this.ip = ip;

        initializeServer();
    }

    private void initializeServer() {
        this.webSocketServer = new WebSocketServer(this);

        try {
            this.webSocketServer.createServer(ip);
        } catch(IOException e) {
            EventBus.getDefault()
                .post(new ApplicationEvent(ApplicationEvent.EventCode.SERVER_CREATION_ERROR, "Unable to create server!"));
        }
    }

    public void start() {
        try {
            this.webSocketServer.connect();
        } catch(Exception e) {
            EventBus.getDefault()
                    .post(new ApplicationEvent(ApplicationEvent.EventCode.SERVER_CONNECTION_ERROR, "Unable to connect to server!"));
        }
    }

    public String getIp()
    {
        return ip;
    }

    public void setIp(String ip)
    {
        this.ip = ip;
    }

    public WebSocketServer getWebSocketServer()
    {
        return webSocketServer;
    }

    public void setWebSocketServer(WebSocketServer webSocketServer)
    {
        this.webSocketServer = webSocketServer;
    }
}
