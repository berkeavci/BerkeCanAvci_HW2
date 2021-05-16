package com.avci.coinhouse;

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

import com.avci.coinhouse.data.database.DatabaseHelper;

import org.json.JSONObject;

public class InitialActivity extends AppCompatActivity {

    private JSONObject username_details;
    DatabaseHelper db;
    private GestureDetectorCompat initialDetector;
    EditText userET, userPw;
    Button mainActBtn, submit_data_btn;
    ConstraintLayout initial_constraint_layout;
    Intent intent;
    Dialog dialogPrompt;
    boolean isEmpt = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Declarations
        userET = findViewById(R.id.userET);
        userPw = findViewById(R.id.userPw);
        mainActBtn = findViewById(R.id.mainActBtn);
        db = new DatabaseHelper(this);

        initial_constraint_layout = findViewById(R.id.initial_constraint_layout);
        initialGesture ig = new initialGesture();
        initialDetector = new GestureDetectorCompat(this, ig);


        initial_constraint_layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return initialDetector.onTouchEvent(event);
            }
        });



        mainActBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = userET.getText().toString();
                Log.d("InitialUsername", "onClick: "+user);
                if(!user.equals("")){
                    intent = new Intent(InitialActivity.this, MainActivity.class);
                    Bundle b = new Bundle();
                    b.putString("userName", user);
                    intent.putExtras(b);
                    startActivity(intent);
                }else{
                    Toast.makeText(InitialActivity.this, "Input Please!", Toast.LENGTH_LONG).show();
                }
            }
        });

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