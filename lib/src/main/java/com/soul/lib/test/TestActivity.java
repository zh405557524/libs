package com.soul.lib.test;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.soul.lib.R;
import com.soul.lib.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class TestActivity extends AppCompatActivity {

    private FrameLayout mMainRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_test);
        mMainRoot = findViewById(R.id.main_root);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                final Intent intent = getIntent();

                final List<String> strings;

                String pageName = null;
                if (intent != null) {
                    pageName = intent.getStringExtra("pageName");
                }
                if (!TextUtils.isEmpty(pageName)) {
                    strings = FragmentFactory.initFragmentCount(TestActivity.this, pageName);
                } else {
                    strings = FragmentFactory.initFragmentCount(TestActivity.this);
                }
                UIUtils.getMainThreadHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        final Bundle bundle = new Bundle();

                        bundle.putStringArrayList("classNames", (ArrayList<String>) strings);
                        FragmentUtils.startFragment(FragmentFactory.TEXT_VIEW_CLASSIFY, TestActivity.this, bundle);
                    }
                });
            }
        }).start();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}