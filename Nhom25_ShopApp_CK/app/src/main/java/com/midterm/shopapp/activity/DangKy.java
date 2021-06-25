package com.midterm.shopapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.midterm.shopapp.R;
import com.midterm.shopapp.model.User;

import java.util.HashMap;

public class DangKy extends AppCompatActivity {
    private EditText edt_name,edt_email,edt_pass,edt_checkpass,edt_phone;
    private Button btn_register;
    private FirebaseAuth mAuth;
    private TextView tv_singIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        Anhxa();
        mAuth = FirebaseAuth.getInstance();

        tv_singIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DangKy.this,DangNhap.class));
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
            }
        });

    }
    public  void Anhxa()
    {
        edt_checkpass = (EditText) findViewById(R.id.edt_cfpass);
        edt_pass = (EditText) findViewById(R.id.edt_passwordRegister);
        edt_email = (EditText) findViewById(R.id.edt_emailRegister);
        edt_phone = (EditText) findViewById(R.id.edt_phone);
        edt_name = (EditText) findViewById(R.id.edt_name);
        btn_register = (Button) findViewById(R.id.btn_register);
        tv_singIn = (TextView) findViewById(R.id.tv_signIn1);
    }
    public void Register()
    {
        String Email = edt_email.getText().toString();
        String Pass = edt_pass.getText().toString();
        String cfPass = edt_checkpass.getText().toString();
        String Phone = edt_phone.getText().toString();
        String Name = edt_name.getText().toString();

        if(Email.isEmpty())
        {
            edt_email.setError("Email is required");
            edt_email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches())
        {
            edt_email.setError("Please provide valid email");
            edt_email.requestFocus();
            return;
        }
        if(Pass.isEmpty())
        {
            edt_pass.setError("Pass is required");
            edt_pass.requestFocus();
            return;
        }
        if(cfPass.isEmpty())
        {
            edt_checkpass.setError("Confirm pass is required");
            edt_checkpass.requestFocus();
            return;
        }


        if(Name.isEmpty())
        {
            edt_name.setError("Name is required");
            edt_name.requestFocus();
            return;
        }
        if(Phone.isEmpty())
        {
            edt_phone.setError("Phone is required");
            edt_phone.requestFocus();
            return;
        }
        if(!Pass.equals(cfPass))
        {
            edt_checkpass.setError("Your password don't match");
            edt_checkpass.requestFocus();
            return;
        }


        mAuth.createUserWithEmailAndPassword(Email,Pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                String uid = mAuth.getUid();
                HashMap<String,Object> hashMap = new HashMap<>();
                hashMap.put("id",uid);
                hashMap.put("email",Email);
                hashMap.put("Password",Pass);
                hashMap.put("Name",Name);
                hashMap.put("Phone",Phone);
                hashMap.put("Image","default");

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
                ref.child(uid).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(),"Register susscessfuly!",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(DangKy.this,DangNhap.class));
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Register Fail!",Toast.LENGTH_SHORT).show();
            }
        });

    }
}