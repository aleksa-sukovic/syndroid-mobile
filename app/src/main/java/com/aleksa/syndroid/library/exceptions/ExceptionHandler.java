package com.aleksa.syndroid.library.exceptions;

public interface ExceptionHandler<Input, Output>
{
    Output handle(Input input);
}
