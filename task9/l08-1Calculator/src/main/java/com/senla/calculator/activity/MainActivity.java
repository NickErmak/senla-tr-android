package com.senla.calculator.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.senla.calculator.repository.DataHandler;
import com.senla.calculator.R;
import com.senla.calculator.api.IDataHandler;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private final int REQUEST_CODE_CACLCULATOR = 111;
    private final int MAX_COUNT = 5;

    private IDataHandler dataHandler;

    private ArrayAdapter<String> dataAdapter;

    private Button mBtnCalculator, mBtnSave;
    private ListView mLvData;
    private TextView mTvCurrentValue;

    private String valueForSave;

    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataHandler = new DataHandler(MAX_COUNT);

        dataAdapter = new ArrayAdapter<String>(this, R.layout.listview_saved_data, R.id.lv_values_tv_data);
        mTvCurrentValue = (TextView) findViewById(R.id.main_tv_current_value);
        mLvData = (ListView) findViewById(R.id.main_lv_data);
        mLvData.setAdapter(dataAdapter);
        mBtnSave = (Button) findViewById(R.id.main_btn_save);
        mBtnCalculator = (Button) findViewById(R.id.main_btn_calc);

        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (valueForSave != null) {
                    dataHandler.putValue(valueForSave);
                    refreshSavedValues();
                }
            }
        });

        mBtnCalculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CalculatorActivity.class);
                startActivityForResult(intent, REQUEST_CODE_CACLCULATOR);
            }
        });

        refreshSavedValues();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case REQUEST_CODE_CACLCULATOR:
                valueForSave = data.getExtras().getString("num");
                mTvCurrentValue.setText(valueForSave);
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void refreshSavedValues() {
        dataAdapter.clear();
        for (String value : dataHandler.getValues()) {
            dataAdapter.insert(value, dataAdapter.getCount());
        }
    }
}
