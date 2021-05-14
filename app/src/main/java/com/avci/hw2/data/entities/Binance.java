package com.avci.hw2.data.entities;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Binance implements Parcelable {
    public String symbol;
    public Double price;


    public Binance(String symbol, Double price) {
        this.symbol = symbol;
        this.price = price;
    }

    public Binance(JSONObject obj) throws JSONException {
        this.symbol = obj.getString("symbol");
        this.price = obj.getDouble("price");
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String  toString() {
        return "Binance{" +
                "symbol='" + symbol + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
