package com.example.shacharchatapp;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
class ReceivedMessageHolder extends RecyclerView.ViewHolder {
    TextView messageText, timeText, nameText;
    ImageView profileImage;

    /**
     * Constructor for the ReceivedMessageHolder class.
     *
     * @param itemView The inflated view for the received message item.
     */
    ReceivedMessageHolder(View itemView) {
        super(itemView);
        messageText = itemView.findViewById(R.id.text_message_body);
        timeText = itemView.findViewById(R.id.text_message_time);
        nameText = itemView.findViewById(R.id.text_message_name);
        profileImage = itemView.findViewById(R.id.image_message_profile);
    }

    /**
     * Binds the message data to the views in the received message item.
     *
     * @param message The UserMessage object containing the message data.
     */
    void bind(UserMessage message) {
        messageText.setText(message.getMessage());

        // Format the stored timestamp into a readable String using method.
        timeText.setText(message.getCreatedAt());
        nameText.setText(message.getSenderName());

        // Insert the profile image from the URL into the ImageView using a separate thread.
        SetImageThread thread = new SetImageThread(profileImage, message.getSenderName());
        thread.start();
    }
}

class SetImageThread extends Thread {
    ImageView profileImageView;
    String user_name;

    /**
     * Constructor for the SetImageThread class.
     *
     * @param profileImageView The ImageView where the profile image will be set.
     * @param user_name        The user name used to generate the avatar.
     */
    public SetImageThread(ImageView profileImageView, String user_name) {
        super();
        this.profileImageView = profileImageView;
        this.user_name = user_name;
    }

    /**
     * Executes the thread logic to set the profile image on the ImageView.
     */
    public void run() {
        // Generate the avatar image for the user name
        Bitmap img = AvatarsGenerator.generateAvatarFor(user_name);

        // Set the image on the ImageView using the UI thread
        profileImageView.post(new Runnable() {
            @Override
            public void run() {
                profileImageView.setImageBitmap(img);
            }
        });
    }
}
