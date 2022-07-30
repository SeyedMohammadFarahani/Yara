package com.ansarbank.room.callback;

public interface SmsListener {
    void onMessageReceived(String messageText);
}