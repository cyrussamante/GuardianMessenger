package com.example.guardianmessenger.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guardianmessenger.ChatActivity;
import com.example.guardianmessenger.R;
import com.example.guardianmessenger.utils.AndroidUtils;
import com.example.guardianmessenger.utils.ChatModel;
import com.example.guardianmessenger.utils.FirebaseUtils;
import com.example.guardianmessenger.utils.UserModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class RecentChatsRecyclerAdapter extends FirestoreRecyclerAdapter<ChatModel, RecentChatsRecyclerAdapter.ChatModelViewHolder> {

    Context context;
    public RecentChatsRecyclerAdapter(@NonNull FirestoreRecyclerOptions<ChatModel> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ChatModelViewHolder holder, int i, @NonNull ChatModel model) {
        FirebaseUtils.getOtherUsers(model.getUserIds()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                UserModel otherUserModel = task.getResult().toObject(UserModel.class);
                holder.userNameText.setText(otherUserModel.getName());
                holder.lastMsgText.setText(model.getLastMsg());
                holder.LastMsgTimeText.setText(FirebaseUtils.timestampToString(model.getLastMsgTime()));

                holder.itemView.setOnClickListener(v -> {
                    Intent intent = new Intent(context, ChatActivity.class);
                    AndroidUtils.passUserModel(intent,otherUserModel);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                });
            }
        });

    }

    @NonNull
    @Override
    public ChatModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recent_chat_recyler_loop,parent, false);
        return new ChatModelViewHolder(view);
    }

    class ChatModelViewHolder extends RecyclerView.ViewHolder{
        TextView userNameText, lastMsgText, LastMsgTimeText;
        public ChatModelViewHolder(@NonNull View itemView) {
            super(itemView);
            userNameText = itemView.findViewById(R.id.user_name);
            lastMsgText = itemView.findViewById(R.id.last_msg_text);
            LastMsgTimeText = itemView.findViewById(R.id.last_msg_time_text);
        }
    }
}
