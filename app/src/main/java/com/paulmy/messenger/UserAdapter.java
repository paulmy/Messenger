package com.paulmy.messenger;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.paulmy.messenger.databinding.ItemUserBinding;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private List<User> users = new ArrayList<>();

    public void setUsers(List<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    private OnUserClickListener onUserClickListener;

    public void setOnUserClickListener(OnUserClickListener onUserClickListener) {
        this.onUserClickListener = onUserClickListener;
    }

    interface OnUserClickListener {
        void onClickUser(User user);
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(ItemUserBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = users.get(position);
        String userInfo = String.format("%s %s %s", user.getName(), user.getLastName(), user.getAge());
        holder.binding.tvUserinfo.setText(userInfo);
        int bgResId;
        if (user.getOnline()) {
            bgResId = R.drawable.circle_green;
        } else {
            bgResId = R.drawable.circle_red;

        }
        Drawable drawable = ContextCompat.getDrawable(holder.itemView.getContext(), bgResId);

        holder.binding.status.setBackground(drawable);
        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onUserClickListener != null) {
                    onUserClickListener.onClickUser(user);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        private final ItemUserBinding binding;

        public UserViewHolder(ItemUserBinding b) {
            super(b.getRoot());
            binding = b;
        }
    }
}
