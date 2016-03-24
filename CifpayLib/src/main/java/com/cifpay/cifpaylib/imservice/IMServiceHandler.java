package com.cifpay.cifpaylib.imservice;

import com.cifpay.cifpaylib.utils.CifpayLog;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;


/**
 * 消息处理handler<br>在使用时需要重写  receiveMessage方法，用于处理服务端发送到客户的消息
 */
public abstract class IMServiceHandler extends IoHandlerAdapter {

    private static final String TAG = "MessageServiceHandler";

    @Override
    // 客服端连接
    public void sessionOpened(IoSession session) throws Exception {
        CifpayLog.d(TAG, "客户端连接成功....");

    }

    @Override
    public void messageReceived(IoSession session, Object message) {
        if (null != message) {
            String msg = message.toString();
            // 当接收到消息以后发送广播
            messageReceived(msg);
        }
    }

    /***
     * 在子类中重写该方法来处理服务端所接收到的消息信息
     * @param msg <br>服务端所发送到客户端的消息内容
     */
    public abstract void messageReceived(String msg);

    @Override
    public void sessionClosed(IoSession session) {
        CifpayLog.d(TAG, "客户端关闭....");
        IMService.clear();
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause)
            throws Exception {
        CifpayLog.d(TAG, "客户端发生异常..." + cause);
        IMService.clear();
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        // TODO Auto-generated method stub
        super.messageSent(session, message);
        CifpayLog.d(TAG, "the message send is：" + message);
    }
}
