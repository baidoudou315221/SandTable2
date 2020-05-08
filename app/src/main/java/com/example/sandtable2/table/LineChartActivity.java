package com.example.sandtable2.table;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.sandtable2.R;
import com.example.sandtable2.infomation.WeatherData;

public class LineChartActivity extends AppCompatActivity {

    private int ItemCode;
    IndexHorizontalScrollView indexHorizontalScrollView;
    Today24HourView today24HourView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart);
        Intent intent = getIntent();
        ItemCode = Integer.parseInt(intent.getStringExtra("项目代号"));
        Log.d("------>","initView");
        Log.d("------>","Item Code："+ItemCode);
        initView();
    }

    private void initView() {
        Log.d("------>","进入initView方法");
        indexHorizontalScrollView = findViewById(R.id.indexHorizontalScrollView);
        Log.d("------>","find ScrollView");
        today24HourView = findViewById(R.id.today24HourView);
        Log.d("------>","find today24HourView");
        switch (ItemCode){
            case 0:
                today24HourView.initHourItems(WeatherData.temperature);
                break;
            case 1:
                today24HourView.initHourItems(WeatherData.illumination);
                break;
            case 2:
                today24HourView.initHourItems(WeatherData.humidity);
                break;
            case 3:
                today24HourView.initHourItems(WeatherData.CO2);
                break;
        }
        indexHorizontalScrollView.set24HourView(today24HourView);
        Log.d("------>","进入initView后");
    }

}
