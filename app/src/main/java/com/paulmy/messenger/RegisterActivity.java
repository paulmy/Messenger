package com.paulmy.messenger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.paulmy.messenger.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private static final String EMAIL = "email";
    private String email;
    private String password;

    public static Intent newIntent(Context context, String email) {

        Intent intent = new Intent(context, RegisterActivity.class);
        intent.putExtra(EMAIL, email);
        return intent;
    }
private String button_name;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        email = getIntent().getStringExtra(EMAIL);
        binding.editRegEmailAddress.setText(email);
        button_name = binding.buttonRegistration.getText().toString();
        Handler handler = new Handler(getMainLooper());
        mAuth = FirebaseAuth.getInstance();
        binding.buttonRegistration.setOnClickListener(v -> {
            email = binding.editRegEmailAddress.getText().toString().trim();
            password = binding.editRegEmailAddress.getText().toString().trim();

            mAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                int count = 5;

                @Override
                public void onSuccess(AuthResult authResult) {
                    Toast.makeText(getApplicationContext(), "Пользователь  " + email + " " + password + " создан", Toast.LENGTH_LONG).show();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while (count > 0) {
                                try {
                                    Thread.sleep(1000);
                                    count--;
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                           // String test = binding.buttonRegistration.getText().toString();
                                            binding.buttonRegistration.setText(button_name + " (" + count + ")");
                                        }
                                    });
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                                if (count == 0)
                                    finish();

                            }

                        }
                    }).start();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Что-то пошло не так, попробуйте позже", Toast.LENGTH_LONG).show();
                }
            });
        });
    }
}