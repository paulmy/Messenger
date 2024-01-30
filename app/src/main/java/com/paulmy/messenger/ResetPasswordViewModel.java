package com.paulmy.messenger;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ResetPasswordViewModel extends ViewModel {
    private FirebaseAuth auth;
    private  MutableLiveData<FirebaseUser> user = new MutableLiveData<>();

    private  MutableLiveData<String> error = new MutableLiveData<>();
    private  MutableLiveData<Boolean> sendMail = new MutableLiveData<>(false);

    public MutableLiveData<Boolean> getSendMail() {
        return sendMail;
    }

    public LiveData<String> getError() {
        return error;
    }

    public LiveData<FirebaseUser> getUser() {
        return user;
    }


    public void resetPassword(String email) {
        auth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        sendMail.setValue(true);
                    }
                })
                .addOnFailureListener(e -> error.setValue(e.getMessage()));
    }

    public ResetPasswordViewModel() {
        auth = FirebaseAuth.getInstance();

        auth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null)
                    user.setValue(firebaseAuth.getCurrentUser());

            }
        });

    }
}
