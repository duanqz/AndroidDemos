package org.xo.demo.basic_jsbridge;

import android.os.Bundle;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;

import org.xo.demo.core.JsonResponse;
import org.xo.demo.core.ui.BaseActivityWithToolBar;

public class BasicJsBridgeActivity extends BaseActivityWithToolBar {

    private static final String BRIDGE_NAME = "bridge";

    private WebInterface mWebInterface;
    private WebView mWebView;

    private View mAndroidView;
    private EditText mEditText;
    private Button mButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jsbridge);

        setupWebView();
        setupAndroidView();
    }

    private void setupWebView() {
        mWebInterface = new WebInterface();
        mWebInterface.setJavascriptReceiver(new DefaultJavascriptReceiver());

        mWebView = findViewById(R.id.web_view);
        mWebView.addJavascriptInterface(mWebInterface, BRIDGE_NAME);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl("file:///android_asset/index.html");
    }

    private void setupAndroidView() {
        mAndroidView = findViewById(R.id.android_view);
        mEditText = findViewById(R.id.edit_text_to_web);
        mButton = findViewById(R.id.btn_send_to_web);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JavascriptExecutor.builder()
                        .function("onReceived")
                        .params(new String[]{mEditText.getText().toString()})
                        .valueCallback(new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String value) {
                                // Handle returned value from javascript
                                info(value);
                            }
                        })
                        .build().fireOn(mWebView);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        teardownWebView();
    }

    private void teardownWebView() {
        mWebView.removeJavascriptInterface(BRIDGE_NAME);
    }

    private class DefaultJavascriptReceiver implements WebInterface.JavascriptReceiver {
        @Override
        public String onReceived(final String message) {
            Snackbar.make(mAndroidView, message, Snackbar.LENGTH_LONG).show();

            return JsonResponse.success();
        }

        @Override
        public String onReceived(final String message, final String callbackId) {
            StringBuilder sb = new StringBuilder(message).append(" with ").append(callbackId);
            Snackbar.make(mAndroidView, sb, Snackbar.LENGTH_LONG).show();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JavascriptExecutor.builder()
                            .function("onAsyncCallback")
                            .params(new String[]{callbackId, "Well, callback now"})
                            .build().fireOn(mWebView);
                }
            });

            return JsonResponse.success();
        }
    }
}
