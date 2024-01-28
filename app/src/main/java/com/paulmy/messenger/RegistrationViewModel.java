package com.paulmy.messenger;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationViewModel extends ViewModel {
    private final FirebaseAuth auth;
    private final FirebaseDatabase firebaseDatabase;
    private final DatabaseReference databaseReference;
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

        auth.addAuthStateListener(firebaseAuth -> user.setValue(firebaseAuth.getCurrentUser()));
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");

    }

    public void registration(String email, String password, String name, String lastName, int age) {
        auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(authResult -> {
            FirebaseUser firebaseUser = authResult.getUser();
            if (firebaseUser == null) {
                return;
            }
            User user = new User(firebaseUser.getUid(), name, lastName, age, false);
            databaseReference.child(firebaseUser.getUid()).setValue(user);

        })
                .addOnFailureListener(e -> error.setValue(e.getMessage()));
    }

}
