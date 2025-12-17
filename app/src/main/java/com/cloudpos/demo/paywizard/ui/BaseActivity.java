package com.cloudpos.demo.paywizard.ui;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

public abstract class BaseActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewID());
        onInitData();
        onInitView();
    }

    protected abstract void onInitData();

    protected abstract void onInitView();

    protected abstract int getContentViewID();
}
