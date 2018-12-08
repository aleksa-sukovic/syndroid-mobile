package com.aleksa.syndroid.library.sockets;

public interface WebSocketListener
{
    void onOpen();
    void onMessage(String message);
    void onClosed();
    void onFailure();
}
