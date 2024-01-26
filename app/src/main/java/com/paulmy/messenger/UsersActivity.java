package com.paulmy.messenger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseUser;
import com.paulmy.messenger.databinding.ActivityUsersBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UsersActivity extends AppCompatActivity {
    public static Intent newIntent(Context context) {
        return new Intent(context, UsersActivity.class);
    }

    private UserAdapter userAdapter;

    private UsersViewModel usersViewModel;
    private ActivityUsersBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUsersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        usersViewModel = new ViewModelProvider(this).get(UsersViewModel.class);
        observeViewModel();
        userAdapter = new UserAdapter();
        binding.recyclerViewListUser.setAdapter(userAdapter);

        List<User> userList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Random random = new Random();
            userList.add(new User(i+"","UserName"+i,"LastName"+i,random.nextInt(30),random.nextBoolean()));
        }
        userAdapter.setUsers(userList);
    }

    public void observeViewModel() {
        usersViewModel.getUser().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser == null) {
                    Intent intent = LoginActivity.newIntent(UsersActivity.this);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.item_logout) {
            usersViewModel.logOut();
            finish();
        }

        return super.onOptionsItemSelected(item);

    }
}