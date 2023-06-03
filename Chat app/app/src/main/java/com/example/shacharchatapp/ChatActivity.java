package com.example.shacharchatapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The activity responsible for handling the chat functionality.
 */
public class ChatActivity extends AppCompatActivity {

    private final String CHAT_COLLECTION_NAME = "chat";
    private RecyclerView mMessageRecycler;
    private MessageListAdapter mMessageAdapter;
    private List<UserMessage> msgList;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private final String TAG = "chat_activity";

    /**
     * Called when the activity is starting. This is where most initialization should go.
     *
     * @param savedInstanceState The saved instance state of the activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // Initialize RecyclerView and adapter
        mMessageRecycler = findViewById(R.id.recycler_gchat);
        List<UserMessage> messageList = new ArrayList<>();
        mMessageAdapter = new MessageListAdapter(this, messageList);
        msgList = messageList;
        mMessageRecycler.setLayoutManager(new LinearLayoutManager(this));
        mMessageRecycler.setAdapter(mMessageAdapter);

        // Initialize Firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Send message input
        TextInputEditText msg_input = findViewById(R.id.message_input);

        // Set send button action
        Button sendButton = findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendMessage(msg_input.getText().toString());
                msg_input.getText().clear();
            }
        });

        // Set logout button
        Button logoutButton = findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent intent = new Intent(ChatActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Retrieve all messages and add them to the list
        db.collection(CHAT_COLLECTION_NAME).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<UserMessage> messages = ConvertToUserMessages(task.getResult());
                for (UserMessage msg : messages) {
                    if (!isMessageInsideList(msg.getId())) {
                        addNewElement(msg);
                    }
                }
            } else {
                Log.d(TAG, "Error getting documents: ", task.getException());
            }
        });

        // Subscribe to changes in the chat collection
        Query query = db.collection(CHAT_COLLECTION_NAME);
        ListenerRegistration registration = query.addSnapshotListener(
                new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                        Log.i(TAG, "Data changed!!!!!!!!!!!!!!!!!!!!!!!!");
                        if (e != null) {
                            Log.w(TAG, "Listen failed.", e);
                            return;
                        }

                        String source = snapshot != null && snapshot.getMetadata().hasPendingWrites()
                                ? "Local" : "Server";

                        if (snapshot != null) {
                            // Convert to UserMessages
                            List<UserMessage> messages = ConvertToUserMessages(snapshot);
                            for (UserMessage msg : messages) {
                                if (!isMessageInsideList(msg.getId())) {
                                    addNewElement(msg);
                                }
                            }
                        } else {
                            Log.d(TAG, source + " data: null");
                        }
                    }
                });

    }

    /**
     * Converts a QuerySnapshot to a list of UserMessages.
     *
     * @param snapshot The QuerySnapshot to convert.
     * @return The list of UserMessages.
     */
    private List<UserMessage> ConvertToUserMessages(QuerySnapshot snapshot) {
        List<UserMessage> l = new ArrayList<>();

        for (QueryDocumentSnapshot doc : snapshot) {
            String id = doc.getId();
            String sender_uid = (String) doc.get("sender_uid");
            String sender_name = (String) doc.get("sender_name");
            Timestamp created_at = (Timestamp) doc.get("created_at");
            String text = (String) doc.get("text");

            String created_at_str = created_at.toDate().toString();

            UserMessage msg = new UserMessage(id, sender_uid.equals(mAuth.getUid()), sender_name, text, created_at_str);
            l.add(msg);
        }

        return l;
    }

    /**
     * Sends a message to the chat collection in Firestore.
     *
     * @param data The message text to send.
     */
    private void SendMessage(String data) {
        Map<String, Object> message = new HashMap<>();
        message.put("sender_uid", mAuth.getCurrentUser().getUid());
        message.put("sender_name", mAuth.getCurrentUser().getEmail());
        message.put("created_at", new Timestamp(new Date()));
        message.put("text", data);

        db.collection(CHAT_COLLECTION_NAME).add(message);
    }

    /**
     * Adds a new message element to the list.
     *
     * @param msg The UserMessage to add.
     */
    private void addNewElement(UserMessage msg) {
        msgList.add(msg);
        mMessageAdapter.notifyItemInserted(msgList.size() - 1);
    }

    /**
     * Checks if a message with the given id is already in the list.
     *
     * @param id The id of the message to check.
     * @return true if the message is already in the list, false otherwise.
     */
    private boolean isMessageInsideList(String id) {
        for (UserMessage msg : msgList) {
            if (msg.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }
}
