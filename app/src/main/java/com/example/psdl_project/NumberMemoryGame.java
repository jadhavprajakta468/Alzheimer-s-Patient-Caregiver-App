package com.example.psdl_project;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class NumberMemoryGame extends AppCompatActivity {

    TextView tv_level, tv_number;
    EditText et_number;
    Button b_confirm, b_back;
    Random r;
    int level = 1;
    String generatedNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_number_memory_game);
        tv_level = (TextView) findViewById(R.id.tv_level);
        tv_number = (TextView) findViewById(R.id.tv_number);
        et_number = (EditText) findViewById(R.id.et_number);
        b_confirm = (Button) findViewById(R.id.b_confirm);
        r = new Random();
        tv_number.setVisibility(View.VISIBLE);
        et_number.setVisibility(View.GONE);
        b_confirm.setVisibility(View.GONE);
        tv_level.setText("Level: " + level);
        generatedNumber = generateNumber(level);
        tv_number.setText(generatedNumber);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                et_number.setVisibility(View.VISIBLE);
                b_confirm.setVisibility(View.VISIBLE);
                tv_number.setVisibility(View.GONE);
                et_number.requestFocus();
            }
        }, 2000);

        b_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (generatedNumber.equals(et_number.getText().toString())){
                    tv_number.setVisibility(View.VISIBLE);
                    et_number.setVisibility(View.GONE);
                    b_confirm.setVisibility(View.GONE);
                    et_number.setText("");
                    level++;
                    tv_level.setText("Level: " + level);
                    generatedNumber = generateNumber(level);
                    tv_number.setText(generatedNumber);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            et_number.setVisibility(View.VISIBLE);
                            b_confirm.setVisibility(View.VISIBLE);
                            tv_number.setVisibility(View.GONE);
                            et_number.requestFocus();
                        }
                    }, 2000);
                } else {
                    tv_level.setText("Game Over! The number was " + generatedNumber);
                    b_confirm.setEnabled(false);
                }
            }
        });


    }

    private String generateNumber(int digits){
        StringBuilder output = new StringBuilder();
        for (int i=1 ; i<=digits ; i++){
            int randomDigit = r.nextInt(10);
            output.append("").append(randomDigit);
        }
        return output.toString();
    }
}

