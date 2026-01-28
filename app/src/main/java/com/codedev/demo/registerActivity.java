package com.codedev.demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class registerActivity extends AppCompatActivity {
    private EditText email;
    private EditText passwordacc;
    private EditText mobile;
    private Button save;
    private TextView tvLoginHere;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email = findViewById(R.id.email);
        mobile = findViewById(R.id.mobile);
        passwordacc = findViewById(R.id.passwordacc);
        save = findViewById(R.id.save);
        auth = FirebaseAuth.getInstance();
        tvLoginHere=findViewById(R.id.tvLoginHere);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String getEmail = email.getText().toString();
                String getMobile = mobile.getText().toString();
                String getPassword = passwordacc.getText().toString();

                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("email", getEmail);
                hashMap.put("mobile", getMobile);
                hashMap.put("password", getPassword);

                if (TextUtils.isEmpty(getEmail) || TextUtils.isEmpty(getPassword) || TextUtils.isEmpty(getMobile)) {
                    Toast.makeText(registerActivity.this, "Enter email, password and mobile", Toast.LENGTH_SHORT).show();
                } else {
                    regis(getEmail, getPassword, hashMap);
                }
            }


            private void regis(String getEmail, String getPassword, final HashMap<String, Object> hashMap) {
                auth.createUserWithEmailAndPassword(getEmail, getPassword).addOnCompleteListener(registerActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Only add to firestore if auth is successful
                            FirebaseFirestore.getInstance().collection("user")
                                    .add(hashMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                             Toast.makeText(registerActivity.this, "User Registered Successfully.", Toast.LENGTH_SHORT).show();
                                             startActivity(new Intent(registerActivity.this, MainActivity.class));
                                             finish();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(registerActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else {
                            if (task.getException() instanceof com.google.firebase.auth.FirebaseAuthUserCollisionException) {
                                Toast.makeText(registerActivity.this, "Account with this email already exists!", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(registerActivity.this, "." + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
        });
        tvLoginHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(registerActivity.this,MainActivity.class));
            }
        });
    }

}