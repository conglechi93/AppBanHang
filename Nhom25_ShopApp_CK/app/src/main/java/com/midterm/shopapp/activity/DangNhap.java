package com.midterm.shopapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.midterm.shopapp.R;

public class DangNhap extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText edt_Email;
    EditText edt_Pass;
    TextView tv_SignUp;
    Button btn_SignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        mAuth = FirebaseAuth.getInstance();

        Anhxa();
        btn_SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });

        tv_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), com.midterm.shopapp.activity.DangKy.class);
                startActivity(intent);
            }
        });
    }
    public void Anhxa()
    {
        edt_Email = (EditText) findViewById(R.id.edt_email);
        edt_Pass = (EditText) findViewById(R.id.edt_password);
        btn_SignIn = (Button) findViewById(R.id.btn_Login);
        tv_SignUp = (TextView) findViewById(R.id.tv_sign_up);
    }
    public void Login()
    {
        String Email = edt_Email.getText().toString();
        String Pass = edt_Pass.getText().toString();
        if(Email.isEmpty())
        {
            edt_Email.setError("Email is required");
            edt_Email.requestFocus();
            return;
        }   
        if(Pass.isEmpty())
        {
            Toast.makeText(getApplicationContext(),"Login susscessfully !",Toast.LENGTH_SHORT).show();
            edt_Pass.setError("Pass is required");
            edt_Pass.requestFocus();
            return;
        }
        mAuth.signInWithEmailAndPassword(Email,Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(),"Login susscessfully !",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DangNhap.this,MainActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Login Fail!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}