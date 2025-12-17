package com.cloudpos.demo.paywizard.http;

import android.util.Log;

import com.cloudpos.POSTerminal;
import com.cloudpos.TerminalSpec;
import com.cloudpos.demo.paywizard.MyApplication;
import com.cloudpos.demo.paywizard.utils.SignUtils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionPool;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class HttpHelper {

    private static volatile Retrofit retrofit;
//    private static volatile Retrofit stringRetrofit;
    private static final int DEFAULT_TIMEOUT = 60;
    public static final String JWT_TOKEN = "O3F41DIHGPOCKA5UUXVVB4HZ8JBUYHSY";
    public static final String CLIENT_ID = "985534437301288960";
    public static final String MID = "202512150000001";

    public static final String MERCH_NAME = "测试商户-终端";
    public static final String POS_ID = "O3F41DIHGPOCKA5UUXVVB4HZ8JBUYHSY";
    public static final String CLIENT_SECRET = "9ef8d34c3c2ef6573148e4cafd01c48f";

    public static final String TERMINAL_SN = "WP50505Q33200088";
    public static TerminalSpec device;

    public static void init(String baseUrl) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkHttpClient okHttpClient = builder
                .addInterceptor(new HeaderInterceptor())
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .connectionPool(new ConnectionPool(8, 15, TimeUnit.SECONDS))
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

//        stringRetrofit = new Retrofit.Builder()
//                .baseUrl(baseUrl)
//                .client(okHttpClient)
//                .addConverterFactory(ScalarsConverterFactory.create())
//                .build();
        if(device == null){
            device = (TerminalSpec) POSTerminal.getInstance(MyApplication.getInstance().getApplicationContext()).getTerminalSpec();
        }
    }



    // A custom interceptor is used to add a unified request header.
    private static class HeaderInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();
            Request newRequest = originalRequest.newBuilder()
                    .addHeader("jwt-token", JWT_TOKEN)
                    .addHeader("Content-Type", "application/json")
                    .build();
            Log.d("MainActivity", "request = " + newRequest.toString());
            return chain.proceed(newRequest);
        }
    }

    public static Retrofit getRetrofit() {
        return retrofit;
    }

//    public static Retrofit getStringRetrofit() {
//        return stringRetrofit;
//    }

    public static HttpApiService create() {
        return HttpHelper.getRetrofit().create(HttpApiService.class);
    }

}