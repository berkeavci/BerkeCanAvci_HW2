package com.avci.hw2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GestureDetectorCompat;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.avci.hw2.data.database.DatabaseHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

public class InitialActivity extends AppCompatActivity {

    private JSONObject username_details;
    DatabaseHelper db;
    private GestureDetectorCompat initialDetector;
    EditText userET, fav_cryptoET, fav_news_websiteET;
    Button mainActBtn, submit_data_btn;
    ConstraintLayout initial_constraint_layout;
    private static final String FILE_NAME = "login.json";
    private String initialLogin;
    Dialog dialogPrompt;
    boolean isEmpt = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Declarations
        userET = findViewById(R.id.userET);
        fav_cryptoET = findViewById(R.id.fav_cryptoET);
        fav_news_websiteET = findViewById(R.id.fav_news_websiteET);
        mainActBtn = findViewById(R.id.mainActBtn);
        submit_data_btn = findViewById(R.id.submit_data_btn);
        db = new DatabaseHelper(this);

        initial_constraint_layout = findViewById(R.id.initial_constraint_layout);
        initialGesture ig = new initialGesture();
        initialDetector = new GestureDetectorCompat(this, ig);

        initialLogin = loadFileFromAssets();


        File file = new File(this.getFilesDir(), FILE_NAME); // File def

        initial_constraint_layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return initialDetector.onTouchEvent(event);
            }
        });


        // For Homework_2 just to show local Json, Further for Project
        submit_data_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = userET.getText().toString();
                String fav_crypto = fav_cryptoET.getText().toString();
                String fav_website = fav_news_websiteET.getText().toString();

                if(user.equals("") && fav_crypto.equals("") && fav_website.equals("")){
                    try {
                        username_details = new JSONObject(initialLogin);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    for (int i = 0; i < initialLogin.length(); i++) {
                        Toast.makeText(InitialActivity.this, initialLogin.toString(),Toast.LENGTH_LONG).show();
                    }
                    Log.d("DATA TRANSFER = > ", user + fav_crypto + fav_website);
                }else{
                    // Homework2_Part
                    Intent intent = new Intent(InitialActivity.this, MainActivity.class);
                    Bundle b = new Bundle();
                    b.putString("user", user);
                    b.putString("fav_crypto", fav_crypto);
                    b.putString("fav_website", fav_website);
                    intent.putExtras(b);
                    isEmpt = true;
                    startActivity(intent);
                    //  File Definitions  under assets basically - BELOW IS PROJECT
                    FileWriter fileW;
                    BufferedWriter bufferedW;
                    username_details = new JSONObject();

                    JSONArray newUserInfo = new JSONArray();
                    newUserInfo.put(user);
                    newUserInfo.put(fav_crypto);
                    newUserInfo.put(fav_website);
                    Log.d("Hello First!", newUserInfo+"");
                    try {
                        username_details.put("username", newUserInfo);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        fileW = new FileWriter(file.getAbsoluteFile());
                        bufferedW = new BufferedWriter(fileW);
                        Log.d("\n\n\nHello", username_details+"");
                        bufferedW.write(username_details.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        mainActBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InitialActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private String loadFileFromAssets() {
        String fileContent = null;
        try {
            InputStream is = getBaseContext().getAssets().open(FILE_NAME);

            int size = is.available();
            byte[] buffer = new byte[size];

            is.read(buffer);
            is.close();

            fileContent = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return fileContent;
    }

    class initialGesture extends GestureDetector.SimpleOnGestureListener{
        @Override
        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);
            dialog_prompt();
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            if(!isEmpt) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
            return true;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
    }

    private void dialog_prompt(){
        dialogPrompt = new Dialog(this);
        dialogPrompt.setContentView(R.layout.dialog_prompt);

        Button yes_btn, no_btn;

        yes_btn = dialogPrompt.findViewById(R.id.yes_btn);
        no_btn = dialogPrompt.findViewById(R.id.no_btn);

        yes_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                dialogPrompt.dismiss();
                startActivity(intent);
            }
        });
        no_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogPrompt.dismiss();
            }
        });

        dialogPrompt.show();
    }
}