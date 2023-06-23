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

import java.util.HashMap;


public class Register2 extends AppCompatActivity {
    EditText nameET,yearsET,weightET,heightET;
    TextView nameTV,yearsTV,weightTV,heightTV,menTV,womenTV,reg2TV;
Switch sexS,langS;
Button saveB;
DatabaseReference mReference;
FirebaseUser mUser;
FirebaseAuth mAuth;
boolean sw;
    HashMap<String,String> mData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        sw = getIntent().getBooleanExtra("sw", false);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_name);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.barColor)));
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mReference = FirebaseDatabase.getInstance().getReference();
        nameET = findViewById(R.id.nameEditText);
        yearsET = findViewById(R.id.yearsEditText);
        weightET = findViewById(R.id.weightEditText);
        heightET = findViewById(R.id.heightEditText);
        saveB = findViewById(R.id.updateButton);
        sexS = findViewById(R.id.reg2Switch);
        nameTV = findViewById(R.id.nameText);
        yearsTV = findViewById(R.id.yearsText);
        weightTV = findViewById(R.id.weightText);
        heightTV = findViewById(R.id.heightText);
        menTV = findViewById(R.id.menText);
        womenTV = findViewById(R.id.womenText);
        reg2TV = findViewById(R.id.reg2TextView);
        langS = findViewById(R.id.accountSwitch);
        langS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    nameTV.setText(R.string.reg2_name_E);
                    nameET.setHint(R.string.reg2_name_E);
                    yearsTV.setText(R.string.reg2_years_E);
                    yearsET.setHint(R.string.reg2_years_E);
                    weightTV.setText(R.string.reg2_weight_E);
                    weightET.setHint(R.string.reg2_weight_E);
                    heightTV.setText(R.string.reg2_height_E);
                    heightET.setHint(R.string.reg2_height_E);
                    menTV.setText(R.string.reg2_men_E);
                    womenTV.setText(R.string.reg2_women_e);
                    saveB.setText(R.string.reg2_save_E);
                    reg2TV.setText(R.string.reg2_enterinfo_E);
                }else{
                    reg2TV.setText(R.string.reg2_enterinfo_T);
                    nameTV.setText(R.string.reg2_name_T);
                    nameET.setHint(R.string.reg2_name_T);
                    yearsTV.setText(R.string.reg2_years_T);
                    yearsET.setHint(R.string.reg2_years_T);
                    weightTV.setText(R.string.reg2_weight_T);
                    weightET.setHint(R.string.reg2_weight_T);
                    heightTV.setText(R.string.reg2_height_T);
                    heightET.setHint(R.string.reg2_height_T);
                    menTV.setText(R.string.reg2_men_T);
                    womenTV.setText(R.string.reg2_women_T);
                    saveB.setText(R.string.reg2_save_T);
                }
            }
        });


        saveB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameET.getText().toString();
                String sex;
                if(sexS.isChecked()){
                    sex = "Women";
                }else{
                    sex = "Men";
                }
                String years = yearsET.getText().toString();
                String weight = weightET.getText().toString();
                String height = heightET.getText().toString();

             if(!TextUtils.isEmpty(name)&&!TextUtils.isEmpty(years)&&!TextUtils.isEmpty(weight)&&!TextUtils.isEmpty(height)){

                 mData = new HashMap<>();
                 mData.put("userID",mUser.getUid());
                 mData.put("name",name);
                 mData.put("years",years);
                 mData.put("weight",weight);
                 mData.put("height",height);
                 mData.put("sex",sex);

                 mReference.child("Users").child(mUser.getUid()).setValue(mData)
                         .addOnCompleteListener(Register2.this, new OnCompleteListener<Void>() {
                             @Override
                             public void onComplete(@NonNull Task<Void> task) {
                                 if(task.isSuccessful()){
                                     if (langS.isChecked()){
                                         Toast.makeText(Register2.this, "Saved Successfully!",
                                                 Toast.LENGTH_SHORT).show();
                                     }else{
                                         Toast.makeText(Register2.this, "Kayıt İşlemi Başarılı!",
                                                 Toast.LENGTH_SHORT).show();
                                     }
                                     Intent intent = new Intent(getApplicationContext(),menu.class);
                                     sw = langS.isChecked();
                                     intent.putExtra("sw", sw);
                                     startActivity(intent);
                                     finish();
                                 }else{
                                     Toast.makeText(Register2.this, task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                 }
                             }
                         });


             }else{
                 if (langS.isChecked()){
                     Toast.makeText(Register2.this, "Fill All Blanks!",
                             Toast.LENGTH_SHORT).show();
                 }else{
                     Toast.makeText(Register2.this, "Boşlukları Doldurun!",
                             Toast.LENGTH_SHORT).show();
                 }
             }
            }
        });
        langS.setChecked(sw);

    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            // Geri düğmesine basıldığında yapılacak işlemler
            // Burada belirli bir aktiviteye yönlendirebilirsiniz
            Intent intent = new Intent(this, MainActivity.class);
            sw = langS.isChecked();
            intent.putExtra("sw", sw);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}