package com.example.ep4_moviesapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ep4_moviesapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    private EditText edtUser, edtPass;
    private Button btnRegister;
    private TextView txtGoLogin;
    private ProgressBar pbRegister;
    FirebaseAuth mAuth;

    @Override
    public void onStart(){
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
    }

    private void initView(){
        edtUser = findViewById(R.id.edtUser);
        edtPass = findViewById(R.id.edtPassword);
        btnRegister = findViewById(R.id.btnRegister);
        txtGoLogin = findViewById(R.id.txtLoginNow);
        pbRegister = findViewById(R.id.pbRegister);
        //mAuth = FirebaseAuth.getInstance();

        txtGoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pbRegister.setVisibility(View.VISIBLE);
                String email, password;
                email = edtUser.getText().toString().trim();
                password = edtPass.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    pbRegister.setVisibility(View.GONE);
                    Toast.makeText(RegisterActivity.this, "Ingrese un Email", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    pbRegister.setVisibility(View.GONE);
                    Toast.makeText(RegisterActivity.this, "Ingrese una Contraseña", Toast.LENGTH_LONG).show();
                    return;
                }
                if (password.length() < 6){
                    pbRegister.setVisibility(View.GONE);
                    Toast.makeText(RegisterActivity.this, "La contraseña debe tener como minimo 6 digitos.", Toast.LENGTH_LONG).show();
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    pbRegister.setVisibility(View.GONE);
                                    Toast.makeText(RegisterActivity.this, "Registro Exitoso.", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                } else {
                                    // If sign in fails, display a message to the user.
                                    pbRegister.setVisibility(View.GONE);
                                    Toast.makeText(RegisterActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });
    }
}