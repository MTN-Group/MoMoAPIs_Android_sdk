package com.momo.sdk.model;

import com.google.gson.annotations.SerializedName;

public class DeliveryNotification {

    @SerializedName("notificationMessage")
    private String notificationMessage;

    public String getNotificationMessage() {
        return notificationMessage;
    }

    public void setNotificationMessage(String notificationMessage) {
        this.notificationMessage = notificationMessage;
    }
}
