package com.schoolcampus.logic;

import com.schoolcampus.callback.ICallBack;

import java.io.IOException;

public interface IRegisteLogic {

    public void registeByPhone(String phone, String code, String password, String name, String head, ICallBack callBack) throws IOException;

    public void registeByEmail(String email, String code, String password, String name, String head, ICallBack callBack);
}
