package com.example.shacharchatapp;

import android.graphics.Bitmap;
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
        profileImage = itemView.findViewById(R.id.image_message_profile);
    }

    void bind(UserMessage message) {

        messageText.setText(message.getMessage());

        // Format the stored timestamp into a readable String using method.
        timeText.setText(message.getCreatedAt());
        nameText.setText(message.getSenderName());

        // Insert the profile image from the URL into the ImageView.
        SetImageThread therad = new SetImageThread(profileImage, message.getSenderName());
        therad.start();
    }
}

class SetImageThread extends Thread {
    ImageView profileImageView;
    String user_name;
    public SetImageThread(ImageView profileImageView, String user_name) {
        super();
        this.profileImageView = profileImageView;
        this.user_name = user_name;
    }
    public void run() {
        // Insert the profile image from the URL into the ImageView.
        Bitmap img = AvatarsGenerator.generateAvatarFor(user_name);
        profileImageView.post(new Runnable() {
            @Override
            public void run() {
                profileImageView.setImageBitmap(img);
            }
        });
    }
}