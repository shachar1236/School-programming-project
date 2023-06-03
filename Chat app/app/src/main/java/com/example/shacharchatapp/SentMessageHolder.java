package com.example.shacharchatapp;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

class SentMessageHolder extends RecyclerView.ViewHolder {
    TextView messageText, timeText;

    SentMessageHolder(View itemView) {
        super(itemView);

        // Initialize the TextViews
        messageText = itemView.findViewById(R.id.text_message_body);
        timeText = itemView.findViewById(R.id.text_message_time);
    }

    /**
     * Binds the message data to the ViewHolder.
     *
     * @param message The UserMessage object containing the message data.
     */
    void bind(UserMessage message) {
        // Set the message text
        messageText.setText(message.getMessage());

        // Set the timestamp text
        timeText.setText(message.getCreatedAt());
    }
}
