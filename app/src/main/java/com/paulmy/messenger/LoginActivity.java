package com.paulmy.messenger;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.paulmy.messenger.databinding.ActivityLoginBinding;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            String email = binding.editEmailAddress.getText().toString().trim();
            Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
            Matcher mat = pattern.matcher(email);
            if (mat.matches()) {
                startActivity(ResetPasswordActivity.newIntent(this, email));
            }else{
                Toast.makeText(this, "NotCorrect Address", Toast.LENGTH_LONG).show();
            }
        });
        binding.registerTv.setOnClickListener(v -> {
            //TODO: реализовать переход на окно с регистрации

            String email = binding.editEmailAddress.getText().toString().trim();
            Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
            Matcher mat = pattern.matcher(email);
            if (mat.matches()) {
                startActivity(RegisterActivity.newIntent(this, email));
            }else{
                Toast.makeText(this, "NotCorrect Address", Toast.LENGTH_LONG).show();
            }

        });


    }
}
