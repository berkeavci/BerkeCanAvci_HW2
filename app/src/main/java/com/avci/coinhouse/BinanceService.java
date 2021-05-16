package com.avci.coinhouse;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.avci.coinhouse.data.entities.Binance;

import org.json.JSONException;
import org.json.JSONObject;

public class BinanceService extends IntentService {
    private static final String BINANCE_API_URL = "https://api.binance.com/api/v3/ticker/price?symbol=BNBUSDT";
    private RequestQueue rq;
    Handler handler;

    public BinanceService() {
        super("Service is Active");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        handler = new Handler(getMainLooper());

        handler.post(new Runnable() {
            @Override
            public void run() {
                requestBinance(this);
            }
        });
    }

    public void requestBinance(Runnable runnable){
        rq = Volley.newRequestQueue(BinanceService.this);
        StringRequest request = new StringRequest(Request.Method.GET, BINANCE_API_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            Binance binance = new Binance(obj);
                            Intent broadcastIntent = new Intent();
                            Bundle b = new Bundle();
                            b.putParcelable("binanceInfo", binance);
                            broadcastIntent.putExtras(b);
                            getBaseContext().sendBroadcast(broadcastIntent);
                            broadcastIntent.setAction("COIN_INFORMATION");
                            sendBroadcast(broadcastIntent);

                            handler.postDelayed(runnable, 600);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("VolleyError", "onErrorResponse: " + error);
            }
        });
        rq.getCache().clear();
        rq.add(request);
    }
}
