package com.schoolcampus.logic;

import com.schoolcampus.callback.ICallBack;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface IRegisteLogic {

    public void registeByPhone(String phone, String password, String name, String head, HttpServletResponse response);

    public void registeByEmail(String email, String password, String name, String head, HttpServletResponse response);
}
