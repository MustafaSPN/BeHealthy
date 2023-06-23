package com.example.cilek_adam;

import androidx.appcompat.app.AppCompatActivity;
import java.lang.Math;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

public class MuscleAndFat extends AppCompatActivity {
TextView fatTV,belTV,boyunTV,fatRatioTV1,fatRatioTV2,kalcaTV,yagKGTV1,yagKGTV2;
Button calculateFat;
Switch fatS;
boolean sw;
double fat,fatkg;
UserInfo info;
EditText belET,boyunET,kalcaET;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muscle_and_fat);
        sw = getIntent().getBooleanExtra("sw", false);

        info = new UserInfo();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>" + "Yağ Oranı"+ "</font>"));
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_name);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.barColor)));
        fatTV=findViewById(R.id.fatTextView);
        belTV = findViewById(R.id.belTextView);
        boyunTV = findViewById(R.id.boyunTextView);
        fatRatioTV1 = findViewById(R.id.fatRatioTextView1);
        fatRatioTV2 = findViewById(R.id.fatRatioTextView2);
        calculateFat = findViewById(R.id.fatCalculateButton);
        fatS = findViewById(R.id.fatSwitch);
        belET = findViewById(R.id.BelEditTextNumber);
        boyunET = findViewById(R.id.boyunEditTextNumber);
        kalcaET = findViewById(R.id.kalcaEditTextNumber);
        kalcaTV = findViewById(R.id.kalcaTextView);
        yagKGTV1 = findViewById(R.id.yagKgTV1);
        yagKGTV2 = findViewById(R.id.yagKgTV2);

        fatTV.setText(R.string.fat_calculatefatratio_T);
        belTV.setText(R.string.fat_waistsize_T);
        boyunTV.setText(R.string.fat_necksize_T);
        kalcaTV.setText(R.string.fat_hipsize_T);
        calculateFat.setText(R.string.fat_calculatefatratio_T);
        fatRatioTV1.setText(R.string.fat_fatratio_T);
        yagKGTV1.setText(R.string.fat_fatmass_T);
        fatS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    fatTV.setText(R.string.fat_calculatefatratio_E);
                    belTV.setText(R.string.fat_waistsize_E);
                    boyunTV.setText(R.string.fat_necksize_E);
                    kalcaTV.setText(R.string.fat_hipsıze_E);
                    calculateFat.setText(R.string.fat_calculatefatratio_E);
                    fatRatioTV1.setText(R.string.fat_fatratio_E);
                    yagKGTV1.setText(R.string.fat_fatmass_E);
                    getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>" + "Fat Ratio" + "</font>"));
                }else{
                    fatTV.setText(R.string.fat_calculatefatratio_T);
                    belTV.setText(R.string.fat_waistsize_T);
                    boyunTV.setText(R.string.fat_necksize_T);
                    kalcaTV.setText(R.string.fat_hipsize_T);
                    calculateFat.setText(R.string.fat_calculatefatratio_T);
                    fatRatioTV1.setText(R.string.fat_fatratio_T);
                    yagKGTV1.setText(R.string.fat_fatmass_T);
                    getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>" + "Yağ Oranı" + "</font>"));
                }
            }
        });

        calculateFat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               double   bel = Double.parseDouble(belET.getText().toString());
               double boyun = Double.parseDouble(boyunET.getText().toString());
               double kalca = Double.parseDouble(kalcaET.getText().toString());
                if (info.getSex().equals("Men")){
                    fat = ((495.0 / (1.0324-(0.19077*(double)Math.log10(bel-boyun))+(0.15456*(double) Math.log10((double) info.getHeight()))))-450.0);
                    fatRatioTV2.setText("%"+String.format("%.1f",fat));
                    fatkg = info.getWeight()*fat/100.0;
                    yagKGTV2.setText(String.format("%.0f",fatkg)+" Kg");


                }else if (info.getSex().equals("Women")){
                    fat = ((495/(1.29579-(0.35004*(double) Math.log10(bel+kalca-boyun))+(0.22100*(double) Math.log10(info.getHeight()))))-450.0);
                    fatRatioTV2.setText("%"+String.format("%.1f",fat));
                    fatkg = info.getWeight()*fat/100.0;
                    yagKGTV2.setText(String.format("%.0f",fatkg)+" Kg");
                }

            }
        });
        fatS.setChecked(sw);


    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            // Geri düğmesine basıldığında yapılacak işlemler
            // Burada belirli bir aktiviteye yönlendirebilirsiniz
            Intent intent = new Intent(this, menu.class);
            sw = fatS.isChecked();
            intent.putExtra("sw", sw);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}