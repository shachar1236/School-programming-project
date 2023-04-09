package com.example.shacharchatapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private RecyclerView mMessageRecycler;
    private MessageListAdapter mMessageAdapter;
    private List<UserMessage> msgList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mMessageRecycler = (RecyclerView) findViewById(R.id.recycler_gchat);
        List<UserMessage> messageList = new ArrayList<>();
        mMessageAdapter = new MessageListAdapter(this, messageList);
        msgList = messageList;
        mMessageRecycler.setLayoutManager(new LinearLayoutManager(this));
        mMessageRecycler.setAdapter(mMessageAdapter);

        addNewElement(new UserMessage(false, "Shalev", "Hi ani shalev", "11/11/11"));
        addNewElement(new UserMessage(false, "Shani", "Hi ani shani", "11/11/11"));
        addNewElement(new UserMessage(true, "Shachar", "Hi ani shachar", "11/11/11"));
    }

    private void addNewElement(UserMessage msg) {
        msgList.add(msg);
        mMessageAdapter.notifyItemInserted(msgList.size() - 1);
    }
}