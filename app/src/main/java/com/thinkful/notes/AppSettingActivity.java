package com.thinkful.notes;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;


public class AppSettingActivity extends ActionBarActivity {

    private RadioGroup radioGroup;
    private Button settingSaveButton;
    private int bg_color = Color.WHITE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_background);
        onClickListenerButton();
    }

    public void onClickListenerButton() {
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup_bg);
        settingSaveButton = (Button) findViewById(R.id.SettingSaveButton);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View container = findViewById(R.id.av);

                switch (checkedId) {
                    case R.id.radioButton_bg_red:
                        bg_color = Color.RED;
                        break;
                    case R.id.radioButton_bg_green:
                        bg_color = Color.GREEN;
                        break;
                    case R.id.radioButton_bg_white:
                        bg_color = Color.WHITE;
                        break;
                }
                container.setBackgroundColor(bg_color);
            }
        });


        settingSaveButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putInt("BG_COLOR", AppSettingActivity.this.bg_color);
                        editor.commit();
                        finish();
                    }
                }
        );
    }
}
