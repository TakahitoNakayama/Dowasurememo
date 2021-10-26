package com.websarva.wings.android.dowasurememo

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi

class AlarmReceiver : BroadcastReceiver() {

    companion object {

        private const val CHANNEL_ID = "memoalarm_notification_channel"
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context, intent: Intent) {

//        Log.d("AlarmBroadcastReceiver", "onReceive() pid=" + android.os.Process.myPid());
//
//        val requestCode = intent.getIntExtra("RequestCode", 0);
//
//        val pendingIntent =
//                PendingIntent.getActivity(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        val id = intent.getIntExtra("RequestCode", 0)


        Toast.makeText(context, "Received ", Toast.LENGTH_LONG).show();

        Log.d("main", "receive")

        val name = (R.string.notification_name).toString()
        val importance = NotificationManager.IMPORTANCE_DEFAULT

        val channel = NotificationChannel(CHANNEL_ID, name, importance)

        val notifmanager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notifmanager.createNotificationChannel(channel)


        val builder = Notification.Builder(context, CHANNEL_ID)
        builder.setSmallIcon(android.R.drawable.ic_dialog_info)
        builder.setContentTitle("通知だよ")
        builder.setContentText("おめでとう")

        val notification = builder.build()
        notifmanager.notify(id, notification)


    }


}