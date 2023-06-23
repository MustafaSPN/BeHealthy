package com.example.cilek_adam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;

public class Sports extends AppCompatActivity {

    UserInfo info;

    EditText minutesInput;
    TextView headerText, descriptionText;
    Spinner sportSpinner;
    Switch sportsSwitch;
    Button saveButton;
    DatabaseReference mReference;
    FirebaseUser mUser;
    FirebaseAuth mAuth;
    boolean sw;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>" + getString(R.string.sports_header_T) + "</font>"));
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_name);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.barColor)));
        sw = getIntent().getBooleanExtra("sw", false);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        info = new UserInfo();
        headerText = findViewById(R.id.sports_header);
        descriptionText = findViewById(R.id.sports_descriptionText);
        sportSpinner = findViewById(R.id.sports_spinner);
        sportsSwitch = findViewById(R.id.sportsSwitch);
        saveButton = findViewById(R.id.sports_saveButton);
        minutesInput = findViewById(R.id.sports_minuteInput);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int caloriesBurned = 0;
                String sport = sportSpinner.getSelectedItem().toString();
                String minutesStr = minutesInput.getText().toString();

                if (TextUtils.isEmpty(minutesStr) || TextUtils.isEmpty(sport)){
                    if (!sportsSwitch.isChecked()){
                        Toast.makeText(Sports.this,"Lütfen Tüm Boşlukları Doldurun!",Toast.LENGTH_SHORT).show();}
                    else{
                        Toast.makeText(Sports.this,"Please Fill All Blanks!",Toast.LENGTH_SHORT).show();}
                    return;
                }

                int minutes = Integer.parseInt(minutesStr);

                switch (sport) {
                    case "Yürüyüş":
                    case "Walking":
                        caloriesBurned += (minutes * 5);
                        break;
                    case "Tempolu Koşu":
                    case "Jogging":
                        caloriesBurned += (minutes * 15);
                        break;
                    case "Bisiklet":
                    case "Cycling":
                        caloriesBurned += (minutes * 12);
                        break;
                    case "Yüzme":
                    case "Swimming":
                        caloriesBurned += (minutes * 12);
                        break;
                    case "Basketbol":
                    case "Basketball":
                        caloriesBurned += (minutes * 10);
                        break;
                    case "İp Atlama":
                    case "Jump Rope":
                        caloriesBurned += (minutes * 13);
                        break;
                    case "Jimnastik":
                    case "Gymnastics":
                        caloriesBurned += (minutes * 5);
                        break;
                }

                if (!sportsSwitch.isChecked()){
                    Toast.makeText(Sports.this, "Güncelleme Başarılı!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Sports.this, "Updated Successfully!", Toast.LENGTH_SHORT).show();
                }
                Calendar simdikiZaman = Calendar.getInstance();
                int yil = simdikiZaman.get(Calendar.YEAR);
                int ay = simdikiZaman.get(Calendar.MONTH) + 1; // Ay başlangıcı 0'dan başladığı için 1 eklenir
                int gun = simdikiZaman.get(Calendar.DAY_OF_MONTH);

                String date = String.valueOf(gun)+String.valueOf(ay)+String.valueOf(yil);


                mReference =  FirebaseDatabase.getInstance().getReference("Users").child(mUser.getUid()).child(date);
                HashMap<String,String> mData = new HashMap<>();
                info.addCalorieBurn(caloriesBurned);
                mData.put("burnedCal",String.valueOf(info.getCalorie_burn()));
                mData.put("takenCal",String.valueOf(info.getCalorie_taken()));
                mData.put("water",String.valueOf(info.getWater()));
                mReference.setValue(mData);




                Intent intent = new Intent(getApplicationContext(),menu.class);
                sw = sportsSwitch.isChecked();
                intent.putExtra("sw", sw);
                startActivity(intent);
                finish();
            }
        });

        ArrayAdapter<CharSequence> adapterE = ArrayAdapter.createFromResource(this,
                R.array.sports_spinner_E, R.layout.spinnerlayout);

        ArrayAdapter<CharSequence> adapterT = ArrayAdapter.createFromResource(this,
                R.array.sports_spinner_T, R.layout.spinnerlayout);
        sportsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {


                if (isChecked) {
                    getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>" + getString(R.string.sports_header_E) + "</font>"));
                    headerText.setText(R.string.sports_header_E);
                    descriptionText.setText(R.string.sports_descriptionText_E);
                    sportSpinner.setAdapter(adapterE);
                    saveButton.setText(R.string.sports_saveButton_E);
                } else {
                    getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>" + getString(R.string.sports_header_T) + "</font>"));
                    headerText.setText(R.string.sports_header_T);
                    descriptionText.setText(R.string.sports_descriptionText_T);
                    sportSpinner.setAdapter(adapterT);
                    saveButton.setText(R.string.sports_saveButton_T);
                }
            }
        });
    sportsSwitch.setChecked(sw);


    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            // Geri düğmesine basıldığında yapılacak işlemler
            // Burada belirli bir aktiviteye yönlendirebilirsiniz
            Intent intent = new Intent(this, menu.class);
            sw = sportsSwitch.isChecked();
            intent.putExtra("sw", sw);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}