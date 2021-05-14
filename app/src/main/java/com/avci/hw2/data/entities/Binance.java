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

    protected Binance(Parcel in) {
        symbol = in.readString();
        if (in.readByte() == 0) {
            price = null;
        } else {
            price = in.readDouble();
        }
    }

    public static final Creator<Binance> CREATOR = new Creator<Binance>() {
        @Override
        public Binance createFromParcel(Parcel in) {
            return new Binance(in);
        }

        @Override
        public Binance[] newArray(int size) {
            return new Binance[size];
        }
    };

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
    public String toString() {
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
        dest.writeString(symbol);
        if (price == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(price);
        }
    }
}
