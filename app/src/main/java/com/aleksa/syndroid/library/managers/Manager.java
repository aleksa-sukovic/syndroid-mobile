package com.aleksa.syndroid.library.managers;

import android.support.annotation.Nullable;

import com.aleksa.syndroid.library.exceptions.BaseException;

public interface Manager<Input, Output>
{
    @Nullable Output manage(Input input) throws BaseException;
}
