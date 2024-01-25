package com.paulmy.messenger;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RegistrationViewModel extends ViewModel {
    private FirebaseAuth auth;
    private final MutableLiveData<FirebaseUser> user = new MutableLiveData<>();

    private final MutableLiveData<String> error = new MutableLiveData<>();

    public LiveData<String> getError() {
        return error;
    }

    public LiveData<FirebaseUser> getUser() {
        return user;
    }

    public RegistrationViewModel() {
        auth = FirebaseAuth.getInstance();

        auth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                user.setValue(firebaseAuth.getCurrentUser());

            }
        });

    }

    public void registration(String email, String password, String name, String lastName, int age) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnFailureListener(e -> error.setValue(e.getMessage()));
    }

}
