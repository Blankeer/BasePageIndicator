package com.blanke.basepageindicator.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.view.View;
import android.view.Window;

import com.blanke.basepageindicator.R;

public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void jumpTitleIndicator(View v) {
        startActivity(new Intent(this, TitleIndicatorActivity.class));
    }

    public void jumpMIUITitleIndicator(View v) {
        startActivity(new Intent(this, MIUITitleIndicatorActivity.class));
    }

    public void jumpIconTitleIndicator(View v) {
        startActivity(new Intent(this, IconTitleIndicatorActivity.class));
    }

    public void jumpAllIndicator(View v) {
        startActivity(new Intent(this, AllStyleIndicatorActivity.class));
    }
}