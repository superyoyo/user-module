package com.schoolcampus.interceptor;

import com.schoolcampus.filter.interceptor.IInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

public class TokenInterceptor implements IInterceptor{
    @Override
    public boolean filter(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(request.getHeader("token") != null || request.getParameter("token") != null){
            return true;
        }
        Writer writer = response.getWriter();
        writer.write("缺少token");
        writer.flush();
        writer.close();
        return false;
    }
}
