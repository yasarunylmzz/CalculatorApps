package com.example.calculatorapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TextView eqText = findViewById(R.id.solution_tv);
        TextView resText = findViewById(R.id.result_tv);
        updateText(resText, "");
        updateText(eqText, "");

        final String[] temp = {""};


        Button button0 = findViewById(R.id.button0);
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        Button button5 = findViewById(R.id.button5);
        Button button6 = findViewById(R.id.button6);
        Button button7 = findViewById(R.id.button7);
        Button button8 = findViewById(R.id.button8);
        Button button9 = findViewById(R.id.button9);
        Button buttonDot = findViewById(R.id.buttondot);
        Button buttonAC = findViewById(R.id.buttonac);
        Button buttonOpenBracket = findViewById(R.id.button_open_bracket);
        Button buttonCloseBracket = findViewById(R.id.button_close_bracket);
        Button buttonEq = findViewById(R.id.equals);
        Button buttonDivide = findViewById(R.id.divide_button);
        Button buttonMultiply = findViewById(R.id.multiply);
        Button buttonPlus = findViewById(R.id.plus);
        Button buttonMinus = findViewById(R.id.minus);

        button0.setOnClickListener(view -> buttonPressed(eqText, temp, "0"));
        button1.setOnClickListener(view -> buttonPressed(eqText, temp, "1"));

        button2.setOnClickListener(view -> buttonPressed(eqText, temp, "2"));

        button3.setOnClickListener(view -> buttonPressed(eqText, temp, "3"));
        button4.setOnClickListener(view -> buttonPressed(eqText, temp, "4"));
        button5.setOnClickListener(view -> buttonPressed(eqText, temp, "5"));

        button6.setOnClickListener(view -> buttonPressed(eqText, temp, "6"));
        button7.setOnClickListener(view -> buttonPressed(eqText, temp, "7"));
        button8.setOnClickListener(view -> buttonPressed(eqText, temp, "8"));
        button9.setOnClickListener(view -> buttonPressed(eqText, temp, "9"));
        buttonPlus.setOnClickListener(view -> buttonPressed(eqText, temp, "+"));
        buttonDivide.setOnClickListener(view -> buttonPressed(eqText, temp, "/"));
        buttonMinus.setOnClickListener(view -> buttonPressed(eqText, temp, "-"));
        buttonOpenBracket.setOnClickListener(view -> buttonPressed(eqText, temp, "("));
        buttonDot.setOnClickListener(view -> buttonPressed(eqText, temp, "."));

        buttonCloseBracket.setOnClickListener(view -> buttonPressed(eqText, temp, ")"));
        buttonAC.setOnClickListener(view -> {
            temp[0] = "";
            buttonPressed(eqText, temp, "");
            buttonPressed(resText, temp, "");
        });
        buttonMultiply.setOnClickListener(view -> buttonPressed(eqText, temp, "*"));
        buttonEq.setOnClickListener(view -> {
            double result;

            ExecutorService exec = Executors.newFixedThreadPool(1);
            Expression e = new ExpressionBuilder(temp[0])
                    .build();
            Future<Double> future = ((Expression) e).evaluateAsync(exec);
            try {
                result = future.get();

                updateText(resText, String.format("%.1f", result));
                temp[0] = "";
                updateText(eqText, temp[0]);
            } catch (ExecutionException | InterruptedException ex) {
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("Hata.");
                alertDialog.setMessage("Hata.");
                updateText(resText, "");
                temp[0] = "";
                updateText(eqText, temp[0]);
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        (dialog, which) -> dialog.dismiss());
                alertDialog.show();
            }

        });


    }


    public void updateText(TextView textView, String string) {
        textView.setText(string);
    }

    public void buttonPressed(TextView textView, String[] string, String substring) {
        string[0] = string[0] + substring;
        textView.setText(string[0]);
    }

}



