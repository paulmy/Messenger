package com.paulmy.messenger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private static final String TAG = "FirebaseAuth";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        String email = "paulmy@yandex.ru";
        String password = "111111";
         mAuth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
             @Override
             public void onSuccess(Void unused) {
                 Log.d(TAG, "Success " + email);
             }
         }).addOnFailureListener(new OnFailureListener() {
             @Override
             public void onFailure(@NonNull Exception e) {
                 Log.d(TAG, e.getMessage());
             }
         });}

      /*  mAuth.signOut();
      //  Log.w(TAG, "getCurrentUser " + user.getUid() + " " + user.getEmail() + " isEmailVerified " + user.isEmailVerified());

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "createUserWithEmail:success " + mAuth.getUid());


                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user != null) {
                        // Name, email address, and profile photo Url
                        String name = user.getDisplayName();

                        String email1 = user.getEmail();
                        // Uri photoUrl = user.getPhotoUrl();

                        // Check if user's email is verified
                        boolean emailVerified = user.isEmailVerified();

                        // The user's ID, unique to the Firebase project. Do NOT use this value to
                        // authenticate with your backend server, if you have one. Use
                        // FirebaseUser.getIdToken() instead.
                        String uid = user.getUid();

                        mAuth.updateCurrentUser(user);
                        Log.w(TAG, "getCurrentUser " + uid + " " + email1 + " isEmailVerified " + emailVerified);


                    } else {
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());

                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "" +e.getMessage());
            }
        });
    }*/

    //  mAuth.signOut();


}