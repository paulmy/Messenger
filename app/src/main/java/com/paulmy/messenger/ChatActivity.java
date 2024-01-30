package com.paulmy.messenger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.paulmy.messenger.databinding.ActivityChatBinding;

public class ChatActivity extends AppCompatActivity {
    private static final String EXTRA_CURRENT_USER = "currentUserID";
    private MessagesAdapter messagesAdapter;

    private ActivityChatBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
       // messagesAdapter = new MessagesAdapter();
    }
    public static Intent getInstance(Context context,String currentUserID){
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(EXTRA_CURRENT_USER,currentUserID);
        return intent;
    }
}