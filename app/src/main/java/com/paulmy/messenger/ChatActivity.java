package com.paulmy.messenger;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.paulmy.messenger.databinding.ActivityChatBinding;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private static final String EXTRA_CURRENT_USER_ID = "currentUserID";
    private static final String EXTRA_OTHER_USER_ID = "otherUserID";
    private MessagesAdapter messagesAdapter;
    private String currentUserId;
    private String otherUserId;
    private ActivityChatBinding binding;
    private ChatViewModel chatViewModel;
    private ChatViewModelFactory chatViewModelFactory;
    private static final int NOTIFY_ID = 101;
    private static final String CHANNEL_ID = "Message channel";
    NotificationManagerCompat notificationManager;

    NotificationCompat.Builder notificationbuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        currentUserId = getIntent().getStringExtra(EXTRA_CURRENT_USER_ID);
        otherUserId = getIntent().getStringExtra(EXTRA_OTHER_USER_ID);
        chatViewModelFactory = new ChatViewModelFactory(currentUserId, otherUserId);

        chatViewModel = new ViewModelProvider(this, chatViewModelFactory).get(ChatViewModel.class);


        messagesAdapter = new MessagesAdapter(currentUserId);
        // messagesAdapter.setMessages(getListTest());
        binding.recyclerViewChat.setAdapter(messagesAdapter);

        observeViewModel();
        binding.sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = binding.editMessage.getText().toString().trim();
                if (!text.isEmpty()) {
                    Message message = new Message(text, currentUserId, otherUserId);
                    chatViewModel.sendMessages(message);
                    }

            }
        });


    }

    @SuppressLint("MissingPermission")
    public void showNotification(Context context, String text) {
        notificationbuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle("otherUserId")
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(NOTIFY_ID, notificationbuilder.build());
    }

    private void observeViewModel() {
        chatViewModel.getMessages().observe(this, new Observer<List<Message>>() {
            @Override
            public void onChanged(List<Message> messages) {

                messagesAdapter.setMessages(messages);
                if (messages.size() != 0)
                    binding.recyclerViewChat.smoothScrollToPosition(messages.size() - 1);
            }
        });
        chatViewModel.getErrors().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errorMessage) {
                if (errorMessage != null) {
                    Toast.makeText(ChatActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                }
            }
        });
        chatViewModel.getMessagesSent().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean sent) {
                if (sent) {
                    binding.editMessage.setText("");
                }
            }
        });
        chatViewModel.getOtherUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                String userName = String.format("%s %s", user.getName(), user.getLastName());
                binding.textViewUserName.setText(userName);
                int bgResId;
                if (user.getOnline()) {
                    bgResId = R.drawable.circle_green;
                } else {
                    bgResId = R.drawable.circle_red;

                }
                Drawable drawable = ContextCompat.getDrawable(ChatActivity.this, bgResId);

                binding.statusId.setBackground(drawable);
            }
        });
    }


    private List<Message> getListTest() {
        List<Message> messages = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Message message;
            if (i % 2 == 0) {
                message = new Message(
                        "Text" + i,
                        currentUserId,
                        otherUserId);
            } else {
                message = new Message(
                        "Text" + i,
                        otherUserId,
                        currentUserId);
            }
            messages.add(message);
        }
        return messages;
    }

    public static Intent newIntent(Context context, String currentUserId, String otherUserId) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(EXTRA_CURRENT_USER_ID, currentUserId);
        intent.putExtra(EXTRA_OTHER_USER_ID, otherUserId);
        return intent;
    }

    @Override
    protected void onPause() {
        super.onPause();
        chatViewModel.setUserOnline(false);
       // showNotification(ChatActivity.this, "Hello");
    }

    @Override
    protected void onResume() {
        super.onResume();
        chatViewModel.setUserOnline(true);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = UsersActivity.newIntent(ChatActivity.this,currentUserId);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}