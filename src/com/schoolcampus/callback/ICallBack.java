package com.schoolcampus.callback;

import java.io.IOException;

public interface ICallBack<T> {
    public void done(int code, String message, Exception e, T t) throws IOException;
}
