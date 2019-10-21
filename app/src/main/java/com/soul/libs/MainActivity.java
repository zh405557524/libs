package com.soul.libs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.soul.lib.test.TestActivity;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.tv_lib).setOnClickListener(this);
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
        }
    }
}
