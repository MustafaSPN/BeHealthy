package com.example.cilek_adam;

import androidx.appcompat.app.AppCompatActivity;

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

public class Account extends AppCompatActivity {
UserInfo info;
TextView accountTV,nameTV,yearsTV,weightTV,heightTV,sexTV;
    TextView nameTV2,yearsTV2,weightTV2,heightTV2,sexTV2,BMITV2,BMITV3;
Switch Aswitch;
FirebaseAuth mAuth;
FirebaseUser mUser;
Button update;
boolean sw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>" + getString(R.string.account_myAccount_T) + "</font>"));
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_name);

        sw = getIntent().getBooleanExtra("sw", false);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.barColor)));
        info = new UserInfo();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        accountTV = findViewById(R.id.accountTV);
        nameTV = findViewById(R.id.accountNameTV);
        yearsTV = findViewById(R.id.accountYearsTV);
        weightTV = findViewById(R.id.accountWeightTV);
        heightTV = findViewById(R.id.accountHeightTV);
        sexTV= findViewById(R.id.accountSexTV);
        Aswitch = findViewById(R.id.accountSwitch);
        update = findViewById(R.id.accountupdatebutton);
        nameTV2 = findViewById(R.id.accountNameTV2);
        yearsTV2 = findViewById(R.id.accountYearsTV2);
        weightTV2 = findViewById(R.id.accountWeightTV2);
        heightTV2 = findViewById(R.id.accountHeightTV2);
        sexTV2= findViewById(R.id.accountSexTV2);
        BMITV2 = findViewById(R.id.accountBMITV2);
        BMITV3 = findViewById(R.id.accountBMITV3);

        nameTV2.setText(":  "+info.getName());
        yearsTV2.setText(":  "+info.getYearsString());
        weightTV2.setText(":  "+info.getWeightString());
        heightTV2.setText(":  "+info.getHeightString());
        if(info.getSex().equals("Men")){ sexTV2.setText(":  "+"Erkek");}
        else {sexTV2.setText(":  "+"Kadın");}
        BMITV2.setText(":  "+info.getBMIString());
        BMITV3.setText("-->  "+info.getBmiRateTR());


        Aswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    accountTV.setText(R.string.account_myAccount_E);
                    nameTV.setText(R.string.reg2_name_E );
                    yearsTV.setText(R.string.reg2_years_E);
                    weightTV.setText(R.string.reg2_weight_E );
                    heightTV.setText(R.string.reg2_height_E);
                    sexTV.setText(R.string.account_sex_E);
                    sexTV2.setText(":  "+info.getSex());
                    BMITV3.setText("-->  "+info.getBmiRate());
                    update.setText(R.string.update_updateInfo_E);
                    getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>" + getString(R.string.account_myAccount_E) + "</font>"));


                }else {
                    accountTV.setText(R.string.account_myAccount_T);
                    nameTV.setText(R.string.account_name_T);
                    yearsTV.setText(R.string.reg2_years_T);
                    weightTV.setText(R.string.reg2_weight_T);
                    heightTV.setText(R.string.reg2_height_T);
                    sexTV.setText(R.string.account_sex_T);
                    getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>" + getString(R.string.account_myAccount_T) + "</font>"));

                    if(info.getSex().equals("Men")){ sexTV2.setText(":  "+"Erkek");}
                    else {sexTV2.setText(":  "+"Kadın");}
                    BMITV3.setText("-->  "+info.getBmiRateTR());
                    update.setText(R.string.update_updateInfo_T);
                }
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UpdateInfo.class);
                sw = Aswitch.isChecked();
                intent.putExtra("sw", sw);
                startActivity(intent);
            }
        });
        Aswitch.setChecked(sw);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            // Geri düğmesine basıldığında yapılacak işlemler
            // Burada belirli bir aktiviteye yönlendirebilirsiniz
            Intent intent = new Intent(this, menu.class);
            sw = Aswitch.isChecked();
            intent.putExtra("sw", sw);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}