package com.loopj.android.http;

import org.apache.http.Header;

/**
 * Created by cifpay on 2015/6/27.
 */
public interface HandlerListener {

    /**
     * Fired when a request returns successfully, override to handle in your own code
     *
     * @param statusCode   the status code of the response
     * @param headers      return headers, if any
     * @param responseBody the body of the HTTP response from the server
     */
    void onSuccess(int statusCode, Header[] headers, byte[] responseBody);

    /**
     * Fired when a request fails to complete, override to handle in your own code
     *
     * @param statusCode   return HTTP status code
     * @param headers      return headers, if any
     * @param responseBody the response body, if any
     * @param error        the underlying cause of the failure
     */
    void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error);

    void onStart();
    void onFinish();
    void onCancel();
}
