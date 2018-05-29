package com.schoolcampus.logic;

import com.schoolcampus.callback.ICallBack;

public interface ILoginLogic {
    public void loginByPhone(String phone, String password, ICallBack callBack);

    public void loginByEmail(String email, String password, ICallBack callBack);
}
