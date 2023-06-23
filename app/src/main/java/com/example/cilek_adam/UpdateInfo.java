package com.example.cilek_adam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateInfo extends AppCompatActivity {
EditText heightET,yearsET,weightET;
TextView heightTV,yearsTV,weightTV,update;
Button updateB;
Switch updateS;
FirebaseUser mUser;
FirebaseAuth mAuth;
DatabaseReference mReference;
boolean sw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_info);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_name);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.barColor)));
        sw = getIntent().getBooleanExtra("sw", false);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mReference = FirebaseDatabase.getInstance().getReference("Users").child(mUser.getUid());

        heightET = findViewById(R.id.updateHeightEditText);
        heightTV = findViewById(R.id.updateHeightText);
        yearsET = findViewById(R.id.updateYearsEditText);
        yearsTV = findViewById(R.id.updateYearsText);
        weightET = findViewById(R.id.updateWeightEditText);
        weightTV = findViewById(R.id.updateWeightText);
        update = findViewById(R.id.updateTextView);
        updateB = findViewById(R.id.updateButton);
        updateS = findViewById(R.id.accountSwitch);
        updateS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    heightET.setHint(R.string.reg2_height_E);
                    heightTV.setText(R.string.reg2_height_E);
                    yearsET.setHint(R.string.reg2_years_E);
                    yearsTV.setText(R.string.reg2_years_E);
                    weightTV.setText(R.string.reg2_weight_E);
                    weightET.setHint(R.string.reg2_weight_E);
                    updateB.setText(R.string.update_update_E);
                    update.setText(R.string.update_updateInfo_E);
                }else{
                    heightET.setHint(R.string.reg2_height_T);
                    heightTV.setText(R.string.reg2_height_T);
                    yearsET.setHint(R.string.reg2_years_T);
                    yearsTV.setText(R.string.reg2_years_T);
                    weightTV.setText(R.string.reg2_weight_T);
                    weightET.setHint(R.string.reg2_weight_T);
                    updateB.setText(R.string.update_update_T);
                    update.setText(R.string.update_updateInfo_T);

                }
            }
        });
        updateB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String height,weight,years;
                height = heightET.getText().toString();
                weight = weightET.getText().toString();
                years = yearsET.getText().toString();
                if(!TextUtils.isEmpty(height)&&!TextUtils.isEmpty(years)&&!TextUtils.isEmpty(weight)){
                mReference.child("weight").removeValue();
                mReference.child("height").removeValue();
                mReference.child("years").removeValue();
                mReference.child("weight").setValue(weight);
                mReference.child("height").setValue(height);
                mReference.child("years").setValue(years).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            if (!updateS.isChecked()){
                            Toast.makeText(UpdateInfo.this, "Güncelleme Başarılı!", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(UpdateInfo.this, "Updated Successfully!", Toast.LENGTH_SHORT).show();
                            }
                            Intent intent = new Intent(getApplicationContext(),Account.class);
                            sw = updateS.isChecked();
                            intent.putExtra("sw", sw);
                            startActivity(intent);
                            finish();
                        }else{
                            if (!updateS.isChecked()) {
                                Toast.makeText(UpdateInfo.this, "Güncelleme Başarısız!", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(UpdateInfo.this, "Update Error!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });








                }else{
                    if (!updateS.isChecked()){
                    Toast.makeText(UpdateInfo.this,"Lütfen Tüm Boşlukları Doldurun!",Toast.LENGTH_SHORT).show();}
                    else{
                        Toast.makeText(UpdateInfo.this,"Please Fill All Blanks!",Toast.LENGTH_SHORT).show();}
                    }
                }




        });
updateS.setChecked(sw);




    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            // Geri düğmesine basıldığında yapılacak işlemler
            // Burada belirli bir aktiviteye yönlendirebilirsiniz
            Intent intent = new Intent(this, Account.class);
            sw = updateS.isChecked();
            intent.putExtra("sw", sw);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}