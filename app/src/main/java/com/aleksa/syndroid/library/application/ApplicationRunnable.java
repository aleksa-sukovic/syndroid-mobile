package com.aleksa.syndroid.library.application;

public class ApplicationRunnable implements Runnable
{

    private String ip;
    private Application application;

    public ApplicationRunnable(String ip) {
        this.application = new Application(ip);
    }

    @Override
    public void run()
    {
        application.start();
    }
}
