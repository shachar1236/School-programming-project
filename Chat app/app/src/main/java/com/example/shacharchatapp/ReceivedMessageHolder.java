package com.example.shacharchatapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

class ReceivedMessageHolder extends RecyclerView.ViewHolder {
    TextView messageText, timeText, nameText;
    ImageView profileImage;
  
    ReceivedMessageHolder(View itemView) {
        super(itemView);
        messageText = itemView.findViewById(R.id.text_message_body);
        timeText = itemView.findViewById(R.id.text_message_time);
        nameText = itemView.findViewById(R.id.text_message_name);
    }

    void bind(UserMessage message) {

        messageText.setText(message.getMessage());

        // Format the stored timestamp into a readable String using method.
        timeText.setText(message.getCreatedAt());
        nameText.setText(message.getSenderName());

        // Insert the profile image from the URL into the ImageView.
//        Utils.displayRoundImageFromUrl(mContext, message.getSender().getProfileUrl(), profileImage);
    }
}