package com.aleksa.syndroid.library.database.promises;

public class Promise
{
    private PromiseResult promiseResult;

    public void resolve(Object object)
    {
        if (promiseResult != null) {
            promiseResult.then(object);
        }
    }

    public void then(PromiseResult promiseResult)
    {
        this.promiseResult = promiseResult;
    }

    public interface PromiseResult
    {
        void then(Object object);
    }
}
