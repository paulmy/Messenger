package com.paulmy.messenger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.paulmy.messenger.databinding.ActivityRegisterBinding;

public class RegistrationActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private static final String EMAIL = "email";


    public static Intent newIntent(Context context) {
        return new Intent(context, RegistrationActivity.class);
    }

    private String button_name;

    private RegistrationViewModel registrationViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        button_name = binding.buttonRegistration.getText().toString();


        registrationViewModel = new ViewModelProvider(this).get(RegistrationViewModel.class);
        observeViewModel();

        binding.buttonRegistration.setOnClickListener(v -> {
            //  Handler handler = new Handler(getMainLooper());
            String email = binding.editRegEmailAddress.getText().toString().trim();
            String password = binding.editRegEmailAddress.getText().toString().trim();
            String name = binding.editUserName.getText().toString().trim();
            String lastName = binding.editUserLastName.getText().toString().trim();
            int age = Integer.parseInt(binding.editUserAge.getText().toString().trim());

            registrationViewModel.registration(email, password, name, lastName, age);


          /*  mAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
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
                //    Toast.makeText(getApplicationContext(), "Что-то пошло не так, попробуйте позже", Toast.LENGTH_LONG).show();
                }
            });
        });*/
        });
    }

    public void observeViewModel() {
        registrationViewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String error) {
                if (error != null)
                    Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
            }
        });
        registrationViewModel.getUser().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null) {
                    Intent intent = UsersActivity.newIntent(RegistrationActivity.this);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}