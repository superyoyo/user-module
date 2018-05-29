package com.schoolcampus.filter.actionflow;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface IActionFlow {
    public void dealRequest(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
