package com.example.guardianmessenger.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guardianmessenger.R;
import com.example.guardianmessenger.utils.AndroidUtils;
import com.example.guardianmessenger.utils.MessageModel;
import com.example.guardianmessenger.utils.FirebaseUtils;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class ChatRecyclerAdapter extends FirestoreRecyclerAdapter<MessageModel, ChatRecyclerAdapter.ChatMessageModelViewHolder> {

    Context context;
    public ChatRecyclerAdapter(@NonNull FirestoreRecyclerOptions<MessageModel> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ChatMessageModelViewHolder holder, int i, @NonNull MessageModel model) {
        //Decrypt Message
        String message = AndroidUtils.decryptMessage(model.getMessage());
        if (model.getSenderId().equals(FirebaseUtils.currentUserId())){
            //Your Messages
           holder.leftChatLayout.setVisibility(View.GONE);
           holder.rightChatLayout.setVisibility(View.VISIBLE);
           holder.rightChatText.setText(message);
       }else {
            //Other User's Messages
           holder.rightChatLayout.setVisibility(View.GONE);
           holder.leftChatLayout.setVisibility(View.VISIBLE);
           holder.leftChatText.setText(message);
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
            //Setting UI Elements
            leftChatLayout = itemView.findViewById(R.id.left_chat_layout);
            rightChatLayout = itemView.findViewById(R.id.right_chat_layout);
            leftChatText = itemView.findViewById(R.id.left_chat_text);
            rightChatText = itemView.findViewById(R.id.right_chat_text);



        }
    }
}
