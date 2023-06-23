package com.example.cilek_adam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;


public class Water extends AppCompatActivity {

    EditText su;
    UserInfo info;

    TextView baslik, aciklama,total;

    Button updateButton;

    Switch trSwitch;

    ProgressBar progressBar;
    FirebaseUser mUser;
    FirebaseAuth mAuth;
    DatabaseReference mReference;
    int total_water = 0;
    boolean sw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_name);
        info = new UserInfo();
        sw = getIntent().getBooleanExtra("sw", false);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>" + "Su" + "</font>"));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.barColor)));
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        su = findViewById(R.id.water_input);
        total = findViewById(R.id.total_water);
        baslik = findViewById(R.id.water_baslik);
        aciklama = findViewById(R.id.water_aciklama);

        updateButton = findViewById(R.id.water_button);

        progressBar = findViewById(R.id.water_progress);

        trSwitch = findViewById(R.id.waterSwitch);

        baslik.setText(R.string.water_baslikT);
        aciklama.setText(R.string.water_aciklamaT);
        updateButton.setText(R.string.water_buttonT);
        progressBar.setProgress(info.getWater());
        total.setText(String.valueOf(info.getWater())+" mL");
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int water;

                String wota;
                wota = su.getText().toString();
                if(wota.isEmpty()){
                    water = 0;
                }else{
                    water = Integer.parseInt(wota);
                }

                info.addWater(water);

                total.setText(String.valueOf(info.getWater())+" mL");
                su.setText("");
                progressBar.setProgress(info.getWater());
                Calendar simdikiZaman = Calendar.getInstance();
                int yil = simdikiZaman.get(Calendar.YEAR);
                int ay = simdikiZaman.get(Calendar.MONTH) + 1; // Ay başlangıcı 0'dan başladığı için 1 eklenir
                int gun = simdikiZaman.get(Calendar.DAY_OF_MONTH);

                String date = String.valueOf(gun)+String.valueOf(ay)+String.valueOf(yil);


                mReference =  FirebaseDatabase.getInstance().getReference("Users").
                        child(mUser.getUid()).child(date);
                HashMap<String,String> mData = new HashMap<>();
                mData.put("water",String.valueOf(info.getWater()));
                mData.put("takenCal",String.valueOf(info.getCalorie_taken()));
                mData.put("burnedCal",String.valueOf(info.getCalorie_burn()));
                mReference.setValue(mData);

            }
        });

   trSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    baslik.setText(R.string.water_baslikE);
                    aciklama.setText(R.string.water_aciklamaE);
                    updateButton.setText(R.string.water_buttonE);
                    getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>" + "Water" + "</font>"));

                } else {
                    baslik.setText(R.string.water_baslikT);
                    aciklama.setText(R.string.water_aciklamaT);
                    updateButton.setText(R.string.water_buttonT);
                    getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>" + "Su" + "</font>"));

                }
            }
        });

        trSwitch.setChecked(sw);




    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            // Geri düğmesine basıldığında yapılacak işlemler
            // Burada belirli bir aktiviteye yönlendirebilirsiniz
            Intent intent = new Intent(getApplicationContext(), menu.class);
            sw = trSwitch.isChecked();
            intent.putExtra("sw", sw);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}