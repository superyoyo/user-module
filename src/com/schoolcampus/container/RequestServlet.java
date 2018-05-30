package com.schoolcampus.container;

import com.schoolcampus.actionflow.RequestActionFlow;
import com.schoolcampus.filter.manager.FilterManager;
import com.schoolcampus.handler.HandlerThread;
import com.schoolcampus.interceptor.TokenInterceptor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RequestServlet")
public class RequestServlet extends HttpServlet {
    private HandlerThread handlerThread;
    @Override
    public void init() throws ServletException {
        super.init();
        handlerThread = new HandlerThread("work");
        handlerThread.start();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        FilterManager filterManager = new FilterManager(request, response);
        filterManager
                .addInterceptor(new TokenInterceptor())//检查token是否生效
                .addActionFlow(RequestActionFlow.getInstance(handlerThread.getLooper()))//检查
                .work();
    }
}
