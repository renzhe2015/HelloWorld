package com.cifpay.cifpaylib.imservice;

import android.util.Log;

import com.cifpay.cifpaylib.utils.CifpayLog;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 消息服务类<br>主要使用MINA与服务端建立连接，然后进行数据的交互，
 * <br>在使用时 必须先调用 init方法来完成基本信息配置
 * <br>根据根据项目实际需要来监听socket连接判断进行不同的操作，如重连接或关闭等操作
 *
 * @author ZhangTing
 */
public class IMService {
    private IoConnector connector = null;

    private IoSession session = null;

    private String msg;// 发送的消息

    private ConnectServes connectserves;

    private ConnectFuture future;

    private static IMService instance;

    public static boolean isSocketConnent = false;

    private static String serverIp;
    private static int serverPort;

    private static IMServiceHandler messageServiceHandler;

    public static void init(IMServiceHandler messageHandler, String ip, int port) {
        messageServiceHandler = messageHandler;
        serverIp = ip;
        serverPort = port;
        getInstance();
    }

    public static void clear() {
        isSocketConnent = false;
        try {
            if (instance == null) {
                return;
            }
        } catch (Exception e) {
            CifpayLog.e("IMService", "instance clear--------" + e.toString());
        }
        try {
            if (null != instance.session) {
                instance.session.close(false);
            }
        } catch (Exception e) {
            CifpayLog.e("IMService", "session clear--------" + e.toString());
        }
        try {
            if (null != instance.future) {
                instance.future.cancel();
            }
        } catch (Exception e) {
            CifpayLog.e("IMService", "future clear--------" + e.toString());
        }
        try {
            if (null != instance.connector) {
                instance.connector.dispose();
            }
        } catch (Exception e) {
            CifpayLog.e("IMService", "connector clear--------" + e.toString());
        }
        try {
            if (null != instance.connectserves) {
                instance.connectserves = null;
            }
        } catch (Exception e) {
            CifpayLog.e("IMService", "connectserves clear--------" + e.toString());
        }
        try {
            if (null != instance) {
                instance = null;
            }
        } catch (Exception e) {
            CifpayLog.e("IMService", "instance clear--------" + e.toString());
        }
    }

    private IMService() {
        initConnector();
    }

    public synchronized static IMService getInstance() {
        if (null == instance) {
            instance = new IMService();

        }
        return instance;
    }

    public void initConnector() {
        ExecutorService executor = Executors.newFixedThreadPool(Runtime
                .getRuntime().availableProcessors());//
        connector = new NioSocketConnector(Runtime.getRuntime()
                .availableProcessors() + 1);
        connector.setConnectTimeoutMillis(30000);
        connector.getFilterChain().addLast("executor1",
                new ExecutorFilter(executor));
        connector.getFilterChain().addLast(
                "codec",
                new ProtocolCodecFilter(new TextLineCodecFactory(Charset
                        .forName("UTF-8"), LineDelimiter.DEFAULT.getValue(),
                        LineDelimiter.DEFAULT.getValue())));

        connector.setHandler(messageServiceHandler);
    }

    // 发送消息之前执行此方法
    public void beginConnect() {
        try {
            if (connectserves == null) {
                connectserves = new ConnectServes();
                connectserves.start();
            } else {
                if (null != future) {
                    session = future.getSession();
                }
            }

        } catch (Exception e) {
            CifpayLog.e("IMService", e);
            IMService.clear();
        }
    }

    class ConnectServes extends Thread {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            if (null == connector) {
                initConnector();
            }

            try {
                future = connector.connect(new InetSocketAddress(serverIp, serverPort));
                future.awaitUninterruptibly();
                session = future.getSession();

                if (msg != null && session != null) {
                    session.write(msg);// 连接成功发送消息
                }
            } catch (Exception e) {
                Log.e("IMService", e.toString());
                clear();
            }
        }
    }

    /**
     * 发送
     *
     * @param msg
     */
    public void send(String msg) {

        try {
            this.msg = msg;
            if (null == session) {
                beginConnect();// 发送消息时session为空时连接，并发送消息
            } else {
                session.write(msg);// 发送消息
            }
        } catch (Exception e) {
            Log.e("IMService", "send msg error : " + e.toString());
        }
    }

}
