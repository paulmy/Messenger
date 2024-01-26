package com.paulmy.messenger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseUser;
import com.paulmy.messenger.databinding.ActivityForgotBinding;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResetPasswordActivity extends AppCompatActivity {
    private ActivityForgotBinding binding;
    private static final String EMAIL = "email";
    private String email;

    public static Intent newIntent(Context context, String email) {

        Intent intent = new Intent(context, ResetPasswordActivity.class);
        intent.putExtra(EMAIL, email);
        return intent;
    }

    private Boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher mat = pattern.matcher(email);
        return mat.matches();
    }

    private ResetPasswordViewModel resetPasswordViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        resetPasswordViewModel = new ViewModelProvider(this).get(ResetPasswordViewModel.class);

        email = getIntent().getStringExtra(EMAIL);
        binding.forgotEmail.setText(email);
        observeViewModel();
        onButtonClick();
    }

    public void onButtonClick() {
        Handler handler = new Handler(getMainLooper());

        binding.btnSendPassword.setOnClickListener(v -> {
                    email = binding.forgotEmail.getText().toString().trim();
                    if (email.isEmpty() || !isValidEmail(email)) {
                        Toast.makeText(this, "NotCorrect Address", Toast.LENGTH_LONG).show();
                    } else {
                        resetPasswordViewModel.resetPassword(email);

                         /*   mAuth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
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
                            });*/
                    }

                }
        );
    }

    private void observeViewModel() {
        resetPasswordViewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String error) {
                Snackbar.make(binding.getRoot(), error, Snackbar.LENGTH_LONG).show();
            }
        });
        resetPasswordViewModel.getSendMail().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean mailsand) {
                if (mailsand) {
                    Snackbar.make(binding.getRoot(), "Письмо отправлено! Проверьте, пожалуйста почту", Snackbar.LENGTH_LONG).show();
                    Intent intent = LoginActivity.newIntent(ResetPasswordActivity.this);
                    startActivity(intent);
                    finish();
                }

            }
        });
        resetPasswordViewModel.getUser().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {

            }
        });
    }

}