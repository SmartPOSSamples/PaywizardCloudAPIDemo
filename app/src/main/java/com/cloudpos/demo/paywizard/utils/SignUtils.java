package com.cloudpos.demo.paywizard.utils;

import android.util.Log;

import com.xiaoleilu.hutool.util.StrUtil;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class SignUtils {

    /**
     * Encrypts data using HmacSHA256.
     * @param data The string to be encrypted.
     * @param key The secret key.
     * @return java.lang.String The encrypted string in hexadecimal format.
     **/
    public static String sha256Hmac(String data, String key) {
        String cipher = "";
        try {
            byte[] byteList = key.getBytes(StandardCharsets.UTF_8);
            // Construct a secret key from the given byte array for a specified algorithm name,
            // in this case, generating a key specific to HmacSHA256.
            SecretKey secretKey = new SecretKeySpec(byteList, "HmacSHA256");

            // Get a Mac object for a specified MAC algorithm.
            Mac mac = Mac.getInstance("HmacSHA256");
            // Initialize the Mac object with the given secret key.
            mac.init(secretKey);
            byte[] text = data.getBytes(StandardCharsets.UTF_8);
            byte[] encryptByte = mac.doFinal(text);
            cipher = bytesToHexStr(encryptByte);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return cipher;
    }

    /**
     * Converts a byte array to a hexadecimal string.
     * @param bytes The byte array to convert.
     * @return java.lang.String The resulting hexadecimal string.
     **/
    public static String bytesToHexStr(byte[] bytes) {
        StringBuilder hexStr = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(b & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            hexStr.append(hex);
        }
        return hexStr.toString();
    }

    /**
     * Generates a signature.
     * @param paramsStr The data to be sent.
     * @param clientId The developer ID.
     * @param clientSecret The developer secret key.
     * @return java.lang.String The generated signature.
     **/
    public static String generateSign(String paramsStr, String clientId, String clientSecret) {
        String str = StrUtil.format("{}&clientId={}&clientSecret={}", paramsStr, clientId, clientSecret);
        Log.i("paramsStr:{}", str);
        String sign = sha256Hmac(str, clientSecret);
        Log.i("sign:", sign);
        return sign;
    }
}
