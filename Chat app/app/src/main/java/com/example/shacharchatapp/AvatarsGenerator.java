package com.example.shacharchatapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class AvatarsGenerator {
    static HashMap<String, Bitmap> avatars = new HashMap<>();

    private static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            Log.e("Bitmap","returned");
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception",e.getMessage());
            return null;
        }
    }

    public static Bitmap generateAvatarFor(String name) {
        Bitmap avatar;
        synchronized (avatars) {
            avatar = avatars.get(name);
        }
        if (avatar != null) {
            return avatar;
        }

        avatar = getBitmapFromURL("https://api.dicebear.com/6.x/pixel-art/jpg?seed=" + name);
        if (avatar != null) {
            synchronized (avatar) {
                avatars.put(name, avatar);
            }
        }

        return avatar;
    }
}
