package com.aleksa.syndroid.library.server;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketFactory;

import java.io.IOException;

public class WebSocketServer extends WebSocketAdapter
{
    private WebSocketFactory webSocketFactory;
    private WebSocket webSocket;
    private WebSocketServerListener listener;

    public WebSocketServer(WebSocketServerListener listener) {
        webSocketFactory = new WebSocketFactory();
        this.listener = listener;
    }

    public void createServer(String ip) throws IOException {
        this.webSocket = webSocketFactory.createSocket("ws://" + ip);
    }

    public void connect() throws Exception {
        this.webSocket.connect();
    }

}
