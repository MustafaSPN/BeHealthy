package com.example.cilek_adam;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import java.io.Console;

public class DailyReport extends AppCompatActivity {

    TextView headerText, calorieTakenText, calorieTakenInput, calorieBurnText, calorieBurnInput, BMIInput, helpText, netCalText, netCalInput, basalText, basalInput;

    ProgressBar calorieBar;

    UserInfo info;

    Switch dailySwitch;
    boolean sw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_report);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>" + getString(R.string.daily_header_T) + "</font>"));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.barColor)));
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_name);

        sw = getIntent().getBooleanExtra("sw", false);

        info = new UserInfo();
        headerText = findViewById(R.id.daily_header);
        calorieTakenText = findViewById(R.id.daily_calorieTakenText);
        calorieTakenInput = findViewById(R.id.daily_calorieTakenInput);
        calorieBurnText = findViewById(R.id.daily_calorieBurnText);
        calorieBurnInput = findViewById(R.id.daily_calorieBurnInput);
        dailySwitch = findViewById(R.id.dailySwitch);
        calorieBar = findViewById(R.id.daily_progressBar);
        BMIInput = findViewById(R.id.daily_BMIInput);
        helpText = findViewById(R.id.daily_helpText);
        netCalInput = findViewById(R.id.daily_netCalInput);
        netCalText = findViewById(R.id.daily_netCalText);
        basalText = findViewById(R.id.daily_basalText);
        basalInput = findViewById(R.id.daily_basalInput);

        int calorieTaken = info.getCalorie_taken();
        int calorieBurn = info.getCalorie_burn();
        int basalMetabolism = info.getBasalMetabolism();
        int calorieNet = calorieTaken - (calorieBurn + basalMetabolism);
        double bmi = info.getBMI();

        calorieTakenInput.setText(String.valueOf(calorieTaken));
        calorieBurnInput.setText(String.valueOf(calorieBurn));
        netCalInput.setText(String.valueOf(calorieNet));
        BMIInput.setText(String.valueOf((int) Math.round(bmi)));
        basalInput.setText(String.valueOf(basalMetabolism));
        dailySwitch.setChecked(sw);

        Resources res = getResources();

        if (calorieNet >= 0) {
            Drawable pbar = ResourcesCompat.getDrawable(res, R.drawable.progressbar, null);
            calorieBar.setProgressDrawable(pbar);
            int progress = (int) ((calorieNet/1000.0) * 100);
            calorieBar.setProgress(progress);
        } else {
            Drawable pbar = ResourcesCompat.getDrawable(res, R.drawable.progressbar_negative, null);
            calorieBar.setScaleX(-1);
            calorieBar.setProgressDrawable(pbar);
            int progress_n = (int) ((Math.abs(calorieNet)/1000.0) * 100);
            calorieBar.setProgress(progress_n);
        }
        if(bmi<=18){
            helpText.setText(R.string.daily_helpTextUW_T);
        }else if (bmi<=25){
            helpText.setText(R.string.daily_helpTextNW_T);
        } else if(bmi<=35){
            helpText.setText(R.string.daily_helpTextOW_T);
        } else{
            helpText.setText(R.string.daily_helpTextOW_T);
        }






        dailySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>" + getString(R.string.daily_header_E) + "</font>"));
                    headerText.setText(R.string.daily_header_E);
                    calorieTakenText.setText(R.string.daily_calorieTakenText_E);
                    calorieBurnText.setText(R.string.daily_calorieBurnText_E);
                    basalText.setText(R.string.daily_basalText_E);
                    netCalText.setText(R.string.daily_netCalText_E);
                    if(bmi<=18){
                        helpText.setText(R.string.daily_helpTextUW_E);
                    }else if (bmi<=25){
                        helpText.setText(R.string.daily_helpTextNW_E);
                    } else if(bmi<=35){
                        helpText.setText(R.string.daily_helpTextOW_E);
                    } else{
                        helpText.setText(R.string.daily_helpTextOW_E);
                    }
                } else {
                    getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>" + getString(R.string.daily_header_T) + "</font>"));
                    headerText.setText(R.string.daily_header_T);
                    calorieTakenText.setText(R.string.daily_calorieTakenText_T);
                    calorieBurnText.setText(R.string.daily_calorieBurnText_T);
                    basalText.setText(R.string.daily_basalText_T);
                    netCalText.setText(R.string.daily_netCalText_T);
                    if(bmi<=18){
                        helpText.setText(R.string.daily_helpTextUW_T);
                    }else if (bmi<=25){
                        helpText.setText(R.string.daily_helpTextNW_T);
                    } else if(bmi<=35){
                        helpText.setText(R.string.daily_helpTextOW_T);
                    } else{
                        helpText.setText(R.string.daily_helpTextOW_T);
                    }
                }
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            // Geri düğmesine basıldığında yapılacak işlemler
            // Burada belirli bir aktiviteye yönlendirebilirsiniz
            Intent intent = new Intent(this, menu.class);
            sw = dailySwitch.isChecked();
            intent.putExtra("sw", sw);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}