package com.cloudpos.demo.paywizard.ui;

import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudpos.demo.paywizard.R;
import com.cloudpos.demo.paywizard.http.BaseCallback;
import com.cloudpos.demo.paywizard.http.Bean;
import com.cloudpos.demo.paywizard.http.HttpHelper;
import com.cloudpos.demo.paywizard.http.dto.TerminalBind;
import com.cloudpos.demo.paywizard.http.dto.TransResult;
import com.cloudpos.demo.paywizard.utils.PreferenceHelper;
import com.cloudpos.demo.paywizard.utils.SignUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.text.SimpleDateFormat;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";
    private TextView tvResult;
    private ScrollView svLog;
    private Spinner spinnerType;
    private EditText etPosId, etTermialSN, etTradeNo, etTransAmount, etTipAmount, etOldTransId, etOldTrace, etOldTransIndexCode, etOldTransRrn, etOldTransInvoiceNumber, etOtherAmount;

    @Override
    protected void onInitData() {
        HttpHelper.init("https://uat.paywizard.biz/ovstrade/semiOpen/v3/");
        HttpHelper.create();
    }

    @Override
    protected void onInitView() {
        tvResult = findViewById(R.id.tv_result);
        svLog = findViewById(R.id.sv_log);
        spinnerType = findViewById(R.id.spinner_trans_type);
        etTradeNo = findViewById(R.id.et_trade_no);
        etTransAmount = findViewById(R.id.et_trans_amount);
        etTipAmount = findViewById(R.id.et_tip_amount);
        etOldTransId = findViewById(R.id.et_old_trans_id);
        etOldTrace = findViewById(R.id.et_old_trace);
        etOldTransIndexCode = findViewById(R.id.et_old_trans_index_code);
        etOldTransRrn = findViewById(R.id.et_old_trans_rrn);
        etOldTransInvoiceNumber = findViewById(R.id.et_old_trans_invoice_number);
        etOtherAmount = findViewById(R.id.et_other_amount);
        etPosId = findViewById(R.id.et_pos_id);
        etTermialSN = findViewById(R.id.et_terminal_sn);
        etPosId.setText(PreferenceHelper.getInstance(this).getStringValue("posId"));
        etTermialSN.setText(PreferenceHelper.getInstance(this).getStringValue("terminalSn"));
    }

    @Override
    protected int getContentViewID() {
        return R.layout.actvity_main;
    }

    public void registerTerminal(View view) {
        JSONObject jsonObject = new JSONObject();
        try {
            String posId = etPosId.getText().toString();
            String terminalSn = etTermialSN.getText().toString();
            if(TextUtils.isEmpty(posId) || TextUtils.isEmpty(terminalSn)){
                Toast.makeText(this, R.string.check_sn, Toast.LENGTH_SHORT).show();
                return;
            }
            jsonObject.put("clientId", HttpHelper.CLIENT_ID);
            jsonObject.put("merchantId", HttpHelper.MID);
            jsonObject.put("posId", posId);
            jsonObject.put("terminalId", "00000000");
            jsonObject.put("terminalSn", terminalSn);
            PreferenceHelper.getInstance(this).setStringValue("posId", posId);
            PreferenceHelper.getInstance(this).setStringValue("terminalSn", terminalSn);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        String json = jsonObject.toString();
        Log.d(TAG, "request body = " + json);
        RequestBody requestBody = RequestBody.create( json, MediaType.parse("application/json"));
        HttpHelper.create()
                .registerTerminal(requestBody, getSign(json)).enqueue(new BaseCallback<Bean<String>>() {
                    @Override
                    protected void onSuccess(Bean<String> stringBean) {
                        showLog(TAG, stringBean.getCode() + " " + stringBean.getMsg() +  " " + stringBean.getTimestamp());
                    }

                    @Override
                    protected void onFail(String errorMsg) {
                        super.onFail(errorMsg);
                        showLog(TAG, errorMsg);
                    }
                });
    }

    public void queryTerminalList(View view) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("clientId", HttpHelper.CLIENT_ID);
            jsonObject.put("merchantId", HttpHelper.MID);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        String json = jsonObject.toString();
        Log.d(TAG, "request body = " + json);
        RequestBody requestBody = RequestBody.create( json, MediaType.parse("application/json"));
        HttpHelper.create()
                .queryTerminalList(requestBody, getSign(json)).enqueue(new BaseCallback<Bean<TerminalBind[]>>() {
                    @Override
                    protected void onSuccess(Bean<TerminalBind[]> stringBean) {
                        showLog(TAG, stringBean.getCode() + " " + stringBean.getMsg() +  " " + stringBean.getTimestamp());
                        for(TerminalBind t : stringBean.getData()){
                            showLog(TAG, t.toString());
                        }
                    }

                    @Override
                    protected void onFail(String errorMsg) {
                        super.onFail(errorMsg);
                        showLog(TAG, errorMsg);
                    }
                });
    }

    public void unbindTerminal(View view) {
        JSONObject jsonObject = new JSONObject();
        try {
            String posId = etPosId.getText().toString();
            if(TextUtils.isEmpty(posId)){
                Toast.makeText(this, R.string.check_sn, Toast.LENGTH_SHORT).show();
                return;
            }
            jsonObject.put("clientId", HttpHelper.CLIENT_ID);
            jsonObject.put("merchantId", HttpHelper.MID);
            jsonObject.put("posId", posId);
            jsonObject.put("terminalId", "00000000");
            jsonObject.put("terminalSn", HttpHelper.device.getSerialNumber());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        String json = jsonObject.toString();
        Log.d(TAG, "request body = " + json);
        RequestBody requestBody = RequestBody.create( json, MediaType.parse("application/json"));
        HttpHelper.create()
                .unbindTerminal(requestBody, getSign(json)).enqueue(new BaseCallback<Bean<String>>() {
                    @Override
                    protected void onSuccess(Bean<String> stringBean) {
                        showLog(TAG, stringBean.getCode() + " " + stringBean.getMsg() +  " " + stringBean.getTimestamp());
                    }

                    @Override
                    protected void onFail(String errorMsg) {
                        super.onFail(errorMsg);
                        showLog(TAG, errorMsg);
                    }
                });
    }


    public void startTrans(View view) {
        JSONObject jsonObject = new JSONObject();
        String date = "";
        String time = "";
        try {
            String posId = etPosId.getText().toString();
            if(TextUtils.isEmpty(posId)){
                Toast.makeText(this, R.string.check_sn, Toast.LENGTH_SHORT).show();
                return;
            }
            SimpleDateFormat dateFormat = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                dateFormat = new SimpleDateFormat("YYYYMMdd hhmmss");
                String timeStr = dateFormat.format(new Date(System.currentTimeMillis()));
                String[] t = timeStr.split(" ");
                date = t[0];
                time = t[1];
            }

            jsonObject.put("clientId", HttpHelper.CLIENT_ID);
            jsonObject.put("merchantId", HttpHelper.MID);
            jsonObject.put("posId", posId);
            jsonObject.put("terminalId", "00000000");
            jsonObject.put("tradeNo", etTradeNo.getText().toString());
            jsonObject.put("transType", (String) spinnerType.getSelectedItem());
            jsonObject.put("transScheme", "Credit");
            jsonObject.put("callerName", HttpHelper.MERCH_NAME);
            jsonObject.put("transAmount", etTransAmount.getText().toString());
            jsonObject.put("currencyCode", "458");
            jsonObject.put("reqTransDate", date);
            jsonObject.put("reqTransTime", time);
            jsonObject.put("oriTransIndexCode", etOldTransIndexCode.getText().toString());
            jsonObject.put("oriTraceNum", etOldTrace.getText().toString());
            jsonObject.put("oriInvoiceNum", etOldTransInvoiceNumber.getText().toString());
            jsonObject.put("oriTransId", etOldTransId.getText().toString());
            jsonObject.put("oriRrn", etOldTransRrn.getText().toString());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        String json = jsonObject.toString();
        Log.d(TAG, "request body = " + json);
        RequestBody requestBody = RequestBody.create( json, MediaType.parse("application/json"));
        HttpHelper.create()
                .internetTrans(requestBody, getSign(json)).enqueue(new BaseCallback<Bean<String>>() {
                    @Override
                    protected void onSuccess(Bean<String> transactionRequestBean) {
                        showLog(TAG, transactionRequestBean.getCode() + " " + transactionRequestBean.getMsg() +  " " + transactionRequestBean.getTimestamp());
                    }

                    @Override
                    protected void onFail(String errorMsg) {
                        super.onFail(errorMsg);
                        showLog(TAG, errorMsg);
                    }
                });
    }

    public void queryResult(View view) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("clientId", HttpHelper.CLIENT_ID);
            jsonObject.put("merchantId", HttpHelper.MID);
            jsonObject.put("tradeNo", etTradeNo.getText().toString());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        String json = jsonObject.toString();
        Log.d(TAG, "request body = " + json);
        RequestBody requestBody = RequestBody.create( json, MediaType.parse("application/json"));
        HttpHelper.create()
                .getCashierAgentResult(requestBody, getSign(json)).enqueue(new BaseCallback<Bean<TransResult>>() {
                    @Override
                    protected void onSuccess(Bean<TransResult> transactionRequestBean) {
                        showLog(TAG, transactionRequestBean.getData().toString());
                    }

                    @Override
                    protected void onFail(String errorMsg) {
                        super.onFail(errorMsg);
                        showLog(TAG, errorMsg);
                    }
                });
    }

    public void cancel(View view) {
    }

    private void showLog(String tag, String log){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvResult.append("\n [ " +tag+" ] " + log + " \n");
                //Scroll to the bottom of the TextView
                svLog.smoothScrollTo(0, tvResult.getHeight());
            }
        });
    }

    private static String getSign(String str){
        Log.d("MainActivity", "params = " + str);
        String sign = SignUtils.generateSign(str, HttpHelper.CLIENT_ID, HttpHelper.CLIENT_SECRET);
        Log.d("MainActivity", sign);
        return sign;
    }
}
