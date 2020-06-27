package com.soul.libs;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.soul.lib.test.TestActivity;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.tv_lib).setOnClickListener(this);
        findViewById(R.id.tv_frame).setOnClickListener(this);
        findViewById(R.id.tv_jni).setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tv_lib: {
                final Intent intent = new Intent(MainActivity.this, TestActivity.class);
                intent.putExtra("pageName", "com.soul.libs.lib");
                startActivity(intent);

            }
            break;
            case R.id.tv_frame: {
                final Intent intent = new Intent(MainActivity.this, TestActivity.class);
                intent.putExtra("pageName", "com.soul.libs.frame");
                startActivity(intent);
            }
            break;
            case R.id.tv_jni: {
                final Intent intent = new Intent(MainActivity.this, TestActivity.class);
                intent.putExtra("pageName", "com.soul.libs.jni");
                startActivity(intent);
            }
            break;
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }
}
