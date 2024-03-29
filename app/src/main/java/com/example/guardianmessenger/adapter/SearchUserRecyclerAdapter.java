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
import com.example.guardianmessenger.utils.FirebaseUtils;
import com.example.guardianmessenger.utils.UserModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class SearchUserRecyclerAdapter extends FirestoreRecyclerAdapter<UserModel, SearchUserRecyclerAdapter.UserModelViewHolder> {

    Context context;
    public SearchUserRecyclerAdapter(@NonNull FirestoreRecyclerOptions<UserModel> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull UserModelViewHolder userModelViewHolder, int i, @NonNull UserModel userModel) {
        userModelViewHolder.userNameText.setText(userModel.getName());
        userModelViewHolder.userEmailText.setText(userModel.getEmail());
        if (userModel.getUserId().equals(FirebaseUtils.currentUserId())){
            userModelViewHolder.userNameText.setText(userModel.getName()+"[Current User]");
        }
        userModelViewHolder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ChatActivity.class);
            AndroidUtils.passUserModel(intent,userModel);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });

    }

    @NonNull
    @Override
    public UserModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_users_loop,parent, false);
        return new UserModelViewHolder(view);
    }

    class UserModelViewHolder extends RecyclerView.ViewHolder{
        TextView userNameText;
        TextView userEmailText;
        public UserModelViewHolder(@NonNull View itemView) {
            super(itemView);
            userNameText = itemView.findViewById(R.id.user_name);
            userEmailText = itemView.findViewById(R.id.user_email);

        }
    }
}
