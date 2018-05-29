package com.schoolcampus.filter.manager;

import com.schoolcampus.filter.actionflow.IActionFlow;
import com.schoolcampus.filter.interceptor.IInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FilterManager {
    private List<IInterceptor> interceptors;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private IActionFlow actionFlow;

    public FilterManager(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.interceptors = new ArrayList<>();
    }

    public FilterManager addInterceptor(IInterceptor iInterceptor){
        interceptors.add(iInterceptor);
        return this;
    }

    public FilterManager addActionFlow(IActionFlow actionFlow){
        this.actionFlow = actionFlow;
        return this;
    }

    public void work() throws IOException {
        boolean intercept = false;
        for (int i = 0, n = interceptors.size(); i < n; i++){
            IInterceptor interceptor = interceptors.get(i);
            if(interceptor.filter(request, response)){
                continue;
            }else{
                intercept = true;
                break;
            }
        }

        if(!intercept){
            actionFlow.dealRequest(request, response);
        }
    }
}
