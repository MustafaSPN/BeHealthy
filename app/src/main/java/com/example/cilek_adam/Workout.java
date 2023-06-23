package com.example.cilek_adam;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.Nullable;


public class Workout extends AppCompatActivity {

    Switch trSwitch;

    Button button3, button4, button5, button6, button7;

    TextView baslik, text1, text2, text3, text4, text5;

    boolean sw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>" + "Antrenman Programları" + "</font>"));

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_name);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.barColor)));
        sw = getIntent().getBooleanExtra("sw", false);

        baslik = findViewById(R.id.workout_text);

        trSwitch = findViewById(R.id.workoutSwitch);

        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        button6 = findViewById(R.id.button6);
        button7 = findViewById(R.id.button7);

        text1 = findViewById(R.id.wotext1);
        text2 = findViewById(R.id.wotext2);
        text3 = findViewById(R.id.wotext3);
        text4 = findViewById(R.id.wotext4);
        text5 = findViewById(R.id.wotext5);


        baslik.setText(R.string.wo_baslikT);
        text1.setText(R.string.wo_5x5T);
        text2.setText(R.string.wo_fullbodyT);
        text3.setText(R.string.wo_3x5T);
        text4.setText(R.string.wo_splitT);
        text5.setText(R.string.wo_kardiyoT);
        button3.setText(R.string.program);
        button4.setText(R.string.program);
        button5.setText(R.string.program);
        button6.setText(R.string.program);
        button7.setText(R.string.program);


        trSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    baslik.setText(R.string.wo_baslikE);
                    text1.setText(R.string.wo_5x5E);
                    text2.setText(R.string.wo_fullbodyE);
                    text3.setText(R.string.wo_3x5E);
                    text4.setText(R.string.wo_splitE);
                    text5.setText(R.string.wo_kardiyoE);
                    button3.setText(R.string.program);
                    button4.setText(R.string.program);
                    button5.setText(R.string.program);
                    button6.setText(R.string.program);
                    button7.setText(R.string.program);
                    getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>" + "Workout Programs" + "</font>"));


                } else {
                    baslik.setText(R.string.wo_baslikT);
                    text1.setText(R.string.wo_5x5T);
                    text2.setText(R.string.wo_fullbodyT);
                    text3.setText(R.string.wo_3x5T);
                    text4.setText(R.string.wo_splitT);
                    text5.setText(R.string.wo_kardiyoT);
                    button3.setText(R.string.program);
                    button4.setText(R.string.program);
                    button5.setText(R.string.program);
                    button6.setText(R.string.program);
                    button7.setText(R.string.program);
                    getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>" + "Antrenman Programları" + "</font>"));

                }
            }


        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), WebWorkout.class);
                if(trSwitch.isChecked()){
                    i.putExtra("url", "https://www.healthline.com/health/fitness/5x5-workout");
                    sw = trSwitch.isChecked();
                    i.putExtra("sw", sw);
                }
                else i.putExtra("url", "https://www.kasvekuvvet.net/stronglifts-5x5");
                sw = trSwitch.isChecked();
                i.putExtra("sw", sw);
                startActivity(i);
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), WebWorkout.class);
                if(trSwitch.isChecked()){
                    i.putExtra("url", "https://hashimashi.com/3x5-workout/#:~:text=3×5%20Workout%20Program%20–%20Introduction,the%20weekend%20off%20to%20recover");
                    sw = trSwitch.isChecked();
                    i.putExtra("sw", sw);
                }
                else i.putExtra("url", "https://formassist.net/blogs/egzersiz/vucut-gelistirmek-icin-3-x-5-antrenman-programi");
                sw = trSwitch.isChecked();
                i.putExtra("sw", sw);
                startActivity(i);
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), WebWorkout.class);
                if(trSwitch.isChecked()){
                    i.putExtra("url", "https://www.setforset.com/blogs/news/full-body-workout-plan");
                    sw = trSwitch.isChecked();
                    i.putExtra("sw", sw);
                }
                else i.putExtra("url", "https://fithub.com.tr/kaslari-sisiren-en-iyi-full-body-antrenman-programi/");
                sw = trSwitch.isChecked();
                i.putExtra("sw", sw);
                startActivity(i);
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), WebWorkout.class);
                if(trSwitch.isChecked()){
                    i.putExtra("url", "https://www.healthline.com/health/fitness/split-workout-schedule");
                    sw = trSwitch.isChecked();
                    i.putExtra("sw", sw);
                }
                else i.putExtra("url", "https://fithub.com.tr/kusursuz-bir-vucut-icin-split-antrenman-programi/");
                sw = trSwitch.isChecked();
                i.putExtra("sw", sw);
                startActivity(i);
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), WebWorkout.class);
                if(trSwitch.isChecked()){
                    i.putExtra("url", "https://www.verywellfit.com/cardio-workout-program-weight-loss-1230810");
                    sw = trSwitch.isChecked();
                    i.putExtra("sw", sw);
                }
                else i.putExtra("url", "https://www.agirsaglam.com/kardiyo/");
                sw = trSwitch.isChecked();
                i.putExtra("sw", sw);
                startActivity(i);
            }
        });




        trSwitch.setChecked(sw);

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            // Geri düğmesine basıldığında yapılacak işlemler
            // Burada belirli bir aktiviteye yönlendirebilirsiniz
            Intent intent = new Intent(this, menu.class);
            sw = trSwitch.isChecked();
            intent.putExtra("sw", sw);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}