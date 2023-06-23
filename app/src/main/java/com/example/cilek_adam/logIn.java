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
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;

public class logIn extends AppCompatActivity {
    TextInputEditText editTextEmail, editTextPassword;
    Button loginButton;
    ProgressBar progressBar;
    FirebaseAuth mAuth;

    Switch switchB;
    TextView tV;
    boolean sw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_name);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.barColor)));

         sw = getIntent().getBooleanExtra("sw", false);

        mAuth = FirebaseAuth.getInstance();
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        progressBar = findViewById(R.id.loginProgressBar);
        tV= findViewById(R.id.logTextView);
        switchB = findViewById(R.id.loginSwitch);

        switchB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    editTextPassword.setHint(R.string.register_password_E);
                    tV.setText(R.string.main_logIn_E);
                    loginButton.setText(R.string.main_logIn_E);
                }else{
                    editTextPassword.setHint(R.string.register_password_T);
                    tV.setText(R.string.main_logIn_T);
                    loginButton.setText(R.string.main_logIn_T);
                }
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String email,password;
                email = String.valueOf(editTextEmail.getText().toString());
                password = String.valueOf(editTextPassword.getText().toString());
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(logIn.this, "Enter email", Toast.LENGTH_SHORT).show();
                    return;
                } else if(TextUtils.isEmpty(password)){
                    Toast.makeText(logIn.this, "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    if (switchB.isChecked()){
                                        Toast.makeText(logIn.this, "Successfully Log In!",
                                                Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(logIn.this, "Giriş Yapıldı!",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                    Intent intent = new Intent(getApplicationContext(), menu.class);
                                    sw = switchB.isChecked();
                                    intent.putExtra("sw", sw);
                                    startActivity(intent);
                                    finish();

                                } else {
                                    // If sign in fails, display a message to the user.
                                    if (switchB.isChecked()){
                                        Toast.makeText(logIn.this, "Authentication Failed!",
                                                Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(logIn.this, "Giriş Yapılamadı!",
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