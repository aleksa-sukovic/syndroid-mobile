package com.aleksa.syndroid.library.application;

import android.support.annotation.Nullable;
import android.util.Log;

import com.aleksa.syndroid.library.bootstrap.Bootstrappable;
import com.aleksa.syndroid.library.bootstrap.Bootstrapper;
import com.aleksa.syndroid.library.events.ApplicationEvent;
import com.aleksa.syndroid.library.exceptions.BaseException;
import com.aleksa.syndroid.library.exceptions.ExceptionHandler;
import com.aleksa.syndroid.library.exceptions.SocketExceptionHandler;
import com.aleksa.syndroid.library.managers.SocketManager;
import com.aleksa.syndroid.library.router.Route;
import com.aleksa.syndroid.library.router.request.OutgoingRequest;
import com.aleksa.syndroid.library.sockets.WebSocket;
import com.aleksa.syndroid.library.sockets.WebSocketListener;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Application implements WebSocketListener, Bootstrappable
{
    private static final String      TAG = "Application";
    private static       Application instance;

    private String                                  ip;
    private int                                     port;
    private boolean                                 activeConnection;
    private Map<Bootstrapper.Data, Object>          serviceProviderData;
    private WebSocket                               webSocket;
    private SocketManager                           manager;
    private ExceptionHandler<BaseException, String> exceptionHandler;

    public static Application getInstance(String ip, int port)
    {
        if(instance == null) {
            instance = new Application(ip, port);
        }

        return instance;
    }

    public static @Nullable Application getInstance()
    {
        return instance;
    }

    private Application(String ip, int port)
    {
        bootstrapApplication();

        this.ip = ip;
        this.port = port;
        this.exceptionHandler = new SocketExceptionHandler();
    }

    public static int getDefaultPort()
    {
        return 3000;
    }

    private void bootstrapApplication()
    {
        this.serviceProviderData = new HashMap<>();
        Bootstrapper bootstrapper = new Bootstrapper(this);
        bootstrapper.bootstrap();
    }

    public void start()
    {
        if (activeConnection) {
            return;
        }

        this.webSocket = new WebSocket(ip, port, this);
        this.manager = new SocketManager((List<Route>) serviceProviderData.get(Bootstrapper.Data.ROUTES));

        this.webSocket.connect();

        this.activeConnection = true;
    }

    public void stop()
    {
        this.webSocket.disconnect();
    }

    public void sendMessage(String message)
    {
        this.webSocket.sendMessage(message);
    }

    public void send(OutgoingRequest request)
    {
        String message = manager.prepareOutgoingRequest(request);

        sendMessage(message);
    }

    @Override
    public void onOpen()
    {
        activeConnection = true;
        EventBus.getDefault().post(new ApplicationEvent(ApplicationEvent.EventCode.SERVER_CONNECT, "Successfully connected to server"));
    }

    @Override
    public void onMessage(String message)
    {
        Log.d(TAG, "onMessage: " + message);
        try
        {
            String response = manager.manage(message);

            if(response != null) {
                sendMessage(response);
            }
        }
        catch(BaseException e)
        {
            String exception = exceptionHandler.handle(e);
            sendMessage(exception);
        }
    }

    @Override
    public void onClosed()
    {
        activeConnection = false;
        Log.d(TAG, "onClosed: ");
        EventBus.getDefault().post(new ApplicationEvent(ApplicationEvent.EventCode.SERVER_DISCONNECT, "Connection with server closed successfully"));
    }

    @Override
    public void onFailure()
    {
        activeConnection = false;
        Log.d(TAG, "onFailure: application");
        EventBus.getDefault().post(new ApplicationEvent(ApplicationEvent.EventCode.SERVER_DISCONNECT, "Connection with server closed violently"));
    }

    @Override
    public Map<Bootstrapper.Data, Object> serviceProviderData()
    {
        return this.serviceProviderData;
    }
}
