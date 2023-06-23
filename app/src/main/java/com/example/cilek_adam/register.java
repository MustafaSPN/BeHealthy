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
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class register extends AppCompatActivity {
    TextInputEditText editTextEmail, editTextPassword;
    Button regButton;
    ProgressBar progressBar;
    FirebaseAuth mAuth;
    Switch switchB;
    TextView tV;
    boolean sw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_name);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.barColor)));

        sw = getIntent().getBooleanExtra("sw", false);

        mAuth = FirebaseAuth.getInstance();
        tV= findViewById(R.id.regTextView);
        editTextEmail = findViewById(R.id.regEmail);
        editTextPassword = findViewById(R.id.regPassword);
        regButton = findViewById(R.id.registerButton);
        progressBar = findViewById(R.id.regProgressBar);
        switchB = findViewById(R.id.fatSwitch);
        switchB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    editTextPassword.setHint(R.string.register_password_E);
                    tV.setText(R.string.main_register_E);
                    regButton.setText(R.string.main_register_E);
                }else{
                    editTextPassword.setHint(R.string.register_password_T);
                    tV.setText(R.string.main_register_T);
                    regButton.setText(R.string.main_register_T);
                }
            }
        });
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String email,password;
                email = String.valueOf(editTextEmail.getText().toString());
                password = String.valueOf(editTextPassword.getText().toString());
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(register.this, "Enter email", Toast.LENGTH_SHORT).show();
                    return;
                } else if(TextUtils.isEmpty(password)){
                    Toast.makeText(register.this, "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);

                                if (task.isSuccessful()) {
                                    if (switchB.isChecked()){
                                        Toast.makeText(register.this, "Account created!",
                                                Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(register.this, "Hesap Oluşturuldu!",
                                                Toast.LENGTH_SHORT).show();
                                    }

                                    Intent intent = new Intent(getApplicationContext(),Register2.class);
                                    sw = switchB.isChecked();
                                    intent.putExtra("sw", sw);
                                    startActivity(intent);
                                    finish();

                                } else {
                                    // If sign in fails, display a message to the user.
                                    if (switchB.isChecked()){
                                        Toast.makeText(register.this, "Authentication Failed!",
                                                Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(register.this, "Hesap Oluşturulamadı!",
                                                Toast.LENGTH_SHORT).show();
                                    }

                                }
                            }
                        });


            }
        });
        switchB.setChecked(sw);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            // Geri düğmesine basıldığında yapılacak işlemler
            // Burada belirli bir aktiviteye yönlendirebilirsiniz
            Intent intent = new Intent(this, MainActivity.class);
            sw = switchB.isChecked();
            intent.putExtra("sw", sw);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}