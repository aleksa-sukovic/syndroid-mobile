package com.aleksa.syndroid.library.application;

import android.content.Context;
import com.aleksa.syndroid.library.events.ApplicationEvent;
import com.aleksa.syndroid.library.exceptions.BaseException;
import com.aleksa.syndroid.library.exceptions.ExceptionHandler;
import com.aleksa.syndroid.library.exceptions.SocketExceptionHandler;
import com.aleksa.syndroid.library.router.Router;
import com.aleksa.syndroid.library.router.request.Request;
import com.aleksa.syndroid.library.sockets.WebSocket;
import com.aleksa.syndroid.library.sockets.WebSocketListener;
import com.aleksa.syndroid.objects.battery.BatteryServiceProvider;
import com.aleksa.syndroid.objects.file.FileServiceProvider;
import com.aleksa.syndroid.objects.utils.UtilsServiceProvider;
import org.greenrobot.eventbus.EventBus;
import java.util.Arrays;
import java.util.List;

public class Application implements WebSocketListener
{
    public static final int PORT = 3000;

    private int port;
    private String ip;
    private Router router;
    private WebSocket webSocket;
    private boolean activeConnection;
    private ExceptionHandler<BaseException, String> exceptionHandler;

    public static Application getInstance(Context context, String ip, int port)
    {
        return new Application(context, ip, port);
    }

    private Application(Context context, String ip, int port)
    {
        // bootstrap providers
        Bootstrapper.Bootstrapped data = (new Bootstrapper(getProviders())).bootstrap();

        // initialize application
        this.ip = ip;
        this.port = port;
        this.exceptionHandler = new SocketExceptionHandler();
        this.router = new Router(context, data.routes);
    }

    public void start()
    {
        if (activeConnection) {
            return;
        }

        this.webSocket = new WebSocket(ip, port, this);
        this.webSocket.connect();
        this.activeConnection = true;
    }

    public void stop()
    {
        if (router != null) {
            router.stop();
        }

        if (this.webSocket != null) {
            this.webSocket.disconnect();
        }
    }

    public void send(Request request, Router.ResponseCallback listener)
    {
        sendMessage(router.sendRequest(request, listener).toString());
    }

    private void sendMessage(String message)
    {
        this.webSocket.sendMessage(message);
    }

    @Override
    public void onOpen()
    {
        activeConnection = true;
        EventBus.getDefault().post(
            new ApplicationEvent(ApplicationEvent.EventCode.SERVER_CONNECT, "Successfully connected to server")
        );
    }

    @Override
    public void onMessage(String message)
    {
        try {
            String response = router.handle(message);

            if (response != null) {
                sendMessage(response);
            }
        } catch (BaseException e) {
            String exception = exceptionHandler.handle(e);
            sendMessage(exception);
        }
    }

    @Override
    public void onClosed()
    {
        activeConnection = false;
        EventBus.getDefault().post(
            new ApplicationEvent(ApplicationEvent.EventCode.SERVER_DISCONNECT, "Disconnected")
        );
    }

    @Override
    public void onFailure()
    {
        activeConnection = false;
        EventBus.getDefault().post(
            new ApplicationEvent(ApplicationEvent.EventCode.SERVER_DISCONNECT, "Disconnected")
        );
    }

    private List<Class<?>> getProviders()
    {
        return Arrays.asList(
            FileServiceProvider.class,
            BatteryServiceProvider.class,
            UtilsServiceProvider.class
        );
    }
}
