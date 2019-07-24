package org.xo.demo.basic_jni;

import android.os.Bundle;
import android.widget.TextView;

import org.xo.demo.core.ui.BaseActivityWithToolBar;

public class BasicJniActivity extends BaseActivityWithToolBar {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_jni);

        // Example of a call to a native method
        TextView tv = findViewById(R.id.sample_text);
        String abi = ABIUtil.getABI(getPackageManager(), getPackageName());
        StringBuilder sb = new StringBuilder(stringFromJNI());
        sb.append("\nABI:").append(abi);
        tv.setText(sb);
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

}
