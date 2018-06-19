package studymate.mstechnologies.com.studymateandroid.Services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import studymate.mstechnologies.com.studymateandroid.Activities.HomeActivity;
import studymate.mstechnologies.com.studymateandroid.R;

/**
 * Created by xboxp on 5/31/2018.
 */

public class StudyMateFirebaseMessagingService extends FirebaseMessagingService
{
 /* @Override
  public void onMessageReceived(RemoteMessage remoteMessage)
  {
    sendFullNotification();
  }

  private void sendFullNotification(String user)
  {
  /*  String appName = getResources().getString(R.string.app_name);
    String message = user+" Answered your Question";
    Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    NotificationCompat.Builder notification = new NotificationCompat.Builder(this);
    notification.setSmallIcon(R.mipmap.ic_launcher)
        .setContentTitle(appName)
        .setSound(notificationSound)
        .setContentText(message);
    Intent intentNotificatio = new Intent(this,HomeActivity.class);
    PendingIntent
        pendingIntent =PendingIntent.getActivity(this,0,intentNotificatio,PendingIntent.FLAG_UPDATE_CURRENT);
    notification.setContentIntent(pendingIntent);
    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    notificationManager.notify(0,notification.build());
  } */

}
