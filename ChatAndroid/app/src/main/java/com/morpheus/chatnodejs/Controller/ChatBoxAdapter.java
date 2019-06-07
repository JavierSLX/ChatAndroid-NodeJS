package com.morpheus.chatnodejs.Controller;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.morpheus.chatnodejs.Model.Message;
import com.morpheus.chatnodejs.R;

import java.util.List;

public class ChatBoxAdapter extends RecyclerView.Adapter<ChatBoxAdapter.ChatBoxHolder>
{
    private List<Message> messageList;

    public ChatBoxAdapter(List<Message> messageList)
    {
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public ChatBoxHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        return new ChatBoxAdapter.ChatBoxHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatBoxHolder chatBoxHolder, int i)
    {
        Message message = messageList.get(i);
        chatBoxHolder.txtNickname.setText(message.getNickname() + ": ");
        chatBoxHolder.txtMessage.setText(message.getMessage());
    }

    @Override
    public int getItemCount()
    {
        return messageList.size();
    }

    public static class ChatBoxHolder extends RecyclerView.ViewHolder
    {
        public TextView txtNickname;
        public TextView txtMessage;

        public ChatBoxHolder(@NonNull View itemView)
        {
            super(itemView);
            txtNickname = itemView.findViewById(R.id.txtNickname);
            txtMessage = itemView.findViewById(R.id.txtMessage);
        }
    }
}
