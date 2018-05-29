package com.schoolcampus.logic;

import com.schoolcampus.callback.ICallBack;

public interface IPhoneCodeLogic {
    public void sendPhoneCode(String phone, String code, long timeout, ICallBack callBack);

    public boolean checkPhoneCode(String phone, String code, ICallBack callBack);
}
