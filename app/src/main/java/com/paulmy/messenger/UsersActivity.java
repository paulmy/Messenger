package com.paulmy.messenger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.paulmy.messenger.databinding.ActivityUsersBinding;

public class UsersActivity extends AppCompatActivity {
    public static Intent newIntent(Context context) {
        return new Intent(context, UsersActivity.class);
    }

    private UserAdapter userAdapter;

    private UsersViewModel usersViewModel;
    private  ActivityUsersBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUsersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        usersViewModel = new ViewModelProvider(this).get(UsersViewModel.class);
        observeViewModel();
        userAdapter = new UserAdapter();
        binding.recyclerViewListUser.setAdapter(userAdapter);
    }

    public void observeViewModel() {
        usersViewModel.getUser().observe(this, firebaseUser -> {
            if (firebaseUser == null) {
                Intent intent = LoginActivity.newIntent(UsersActivity.this);
                startActivity(intent);
                finish();
            }
        });
        usersViewModel.getUsersReferenceLiveData()
                .observe(this, users -> userAdapter.setUsers(users));
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