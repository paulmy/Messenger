package com.paulmy.messenger;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;
import com.paulmy.messenger.databinding.ActivityLoginBinding;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.buttonLogin.setOnClickListener(v -> {
            String email = binding.editEmailAddress.getText().toString().trim();
            String password = binding.editPassword.getText().toString().trim();
            if (email.isEmpty() || password.isEmpty()) {
                Snackbar.make(binding.getRoot(), "некорректный ввод", Snackbar.LENGTH_LONG).show();
            } else {
                //TODO: реализовать переход на окно авторизованного пользователя
             //   startActivity(UserListActivity.newIntent(this));

            }
        });
        binding.forgotpasswordTv.setOnClickListener(v -> {
            startActivity(ForgotActivity.newIntent(this));
        });
        binding.registerTv.setOnClickListener(v -> {
            //TODO: реализовать переход на окно с регистрации
            startActivity(RegisterActivity.newIntent(this));

        });


    }
}
