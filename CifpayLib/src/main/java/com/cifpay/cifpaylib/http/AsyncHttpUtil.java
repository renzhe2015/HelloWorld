package com.cifpay.cifpaylib.http;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.HandlerListener;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

import org.apache.http.Header;

/**
 * @author kun 网络异步处理
 */
public abstract class AsyncHttpUtil implements HandlerListener {
    public static AsyncHttpClient client = new AsyncHttpClient();

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

    public static void get(String url, AsyncHttpResponseHandler responseHandler) {
        client.get(url, responseHandler.setHandlerListener(handlerListener));
    }

    public static void get(String url, RequestParams requestParams, ResponseHandlerInterface responseHandler) {
        client.get(url, requestParams, responseHandler.setHandlerListener(handlerListener));
    }

    public static void get(Context context, String url, RequestParams requestParams, ResponseHandlerInterface responseHandler) {
        client.get(context, url, requestParams, responseHandler.setHandlerListener(handlerListener));
    }

    public static void post(String url, AsyncHttpResponseHandler responseHandler) {
        client.post(url, responseHandler.setHandlerListener(handlerListener));
    }

    public static void post(Context context, String url,
                            RequestParams requestParams,
                            AsyncHttpResponseHandler responseHandler) {
        client.post(context, url, requestParams, responseHandler.setHandlerListener(handlerListener));
    }

    public static void post(String url, RequestParams requestParams,
                            AsyncHttpResponseHandler responseHandler) {
        client.post(url, requestParams, responseHandler.setHandlerListener(handlerListener));
    }
}
