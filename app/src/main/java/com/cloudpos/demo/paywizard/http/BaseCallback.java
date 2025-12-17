package com.cloudpos.demo.paywizard.http;

import android.util.Log;

import androidx.annotation.NonNull;

import com.cloudpos.demo.paywizard.MyApplication;
import com.cloudpos.demo.paywizard.utils.GsonUtils;
import com.cloudpos.demo.paywizard.utils.NetworkManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class BaseCallback<T> implements Callback<T> {

    private static final String TAG = "BaseCallback";

    @Override
    public void onResponse(@NonNull Call<T> call, Response<T> response) {
        NetworkManager networkManager = MyApplication.getInstance().getNetworkManager();
        if (networkManager != null) {
            networkManager.noticeAllListener(true);
        }

        T t = response.body();
        if (t instanceof Bean) {
            Bean bean = (Bean) t;
            if (bean.getCode() != null && bean.getCode() == 200) {
                onSuccess(t);
            } else {
                onFail(bean.getMsg());
            }
        } else {
            Log.d(TAG, response.toString());
            Log.d(TAG, GsonUtils.getInstance().toJson(t));
            onFail("Serialization failed");
        }
        onFinish();
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        Log.e(TAG, t.getMessage());
        onFail(t.getMessage());
        onFinish();
    }

    protected abstract void onSuccess(T t);

    protected void onFail(String errorMsg) {
        Log.e(TAG, errorMsg);
    }

    public void onFinish() {
    }

}