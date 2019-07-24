package org.xo.demo.basic_jsbridge;

import android.webkit.ValueCallback;
import android.webkit.WebView;

import lombok.Builder;
import lombok.NonNull;

@Builder
public class JavascriptExecutor {

    /** Javascript function name */
    private String function;

    /** Javascript function parameters */
    private String[] params;

    /** Javascript function returned value callback */
    private ValueCallback valueCallback;

    /**
     * Fire on the specific web view.
     * Must run on UI thread
     * @param webView
     */
    public void fireOn(@NonNull WebView webView) {
        webView.evaluateJavascript(buildJavascript(), valueCallback);
    }

    /**
     * Build javascript string
     * @return Formatted string like: "javascript: function(param1, param2, ...)"
     */
    protected String buildJavascript() {
        StringBuilder script = new StringBuilder("javascript: ");
        script.append(function).append("(");
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                script.append("\"").append(params[i]).append("\"");
                if (i != params.length - 1) {
                    script.append(",");
                }
            }
        }
        script.append(")");

        return script.toString();
    }
}
