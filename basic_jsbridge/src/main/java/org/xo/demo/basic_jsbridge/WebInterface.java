package org.xo.demo.basic_jsbridge;

import android.webkit.JavascriptInterface;

public class WebInterface {

    interface JavascriptReceiver {
        String onReceived(final String data);
        String onReceived(final String data, final String callbackId);
    }

    private JavascriptReceiver mReceiver;

    public void setJavascriptReceiver(JavascriptReceiver receiver) {
        mReceiver = receiver;
    }

    @JavascriptInterface
    public String send(String data) {
        return mReceiver == null ? "" : mReceiver.onReceived(data);
    }

    @JavascriptInterface
    public String sendAsync(String data, String callbackId) {
        return mReceiver == null ? "" : mReceiver.onReceived(data, callbackId);
    }
}
