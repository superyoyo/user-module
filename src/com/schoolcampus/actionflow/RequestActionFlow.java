package com.schoolcampus.actionflow;

import com.schoolcampus.callback.ICallBack;
import com.schoolcampus.filter.actionflow.IActionFlow;
import com.schoolcampus.logic.ILoginLogic;
import com.schoolcampus.logic.IRegisteLogic;
import com.schoolcampus.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

public class RequestActionFlow implements IActionFlow {
    private ILoginLogic loginLogic;
    private IRegisteLogic registeLogic;

    public RequestActionFlow() {
        registeLogic = new IRegisteLogic() {
            @Override
            public void registeByPhone(String phone, String code, String password, String name, String head, ICallBack callBack) throws IOException {
                User user = new User();
                user.setName("Williams");
                callBack.done(0, "", null, user);
            }

            @Override
            public void registeByEmail(String email, String code, String password, String name, String head, ICallBack callBack) {

            }
        };
    }

    public void dealRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String action = request.getParameter("action");
        if(action == null){
            Writer writer = response.getWriter();
            writer.write("缺少action");
            writer.flush();
            writer.close();
            return;
        }
        if(action.equals("registeByPhone")){
            String phone = request.getParameter("phone");
            String code = request.getParameter("code");
            String password = request.getParameter("password");
            String name = request.getParameter("name");
            String head = request.getParameter("head");

            registeLogic.registeByPhone(phone, code, password, name, head, new ICallBack<User>() {
                @Override
                public void done(int code, String message, Exception e, User user) throws IOException {
                    Writer writer = response.getWriter();
                    if(code == 0 && e == null){
                        writer.write("user:" + user.getName());
                    }else{
                        writer.write("error:" + message);
                    }
                    writer.flush();
                    writer.close();
                }
            });

        }else if(action.equals("registeByEmail")){
            
        }else{

        }
    }
}
