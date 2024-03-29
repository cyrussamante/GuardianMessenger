package com.example.guardianmessenger.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guardianmessenger.ChatActivity;
import com.example.guardianmessenger.R;
import com.example.guardianmessenger.utils.AndroidUtils;
import com.example.guardianmessenger.utils.FirebaseUtils;
import com.example.guardianmessenger.utils.ChatMessageModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import org.w3c.dom.Text;

public class ChatRecyclerAdapter extends FirestoreRecyclerAdapter<ChatMessageModel, ChatRecyclerAdapter.ChatMessageModelViewHolder> {

    Context context;
    public ChatRecyclerAdapter(@NonNull FirestoreRecyclerOptions<ChatMessageModel> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ChatMessageModelViewHolder holder, int i, @NonNull ChatMessageModel model) {
       if (model.getSenderId().equals(FirebaseUtils.currentUserId())){
           holder.leftChatLayout.setVisibility(View.GONE);
           holder.rightChatLayout.setVisibility(View.VISIBLE);
           holder.rightChatText.setText(model.getMessage());
       }else {
           holder.rightChatLayout.setVisibility(View.GONE);
           holder.leftChatLayout.setVisibility(View.VISIBLE);
           holder.leftChatText.setText(model.getMessage());
       }

    }

    @NonNull
    @Override
    public ChatMessageModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chat_message_recycler_loop,parent, false);
        return new ChatMessageModelViewHolder(view);
    }

    class ChatMessageModelViewHolder extends RecyclerView.ViewHolder{
        LinearLayout leftChatLayout, rightChatLayout;
        TextView leftChatText, rightChatText;
        public ChatMessageModelViewHolder(@NonNull View itemView) {
            super(itemView);
            
            leftChatLayout = itemView.findViewById(R.id.left_chat_layout);
            rightChatLayout = itemView.findViewById(R.id.right_chat_layout);
            leftChatText = itemView.findViewById(R.id.left_chat_text);
            rightChatText = itemView.findViewById(R.id.right_chat_text);



        }
    }
}
