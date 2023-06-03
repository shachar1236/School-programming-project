package com.example.shacharchatapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MessageListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;

    private Context mContext;
    private List<UserMessage> mMessageList;

    /**
     * Constructs a MessageListAdapter.
     *
     * @param context      The context of the activity or fragment.
     * @param messageList  The list of UserMessage objects to be displayed.
     */
    public MessageListAdapter(Context context, List<UserMessage> messageList) {
        mContext = context;
        mMessageList = messageList;
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    /**
     * Determines the appropriate ViewType for a given position in the message list.
     *
     * @param position  The position of the message in the list.
     * @return          The ViewType for the message, either VIEW_TYPE_MESSAGE_SENT or VIEW_TYPE_MESSAGE_RECEIVED.
     */
    @Override
    public int getItemViewType(int position) {
        UserMessage message = mMessageList.get(position);

        if (message.isFromMe()) {
            // If the current user is the sender of the message
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            // If some other user sent the message
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    /**
     * Inflates the appropriate layout for the ViewType.
     *
     * @param parent    The ViewGroup into which the new View will be added.
     * @param viewType  The ViewType of the new View.
     * @return          A new instance of RecyclerView.ViewHolder for the corresponding ViewType.
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_sent, parent, false);
            return new SentMessageHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_received, parent, false);
            return new ReceivedMessageHolder(view);
        }

        return null;
    }

    /**
     * Binds the message object to the ViewHolder to populate the UI.
     *
     * @param holder    The ViewHolder to be bound.
     * @param position  The position of the message in the list.
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        UserMessage message = mMessageList.get(position);

        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) holder).bind(message);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) holder).bind(message);
                break;
        }
    }
}
