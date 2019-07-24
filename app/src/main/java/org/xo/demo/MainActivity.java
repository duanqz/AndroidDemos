package org.xo.demo;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ProgressBar mProgressBar;
    private RecyclerView mRecyclerView;
    private DemoListAdapter mDemoListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDemoListAdapter = new DemoListAdapter(this);

        mProgressBar = findViewById(R.id.progress_bar);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setAdapter(mDemoListAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(
                new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
    }

    @Override
    protected void onStart() {
        super.onStart();
        new DemoListRetriever().execute();
    }

    private class DemoListRetriever extends AsyncTask<Object, Void, List<Demo>> {

        @Override
        protected List<Demo> doInBackground(Object[] objects) {
            return getDemoList();
        }

        @Override
        protected void onPostExecute(List<Demo> list) {
            mProgressBar.setVisibility(View.GONE);
            mDemoListAdapter.setDemoList(list);
        }

        private List getDemoList() {
            List<Demo> demoList = new ArrayList();

            Intent queryIntent = new Intent();
            queryIntent.setAction(Intent.ACTION_MAIN);
            queryIntent.addCategory("org.xo.demo.intent.category.SAMPLE_CODE");

            // Query activities by the specific intent
            PackageManager pm = getPackageManager();
            List<ResolveInfo> list = pm.queryIntentActivities(queryIntent, 0);
            if (list == null) {
                return demoList;
            }

            // Parse into result list
            for (ResolveInfo info : list) {
                Demo demo = new Demo();
                demo.mTitle = getTitle(info);
                demo.mDescription = getString(info.activityInfo.descriptionRes);
                demo.mIntent = createActivityIntent(
                        info.activityInfo.applicationInfo.packageName, info.activityInfo.name);

                demoList.add(demo);
            }

            return demoList;
        }

        private String getTitle(ResolveInfo info) {
            String className = info.activityInfo.name;
            return className.substring(className.lastIndexOf(".") + 1);
        }

        private Intent createActivityIntent(String packageName, String className) {
            Intent intent = new Intent();
            intent.setClassName(packageName, className);
            return intent;
        }
    }
}
