package com.aleksa.syndroid.library.exceptions;

public class SocketExceptionHandler implements ExceptionHandler<BaseException, String>
{
    @Override
    public String handle(BaseException s)
    {
        return s.render().toString();
    }
}
