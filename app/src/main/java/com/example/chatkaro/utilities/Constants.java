package com.example.chatkaro.utilities;

import java.util.HashMap;

public class Constants {
    //creating a hashmap with constant and user detail
    public static final String KEY_COLLECTION_USERS = "users"; // database collection
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_PREFERENCE_NAME = "chatAppPreference"; //storage
    public static final String KEY_SIGNED_IN = "isSignedIn";
    public static final String KEY_USER_ID = "userID"; // that particular user id from the data base collection
    public static final String KEY_IMAGE = "image";
    public static final String KEY_FCM_TOKEN = "fcmToken";
    public static final String KEY_USER = "user"; //the user that you are chatting with
    public static final String KEY_COLLECTION_CHAT = "chat"; // array list of chat
    public static final String KEY_SENDER_ID = "senderID";
    public static final String KEY_RECEIVER_ID = "receiverID";
    public static final String KEY_MESSAGE = "message"; // one message
    public static final String KEY_TIMESTAMP = "timestamp";
    public static final String KEY_COLLECTION_CONVERSATIONS = "conversations";
    public static final String KEY_SENDER_NAME = "senderName";
    public static final String KEY_RECEIVER_NAME = "receiverName";
    public static final String KEY_SENDER_IMAGE = "senderImage";
    public static final String KEY_RECEIVER_IMAGE = "receiverImage";
    public static final String KEY_LAST_MESSAGE = "lastMessage";
    public static final String KEY_AVAILABILITY = "availability";
    public static final String REMOTE_MSG_AUTHORIZATION = "Authorization";
    public static final String REMOTE_MSG_CONTENT_TYPE = "Content-Type";
    public static final String REMOTE_MSG_DATA= "data";
    public static final String REMOTE_MSG_REGISTRATION_IDS= "registration_ids";

    public static HashMap<String, String> remoteMsgHeaders = null;
    public static HashMap<String, String> getRemoteMsgHeaders(){
        if (remoteMsgHeaders == null){
            remoteMsgHeaders = new HashMap<>();
            remoteMsgHeaders.put(
                    REMOTE_MSG_AUTHORIZATION,
                    "key=AAAALjI1GKA:APA91bEE3ofkIzuDDzkMhiY0j7MnD00DPfSCrgXEHdxyU0KwUS1jfXoMk_k25K_xWWQo7lMpWc3RSxxzWPZSc0pS-inbmn8VZO0KiFgUyUTJ5rUjuNpxHVBa7dcKOj2C6o7IRhzW363v"
            );
            remoteMsgHeaders.put(
                    REMOTE_MSG_CONTENT_TYPE,
                    "application/json"
            );
        }
        return remoteMsgHeaders;
    }




}
