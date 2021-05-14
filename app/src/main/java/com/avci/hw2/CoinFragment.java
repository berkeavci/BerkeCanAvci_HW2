package com.avci.hw2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.avci.hw2.data.entities.Binance;


public class CoinFragment extends Fragment {

    TextView coin_name, coin_price;

    public CoinFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        coin_name = view.findViewById(R.id.coin_name);
        coin_price = view.findViewById(R.id.coin_price);

        Binance bi = getArguments().getParcelable("binanceInfo");

        coin_name.setText(bi.getSymbol()+"");
        coin_price.setText(bi.getPrice()+"");

        // String c_name = getArguments()
    }



}
