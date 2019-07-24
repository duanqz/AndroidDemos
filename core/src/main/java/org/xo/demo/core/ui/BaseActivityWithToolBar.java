package org.xo.demo.core.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.core.app.TaskStackBuilder;

public class BaseActivityWithToolBar extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBar();
    }

    protected void setActionBar() {
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getToolbarTitle());
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    protected String getToolbarTitle() {
        return getClass().getSimpleName();
    }

    protected void info(String message) {
        Log.i(getClass().getSimpleName(), message);
    }

    protected void debug(String message) {
        Log.d(getClass().getSimpleName(), message);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                if (upIntent != null) {
                    if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                        TaskStackBuilder.create(this)
                                .addNextIntentWithParentStack(upIntent)
                                .startActivities();
                    } else {
                        NavUtils.navigateUpTo(this, upIntent);
                    }
                } else {
                    onBackPressed();
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
