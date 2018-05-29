package com.schoolcampus.filter.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface IInterceptor {
    public boolean filter(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
