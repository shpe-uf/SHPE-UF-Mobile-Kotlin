package com.example.shpe_uf_mobile_kotlin.util

import android.Manifest
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.AlarmManagerCompat
import androidx.core.app.NotificationCompat
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.getSystemService
import com.example.shpe_uf_mobile_kotlin.MainActivity
import com.example.shpe_uf_mobile_kotlin.R
import com.example.shpe_uf_mobile_kotlin.ui.pages.home.HomeViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class NotificationsUtil {
    companion object {
        const val CHANNEL_ID = "SHPEUFEventsChannel"
    }

    // scheduleNotification is a method that takes in an Event object and schedules a notification for that event
    // will be used in the HomeViewModel and just allows for the app to be open within the
    // event details page

    private fun checkCanCreateNotifications(context: Context): Boolean {
        return NotificationManagerCompat.from(context).areNotificationsEnabled()
    }

    private fun checkCanScheduleExactAlarms(context: Context): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            return alarmManager.canScheduleExactAlarms()
        }
        return true // Permission only required for API level 31 and above
    }

    private fun requestPermissionForExactAlarms(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val intent = Intent().apply {
                action = android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
        }
    }

    private fun requestPermissionForNotifications(context: Context) {
        val intent = Intent().apply {
            action = android.provider.Settings.ACTION_APP_NOTIFICATION_SETTINGS
            putExtra(android.provider.Settings.EXTRA_APP_PACKAGE, context.packageName)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        context.startActivity(intent)
    }


    fun scheduleNotification(context: Context, event: HomeViewModel.Event) {
        if (!checkCanScheduleExactAlarms(context)) {
            requestPermissionForExactAlarms(context)
            return
        }
        if (!checkCanCreateNotifications(context)) {
            requestPermissionForNotifications(context)
            return
        }

        val alarmManager = context.getSystemService(AlarmManager::class.java)
        val intent = Intent(context, AlarmReceiver::class.java)
            .putExtra(NotificationsUtilInfo.NOTIFICATION_ID, event.id)
            .putExtra(NotificationsUtilInfo.NOTIFICATION_TITLE, "SHPE UF Event")
            .putExtra(NotificationsUtilInfo.NOTIFICATION_MESSAGE, "${event.summary} is starting soon!")

        // pritnt out all the extras:
        Log.d("HomeViewModel", "Notification ID: ${event.id}")
        Log.d("HomeViewModel", "Notification Title: ${event.summary}")
        Log.d("HomeViewModel", "Notification Message: ${event.description}")

        val requestCode = event.id.hashCode()
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        // create eventTime var based on eventStart date and dateTime
        var eventTime = event.start.dateTime?.let {
            LocalDateTime.parse(it, DateTimeFormatter.ISO_DATE_TIME)?.withSecond(0)
        } ?: event.start.date?.let {
            LocalDate.parse(it, DateTimeFormatter.ISO_DATE).atStartOfDay()?.withSecond(0)
        }?.atZone(ZoneId.systemDefault())?.toLocalDateTime()

        eventTime = eventTime?.minusMinutes(15)
        Log.d("HomeViewModel", "Event time: $eventTime")

        // proper format time
        val triggerAtMillis = eventTime?.atZone(ZoneId.systemDefault())?.toInstant()?.toEpochMilli()

        // Schedule the alarm
        if (triggerAtMillis != null) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                triggerAtMillis,
                pendingIntent
            )
        }
        }
    }

class AlarmReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context, intent: Intent) {
        val notificationId = intent.getIntExtra(NotificationsUtilInfo.NOTIFICATION_ID, 0)
        val title = intent.getStringExtra(NotificationsUtilInfo.NOTIFICATION_TITLE)  ?: "Shpe UF Event"
        val message = intent.getStringExtra(NotificationsUtilInfo.NOTIFICATION_MESSAGE) ?: "You have an upcoming event"


        Log.d("HomeViewModel", "Alarm received for event: $title")
       // Log.d("HomeViewModel", "Notification ID: $notificationId")
        Log.d("HomeViewModel", "Notification message: $message")

        val notificationBuilder = NotificationCompat.Builder(context, NotificationsUtilInfo.CHANNEL_ID)
            .setSmallIcon(R.drawable.shpe_logo_full_color)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        // Check for POST_NOTIFICATIONS permission on Android 13 and above
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU ||
            ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {

            val notificationManager = NotificationManagerCompat.from(context)
            notificationManager.notify(notificationId, notificationBuilder)
        } else {
            Toast.makeText(context, "Please enable notifications for this app in your settings", Toast.LENGTH_LONG).show()
            openAppSettings(context)
        }
    }
}



object NotificationsUtilInfo  {
    const val NOTIFICATION_ID = "notification_id"
    const val NOTIFICATION_TITLE = "notification_title"
    const val NOTIFICATION_MESSAGE = "notification_message"
    const val CHANNEL_ID = "SHPEUFEventsChannel" // Make sure this matches your Notification Channel ID
}