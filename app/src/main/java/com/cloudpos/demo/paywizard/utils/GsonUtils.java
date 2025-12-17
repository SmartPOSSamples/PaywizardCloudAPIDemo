package com.cloudpos.demo.paywizard.utils;


import com.google.gson.Gson;

public class GsonUtils {

    private GsonUtils() {
    }

    public static Gson getInstance() {
        return SignHolder.gson;
    }

    static class SignHolder {
        static Gson gson = new Gson();
    }

}