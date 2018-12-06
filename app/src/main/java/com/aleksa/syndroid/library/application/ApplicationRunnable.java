package com.aleksa.syndroid.library.application;

public class ApplicationRunnable implements Runnable
{

    private Application application;

    public ApplicationRunnable(String ip, int port)
    {
        this.application = new Application(ip, port);
    }

    @Override
    public void run()
    {
        application.start();
    }
}
