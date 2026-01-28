package com.codedev.demo;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private TextView createAcc;
    private EditText edname;
    private EditText edpass;
    private Button button;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createAcc=findViewById(R.id.createAcc);
        edname=findViewById(R.id.edname);
        edpass=findViewById(R.id.edpass);
        button=findViewById(R.id.button);
        auth=FirebaseAuth.getInstance();



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=edname.getText().toString();
                String pass=edpass.getText().toString();
                if(TextUtils.isEmpty(name) || TextUtils.isEmpty(pass)){
                    Toast.makeText(MainActivity.this,"enter email and password both", Toast.LENGTH_SHORT).show();
                }
                else {
                    login(name,pass);
                }
            }
        });



        createAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,registerActivity.class));
            }
        });

        TextView forpass = findViewById(R.id.forpass);
        forpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText resetMail = new EditText(v.getContext());
                androidx.appcompat.app.AlertDialog.Builder passwordResetDialog = new androidx.appcompat.app.AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset Password?");
                passwordResetDialog.setMessage("Enter Your Email To Received Reset Link.");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton("Yes", new android.content.DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(android.content.DialogInterface dialog, int which) {
                        String mail = resetMail.getText().toString();
                        if (!TextUtils.isEmpty(mail)) {
                            auth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(MainActivity.this, "Reset Link Sent To Your Email.", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@androidx.annotation.NonNull Exception e) {
                                    Toast.makeText(MainActivity.this, "Error! Reset Link is Not Sent" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            Toast.makeText(MainActivity.this, "Please enter email", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                passwordResetDialog.setNegativeButton("No", new android.content.DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(android.content.DialogInterface dialog, int which) {
                        // Close the dialog
                    }
                });

                passwordResetDialog.create().show();
            }
        });
    }
    private void login(String name,String pass){
        auth.signInWithEmailAndPassword(name,pass).addOnSuccessListener(MainActivity.this, new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(MainActivity.this,"login successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, categoryActivity.class));

                MainActivity.this.finish();
            }


        });



    }
    @Override
    public void onStart() {



        super.onStart();
        FirebaseUser currentUser =auth.getCurrentUser();
        if(currentUser!=null){
            startActivity(new Intent(MainActivity.this,categoryActivity.class));
        }
    }
}