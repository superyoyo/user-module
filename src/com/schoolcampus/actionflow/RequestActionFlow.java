package com.schoolcampus.actionflow;

import com.schoolcampus.filter.actionflow.IActionFlow;
import com.schoolcampus.handler.Handler;
import com.schoolcampus.handler.Looper;
import com.schoolcampus.handler.Message;
import com.schoolcampus.logic.ILoginLogic;
import com.schoolcampus.logic.IRegisteLogic;
import com.schoolcampus.logic.LogicResponse;
import com.schoolcampus.logic.impl.RegisteLogic;
import com.schoolcampus.model.User;
import com.schoolcampus.util.NetworkUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

public class RequestActionFlow implements IActionFlow {
    private ILoginLogic mLoginLogic;
    private IRegisteLogic mRegisteLogic;
    private Handler mWorkhandler;
    private Looper mLooper;

    private static RequestActionFlow instance;

    public static RequestActionFlow getInstance(Looper mLooper){
        if(instance == null){
            synchronized (RequestActionFlow.class){
                if(instance == null){
                    instance = new RequestActionFlow(mLooper);
                }
            }
        }

        return instance;
    }

    private RequestActionFlow(Looper mLooper) {
        this.mLooper = mLooper;
        mRegisteLogic = RegisteLogic.getInstance();
        initWorkHandler();
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
            String password = request.getParameter("password");
            String name = request.getParameter("name");
            String head = request.getParameter("head");

            int what = (NetworkUtil.getIpAddress(request) + action).hashCode();
            mWorkhandler.removeMessages(what);
            Message msg = Message.obtain();
            msg.what = what;
            msg.put("phone", phone);
            msg.put("password", password);
            msg.put("name", name);
            msg.put("head", head);
            msg.put("response", response);
            mWorkhandler.sendMessageDelayed(msg, 100);

        }else if(action.equals("registeByEmail")){
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String name = request.getParameter("name");
            String head = request.getParameter("head");

            int what = (NetworkUtil.getIpAddress(request) + action).hashCode();
            mWorkhandler.removeMessages(what);
            Message msg = Message.obtain();
            msg.what = what;
            msg.put("email", email);
            msg.put("password", password);
            msg.put("name", name);
            msg.put("head", head);
            msg.put("response", response);
            mWorkhandler.sendMessageDelayed(msg, 100);
        }else{

        }
    }

    private void initWorkHandler(){
        mWorkhandler = new Handler(mLooper){
            @Override
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                switch (msg.what){
                    case 0:
                        mRegisteLogic.registeByPhone(msg.getString("phone", "")
                                , msg.getString("password", "")
                                , msg.getString("name", "")
                                , msg.getString("head", "")
                                , (HttpServletResponse) msg.get("response"));
                        break;
                    case 1:
                        mRegisteLogic.registeByEmail(msg.getString("email", "")
                                , msg.getString("password", "")
                                , msg.getString("name", "")
                                , msg.getString("head", "")
                                , (HttpServletResponse) msg.get("response"));
                        break;
                }
            }
        };
    }
}
