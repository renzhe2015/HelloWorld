package com.cifpay.cifpaylib.http;

import android.content.Context;

import com.cifpay.cifpaylib.utils.CifpayLog;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.HandlerListener;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;

import java.io.IOException;
import java.security.KeyStore;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;

/**
 * @author ZhangTing 网络异步处理HTTPs请求 <br>
 *         <br>支持HTTP，HTTPS 请求
 *         默认所有HTTPS请求都通过，不进行证书认证。
 */
public abstract class AsyncHttpsUtil implements HandlerListener {
    /**
     * 支持HTTP,HTTPS 请求
     */
    public static AsyncHttpClient httpsClient;
    private static final String TAG = "AsyncHttpsUtil";

    /**
     * 初始化方法，在使用之前需要先加载证书，在后面的证书认证时会使用到
     *
     * @param keyStore KeyStore
     */
    public static void init(KeyStore keyStore) {
        SchemeRegistry schemeRegistry = getSchemeRegistry(80, 443, keyStore);
        httpsClient = new AsyncHttpClient(schemeRegistry);
    }

    /**
     * 注册HTTPS 认证
     *
     * @param httpPort   http 请求注册端口
     * @param httpsPort  https 请求注册端口
     * @param trustStore KeyStore
     * @return SchemeRegistry
     */
    private static SchemeRegistry getSchemeRegistry(int httpPort, int httpsPort, KeyStore trustStore) {

        if (httpPort < 1) {
            httpPort = 80;
            CifpayLog.d(TAG, "Invalid HTTP port number specified, defaulting to 80");
        }

        if (httpsPort < 1) {
            httpsPort = 443;
            CifpayLog.d(TAG, "Invalid HTTPS port number specified, defaulting to 443");
        }

        SchemeRegistry schemeRegistry = new SchemeRegistry();
        try {
            SSLSocketFactory sslSocketFactory = new CSSSLSocketFactory(trustStore);
            /***
             * 添加host过滤，默认所有HTTPS请求都通过
             */
            sslSocketFactory.setHostnameVerifier(new X509HostnameVerifier() {
                @Override
                public boolean verify(String s, SSLSession sslSession) {
                    return true;
                }

                @Override
                public void verify(String s, SSLSocket sslSocket) throws IOException {

                }

                @Override
                public void verify(String s, X509Certificate x509Certificate) throws SSLException {

                }

                @Override
                public void verify(String s, String[] strings, String[] strings1) throws SSLException {

                }
            });  //允许所有主机

            schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), httpPort));
            schemeRegistry.register(new Scheme("https", sslSocketFactory, httpsPort));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return schemeRegistry;

    }

    /**
     * 监听所有的网络请求反馈信息
     */
    private static HandlerListener handlerListener = new HandlerListener() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            //请求成功

        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            //请求失败

        }

        @Override
        public void onStart() {
            //开始请求

        }

        @Override
        public void onFinish() {
            //请求结束

        }

        @Override
        public void onCancel() {
            //请求取消

        }
    };

    /**
     * http or https  GET 请求
     *
     * @param url             请求URL
     * @param responseHandler 回调处理 handler
     */
    public static void get(String url, AsyncHttpResponseHandler responseHandler) {
        httpsClient.get(url, responseHandler.setHandlerListener(handlerListener));
    }

    /**
     * HTTP OR HTTPS GET 请求
     *
     * @param url             请求URL
     * @param requestParams   请求参数
     * @param responseHandler 回调处理handler
     */
    public static void get(String url, RequestParams requestParams, ResponseHandlerInterface responseHandler) {
        httpsClient.get(url, requestParams, responseHandler.setHandlerListener(handlerListener));
    }

    /**
     * HTTP OR HTTPS GET 请求
     *
     * @param context         Context
     * @param url             请求URL
     * @param requestParams   请求参数
     * @param responseHandler 回调处理handler
     */
    public static void get(Context context, String url, RequestParams requestParams, ResponseHandlerInterface responseHandler) {
        httpsClient.get(context, url, requestParams, responseHandler.setHandlerListener(handlerListener));
    }

    /**
     * HTTP OR HTTPS POST 请求
     *
     * @param url             请求URL
     * @param responseHandler 回调处理handler
     */
    public static void post(String url, AsyncHttpResponseHandler responseHandler) {
        httpsClient.post(url, responseHandler.setHandlerListener(handlerListener));
    }

    /**
     * HTTP OR HTTPS post 请求
     *
     * @param context
     * @param url             请求URL
     * @param requestParams   请求参数
     * @param responseHandler 回调处理handler
     */
    public static void post(Context context, String url, RequestParams requestParams,
                            AsyncHttpResponseHandler responseHandler) {
        httpsClient.post(context, url, requestParams, responseHandler.setHandlerListener(handlerListener));
    }

    /**
     * HTTP OR HTTPS post 请求
     *
     * @param url             请求URL
     * @param requestParams   请求参数
     * @param responseHandler 回调处理handler
     */
    public static void post(String url, RequestParams requestParams, AsyncHttpResponseHandler responseHandler) {
        httpsClient.post(url, requestParams, responseHandler.setHandlerListener(handlerListener));
    }

    /**
     * 设置请求头
     */
    public static void post(Context context, String url, HttpEntity entity, String contenType, AsyncHttpResponseHandler responseHandler) {
        httpsClient.post(context, url, entity, contenType, responseHandler);
    }
}
