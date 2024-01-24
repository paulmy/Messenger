package com.paulmy.messenger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.paulmy.messenger.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private static final String EMAIL = "email";
    private String email;

    public static Intent newIntent(Context context, String email) {

        Intent intent = new Intent(context, RegisterActivity.class);
        intent.putExtra(EMAIL, email);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        email = getIntent().getStringExtra(EMAIL);
        binding.editRegEmailAddress.setText(email);
        binding.buttonRegistration.setOnClickListener(v -> {


        });
    }
}