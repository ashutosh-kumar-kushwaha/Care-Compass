package me.ashutoshkk.carecompass.alarm

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Vibrator
import android.widget.Toast
import androidx.core.app.NotificationCompat
import me.ashutoshkk.carecompass.R
import me.ashutoshkk.carecompass.common.Constants

class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val message = intent.getStringExtra(Constants.ALARM_NAME) ?: return
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(4000)
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
//        val alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
//        val ringtone = RingtoneManager.getRingtone(context, alarmUri)
//        ringtone.play()

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        val stopIntent = PendingIntent.getBroadcast(context, 0, Intent("STOP_ACTION"), PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        val notification = NotificationCompat.Builder(context, "alarm_channel")
            .setContentTitle("Alarm")
            .setContentText("Your alarm is running")
            .setSmallIcon(R.drawable.health_hive)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(1, notification)

    }
}