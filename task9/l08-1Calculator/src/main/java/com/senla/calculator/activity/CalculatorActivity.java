package com.senla.calculator.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.senla.calculator.R;

public class CalculatorActivity extends AppCompatActivity implements View.OnClickListener {

    private final String KEY_RESULT = "keyResult";
    private final String KEY_NUM = "keyNum";
    private final String KEY_FUN = "keyFUN";
    private final String KEY_SCREEN_VALUE = "screenValue";

    private Button mBtn1, mBtn2, mBtn3, mBtn4, mBtn5, mBtn6, mBtn7, mBtn8, mBtn9, mBtn0,
            mBtnMinus, mBtnPlus, mBtnDivide, mBtnMultiply, mBtnEquels, mBtnReset, mBtnOk;
    private TextView mTvResult;

    private int result;
    private int num;
    private char fun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        findAllViews();
        setListeners();
        result = 0;
        num = 0;
        mTvResult.setText(String.valueOf(result));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn0:
            case R.id.btn1:
            case R.id.btn2:
            case R.id.btn3:
            case R.id.btn4:
            case R.id.btn5:
            case R.id.btn6:
            case R.id.btn7:
            case R.id.btn8:
            case R.id.btn9:
                numHandler((Button) view);
                break;
            case R.id.btn_plus:
            case R.id.btn_minus:
            case R.id.btn_multiply:
            case R.id.btn_divide:
                funHandler((Button) view);
                break;
            case R.id.btn_equels:
                solveExpression();
                break;
            case R.id.btn_reset:
                result = 0;
                mTvResult.setText(String.valueOf(result));
                break;
            case R.id.btn_ok:
                setResult(RESULT_OK, new Intent().putExtra("num", mTvResult.getText().toString()));
                finish();
                break;
        }
    }

    private void numHandler(Button btn) {
        CharSequence btnTextValue = btn.getText();
        if (mTvResult.getText().toString().matches("[0*+\\-\\/]")) {
            mTvResult.setText(btnTextValue);
        } else {
            mTvResult.append(btnTextValue);
        }
    }

    private void funHandler(Button btn) {
        result = Integer.valueOf(mTvResult.getText().toString());
        mTvResult.setText(btn.getText());
        fun = btn.getText().toString().charAt(0);
        num = 0;
    }

    private void solveExpression() {
        try {
            if (num == 0) {
                num = Integer.valueOf(mTvResult.getText().toString());
            }
            switch (fun) {
                case '+': {
                    result += num;
                    break;
                }
                case '-': {
                    result -= num;
                    break;
                }
                case '*': {
                    result *= num;
                    break;
                }
                case '/': {
                    result /= num;
                    break;
                }
            }
            mTvResult.setText(String.valueOf(result));
        } catch (ArithmeticException e) {
            Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
            recreate();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(KEY_RESULT, result);
        outState.putInt(KEY_NUM, num);
        outState.putChar(KEY_FUN, fun);
        outState.putString(KEY_SCREEN_VALUE, mTvResult.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        result = savedInstanceState.getInt(KEY_RESULT);
        num = savedInstanceState.getInt(KEY_NUM);
        fun = savedInstanceState.getChar(KEY_FUN);
        mTvResult.setText(savedInstanceState.getString(KEY_SCREEN_VALUE));
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void findAllViews() {
        mTvResult = (TextView) findViewById(R.id.tv_result);
        mBtn0 = (Button) findViewById(R.id.btn0);
        mBtn1 = (Button) findViewById(R.id.btn1);
        mBtn2 = (Button) findViewById(R.id.btn2);
        mBtn3 = (Button) findViewById(R.id.btn3);
        mBtn4 = (Button) findViewById(R.id.btn4);
        mBtn5 = (Button) findViewById(R.id.btn5);
        mBtn6 = (Button) findViewById(R.id.btn6);
        mBtn7 = (Button) findViewById(R.id.btn7);
        mBtn8 = (Button) findViewById(R.id.btn8);
        mBtn9 = (Button) findViewById(R.id.btn9);
        mBtnMinus = (Button) findViewById(R.id.btn_minus);
        mBtnPlus = (Button) findViewById(R.id.btn_plus);
        mBtnDivide = (Button) findViewById(R.id.btn_divide);
        mBtnMultiply = (Button) findViewById(R.id.btn_multiply);
        mBtnReset = (Button) findViewById(R.id.btn_reset);
        mBtnEquels = (Button) findViewById(R.id.btn_equels);
        mBtnOk = (Button) findViewById(R.id.btn_ok);
    }

    private void setListeners() {
        mBtn0.setOnClickListener(this);
        mBtn1.setOnClickListener(this);
        mBtn2.setOnClickListener(this);
        mBtn3.setOnClickListener(this);
        mBtn4.setOnClickListener(this);
        mBtn5.setOnClickListener(this);
        mBtn6.setOnClickListener(this);
        mBtn7.setOnClickListener(this);
        mBtn8.setOnClickListener(this);
        mBtn9.setOnClickListener(this);
        mBtnDivide.setOnClickListener(this);
        mBtnPlus.setOnClickListener(this);
        mBtnMultiply.setOnClickListener(this);
        mBtnMinus.setOnClickListener(this);
        mBtnReset.setOnClickListener(this);
        mBtnEquels.setOnClickListener(this);
        mBtnOk.setOnClickListener(this);

    }
}
