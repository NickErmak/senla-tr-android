package com.senla.calculator.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.senla.calculator.App;
import com.senla.calculator.api.IDataHandler;

import java.util.LinkedList;
import java.util.Queue;

public class DataHandler implements IDataHandler {

    private final String KEY_VALUE = "keyValue";
    private final String DATA_VALUES = "dataValues";
    private SharedPreferences sPref;
    private SharedPreferences.Editor editor;
    private int maxCount;
    private Queue<String> values;

    public DataHandler(int maxCount) {
        this.maxCount = maxCount;
        values = new LinkedList<>();
        sPref = App.self.getSharedPreferences(DATA_VALUES, Context.MODE_PRIVATE);
        editor = sPref.edit();
        loadData();
    }

    private void loadData() {
        for (int i = 0; i++ < maxCount; ) {
            String newValue = sPref.getString(KEY_VALUE + i, "0");
            if (!newValue.equals("0")) {
                values.add(newValue);
            }
        }
    }

    @Override
    public void putValue(String value) {
        editor.putString(KEY_VALUE + values.size(), value)
                .commit();
        if (values.size() >= maxCount) {
            values.remove();
        }
        values.add(value);
    }

    @Override
    public String[] getValues() {
        return values.toArray(new String[0]);
    }
}
