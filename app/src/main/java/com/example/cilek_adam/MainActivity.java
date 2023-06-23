package com.example.cilek_adam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {
Switch switchB;
    Button b2;
    Button b1;
    boolean sw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.barColor)));
        sw = getIntent().getBooleanExtra("sw", false);
        switchB = findViewById(R.id.fatSwitch);

         b1 =findViewById(R.id.button);

         b2 = findViewById(R.id.button2);
        switchB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    b1.setText(R.string.main_logIn_E);
                    b2.setText(R.string.main_register_E);
                }else{
                    b1.setText(R.string.main_logIn_T);
                    b2.setText(R.string.main_register_T);
                }
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, logIn.class);
                sw = switchB.isChecked();
                i.putExtra("sw", sw);
                startActivity(i);
                finish();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, register.class);
                sw = switchB.isChecked();
                i.putExtra("sw", sw);
                startActivity(i);
            }
        });
        switchB.setChecked(sw);
    }
}