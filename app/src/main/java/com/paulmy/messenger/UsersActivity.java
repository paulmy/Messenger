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
    public static Intent newIntent(Context context,String currentUserId) {
        Intent intent = new Intent(context, UsersActivity.class);
        intent.putExtra(EXTRA_CURRENT_USER_ID,currentUserId);
        return intent;
    }

    private UserAdapter userAdapter;

    private UsersViewModel usersViewModel;
    private static final String EXTRA_CURRENT_USER_ID = "currentUserID";
    private String currentUserId;
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

        currentUserId=getIntent().getStringExtra(EXTRA_CURRENT_USER_ID);
        userAdapter.setOnUserClickListener(new UserAdapter.OnUserClickListener() {
            @Override
            public void onClickUser(User user) {

                startActivity(ChatActivity.newIntent(UsersActivity.this,currentUserId,user.getId()));
            }
        });
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

    @Override
    protected void onPause() {
        super.onPause();
        usersViewModel.setUserOnline(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        usersViewModel.setUserOnline(true);
    }
}