package com.paulmy.messenger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.paulmy.messenger.databinding.ActivityForgotBinding;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForgotActivity extends AppCompatActivity {
    private ActivityForgotBinding binding;
    private FirebaseAuth mAuth;

    public static Intent newIntent(Context context) {

        return new Intent(context, ForgotActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();
        Handler handler = new Handler(getMainLooper());
        binding.btnSendPassword.setOnClickListener(v -> {
                    String email = binding.forgotEmail.getText().toString().trim();
                    if (email.isEmpty()) {
                        Toast.makeText(this, "NotCorrect Address", Toast.LENGTH_LONG).show();
                    } else {
                        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
                        Matcher mat = pattern.matcher(email);
                        if (mat.matches()) {
                            mAuth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                                int count = 5;

                                @Override
                                public void onSuccess(Void unused) {
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
                                                            binding.titleForgot.setTextSize(20f);
                                                            binding.titleForgot.setBackgroundColor(Color.GREEN);
                                                            binding.titleForgot.setText("письмо успешно отправлено окно закроется через " + count);
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
                                    // Snackbar.make(binding.getRoot(), "Письмо отправлено", Snackbar.LENGTH_LONG).show();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Snackbar.make(binding.getRoot(), "Письмо не отправлено", Snackbar.LENGTH_LONG).show();
                                }
                            });
                        } else {
                            Toast.makeText(this, "NotCorrect Address", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );


    }

}