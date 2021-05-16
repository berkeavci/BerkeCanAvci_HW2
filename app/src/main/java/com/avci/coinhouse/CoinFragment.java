package com.avci.coinhouse;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.avci.coinhouse.data.entities.Binance;


public class CoinFragment extends Fragment {
    TextView coin_name, coin_price, username;

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
        username = view.findViewById(R.id.username);

        coin_name.setText("BTC");
        coin_name.setText("10");
        username.setText("Username");

         // String c_name = getArguments()
    }

    public void binanceUpdate(Binance b, String userN){
        Log.d("Fragment Username", "UserNameM: ");
        coin_name.setText(b.getSymbol() + "");
        coin_price.setText(b.getPrice() + "");
        username.setText(userN);

    }


}
