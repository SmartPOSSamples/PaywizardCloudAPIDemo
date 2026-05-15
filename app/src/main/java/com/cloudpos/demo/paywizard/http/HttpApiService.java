package com.cloudpos.demo.paywizard.http;

import com.cloudpos.demo.paywizard.http.dto.TerminalBind;
import com.cloudpos.demo.paywizard.http.dto.TransResult;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface HttpApiService {

    /**
     * Register POS and Bind POS with Payment Terminal
     * @param body
     * @param sign
     * @return
     */
    @POST("registerTerminal")
    Call<Bean<String>> registerTerminal(@Body RequestBody body, @Header("sign") String sign);

    /**
     * Get POS & Terminal Binding List
     * @param body
     * @param sign
     * @return
     */
    @POST("queryTerminalList")
    Call<Bean<TerminalBind[]>> queryTerminalList(@Body RequestBody body, @Header("sign") String sign);

    /**
     * Unbind Terminal with POS
     * @param body
     * @param sign
     * @return
     */
    @POST("unbindTerminal")
    Call<Bean<String>> unbindTerminal(@Body RequestBody body, @Header("sign") String sign);

    /**
     * Initiate a Transaction
     * @param body
     * @param sign
     * @return
     */
    @POST("internetTrans")
    Call<Bean<String>> internetTrans(@Body RequestBody body, @Header("sign") String sign);

    /**
     * Get Transaction Result
     * @param body
     * @param sign
     * @return
     */
    @POST("getCashierAgentResult")
    Call<Bean<TransResult>> getCashierAgentResult(@Body RequestBody body, @Header("sign") String sign);
    /**
     * Get Transaction Result
     * @param body
     * @param sign
     * @return
     */
    @POST("terminalOperations")
    Call<Bean<TransResult>> terminalOperations(@Body RequestBody body, @Header("sign") String sign);
}