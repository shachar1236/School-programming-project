package com.example.shacharchatapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;


/**
 * The AvatarsGenerator class is responsible for generating avatars for given names.
 */
public class AvatarsGenerator {
    // Static map to store generated avatars with their corresponding names
    static HashMap<String, Bitmap> avatars = new HashMap<>();

    /**
     * Retrieves a Bitmap image from a given URL.
     *
     * @param src The URL of the image to retrieve.
     * @return The Bitmap image retrieved from the URL, or null if an error occurred.
     */
    private static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            Log.e("Bitmap", "returned");
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception", e.getMessage());
            return null;
        }
    }

    /**
     * Generates an avatar Bitmap for a given name.
     *
     * @param name The name to generate the avatar for.
     * @return The generated avatar Bitmap, or null if an error occurred.
     */
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
