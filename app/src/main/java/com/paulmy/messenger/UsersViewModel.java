package com.paulmy.messenger;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UsersViewModel extends ViewModel {
    private final FirebaseAuth auth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference userReference;
    private MutableLiveData<FirebaseUser> user = new MutableLiveData<>();
    private MutableLiveData<List<User>> usersReferenceLiveData = new MutableLiveData<>();

    public LiveData<List<User>> getUsersReferenceLiveData() {
        return usersReferenceLiveData;
    }

    public LiveData<FirebaseUser> getUser() {
        return user;
    }

    public void setUserOnline(boolean online) {
        FirebaseUser firebaseUser = auth.getCurrentUser();
        if(firebaseUser==null){return;}
        userReference.child(firebaseUser.getUid()).child("online").setValue(online);
    }
    public UsersViewModel() {
        auth = FirebaseAuth.getInstance();
        auth.addAuthStateListener(firebaseAuth -> user.setValue(firebaseAuth.getCurrentUser()));
        firebaseDatabase = FirebaseDatabase.getInstance();
        userReference = firebaseDatabase.getReference("Users");
        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                List<User> userList = new ArrayList<>();
                FirebaseUser currentUser = auth.getCurrentUser();
                if (currentUser == null) {
                    return;
                }
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    User user = snapshot1.getValue(User.class);
                    if (!user.getId().equals(currentUser.getUid())) {
                        userList.add(user);
                        Log.d("USERS",user.toString());
                    }
                }
                usersReferenceLiveData.setValue(userList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void logOut() {
        auth.signOut();
    }
}
