package com.cloudpos.demo.paywizard;

import android.app.Application;

import com.cloudpos.demo.paywizard.utils.NetworkManager;

public class MyApplication extends Application {

    private static MyApplication instance;
    private NetworkManager networkManager;

    public static MyApplication getInstance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        networkManager = new NetworkManager(this);
        networkManager.addListener(new NetworkManager.OnNetworkStateListener() {
            @Override
            public void onNetworkStateChange(boolean isConnect) {

            }
        });
        networkManager.register();
    }

    public NetworkManager getNetworkManager() {
        return networkManager;
    }
}
