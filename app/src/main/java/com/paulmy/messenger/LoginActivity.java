package com.paulmy.messenger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseUser;
import com.paulmy.messenger.databinding.ActivityLoginBinding;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        observeViewModel();
        setupClickListeners();


    }

    private Boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher mat = pattern.matcher(email);
        return mat.matches();
    }

    private void setupClickListeners() {
        binding.buttonLogin.setOnClickListener(v -> {
            String email = binding.editEmailAddress.getText().toString().trim();
            String password = binding.editPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Snackbar.make(binding.getRoot(), "некорректный ввод", Snackbar.LENGTH_LONG).show();
            } else {
                if (isValidEmail(email)){
                    loginViewModel.login(email, password);
            } else {
                Toast.makeText(this, "NotCorrect Address", Toast.LENGTH_LONG).show();
            }
            }
        });
        binding.forgotpasswordTv.setOnClickListener(v ->
        {
            String email = binding.editEmailAddress.getText().toString().trim();
            if (isValidEmail(email)) {
                startActivity(ResetPasswordActivity.newIntent(this, email));
            } else {
                Toast.makeText(this, "NotCorrect Address", Toast.LENGTH_LONG).show();
            }
        });
        binding.registerTv.setOnClickListener(v ->
        {
            //TODO: реализовать переход на окно с регистрации
         //  String email = binding.editEmailAddress.getText().toString().trim();


                startActivity(RegistrationActivity.newIntent(this));

        });

    }

    private void observeViewModel() {
        loginViewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errorMessage) {
                if (errorMessage != null)
                    Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_LONG).show();
            }
        });
        loginViewModel.getUser().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null) {
                    Intent intent = UsersActivity.newIntent(LoginActivity.this);
                    startActivity(intent);
                    finish();
                }
                // Toast.makeText(LoginActivity.this, "Authorized", Toast.LENGTH_LONG).show();
            }
        });
    }
    public static Intent newIntent(Context context){
        return new Intent(context,LoginActivity.class);

    }
}
