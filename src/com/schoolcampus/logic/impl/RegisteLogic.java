package com.schoolcampus.logic.impl;

import com.schoolcampus.logic.IRegisteLogic;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

public class RegisteLogic implements IRegisteLogic{
    public static IRegisteLogic instance;
    public static IRegisteLogic getInstance(){
        if(instance == null){
            synchronized (RegisteLogic.class){
                if(instance == null){
                    instance = new RegisteLogic();
                }
            }
        }

        return instance;
    }

    private RegisteLogic() {}

    @Override
    public void registeByPhone(String phone, String password, String name, String head, HttpServletResponse response) {

        try {
            Writer writer = response.getWriter();
            writer.write("hello Handler!!! phone:" + phone);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void registeByEmail(String email, String password, String name, String head, HttpServletResponse response) {

        try {
            Writer writer = response.getWriter();
            writer.write("hello Handler!!! email:" + email);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
