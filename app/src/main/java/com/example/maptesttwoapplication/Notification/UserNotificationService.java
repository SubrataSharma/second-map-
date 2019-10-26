package com.example.maptesttwoapplication.Notification;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.maptesttwoapplication.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Objects;

public class UserNotificationService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        showNotification(Objects.requireNonNull(remoteMessage.getNotification()).getTitle()
                ,remoteMessage.getNotification().getBody());
    }

    private void showNotification(String title,String message) {
        NotificationCompat.Builder builder= new NotificationCompat.Builder(this,"NewProduct")
                .setContentTitle(title)
                .setSmallIcon(R.drawable.ic_shopping_cart_black_24dp)
                .setAutoCancel(true)
                .setContentText(message);
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(999,builder.build());
    }


}
