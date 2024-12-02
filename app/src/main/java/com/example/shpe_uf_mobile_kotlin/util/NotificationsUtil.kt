package com.example.shpe_uf_mobile_kotlin.util

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import com.example.shpe_uf_mobile_kotlin.MainActivity
import com.example.shpe_uf_mobile_kotlin.R
import com.example.shpe_uf_mobile_kotlin.ui.pages.home.HomeViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object NotificationsUtil {
    private lateinit var appContext: Context

    fun init(context: Context) {
        appContext = context
    }

    const val CHANNEL_ID = "SHPEUFEventsChannel"

    // scheduleNotification is a method that takes in an Event object and schedules a notification for that event
    // will be used in the HomeViewModel and just allows for the app to be open within the
    // event details page

    private fun checkCanCreateNotifications(): Boolean {
        return NotificationManagerCompat.from(appContext).areNotificationsEnabled()
    }

    private fun checkCanScheduleExactAlarms(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val alarmManager = appContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            return alarmManager.canScheduleExactAlarms()
        }
        return true // Permission only required for API level 31 and above
    }

    private fun requestPermissionForExactAlarms() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val intent = Intent().apply {
                action = android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            appContext.startActivity(intent)
        }
    }

    private fun requestPermissionForNotifications() {
        val intent = Intent().apply {
            action = android.provider.Settings.ACTION_APP_NOTIFICATION_SETTINGS
            putExtra(android.provider.Settings.EXTRA_APP_PACKAGE, appContext.packageName)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        appContext.startActivity(intent)
    }

    fun scheduleNotification(event: HomeViewModel.Event) {
        if (!checkCanScheduleExactAlarms()) {
            requestPermissionForExactAlarms()
            return
        }
        if (!checkCanCreateNotifications()) {
            requestPermissionForNotifications()
            return
        }

        val alarmManager = appContext.getSystemService(AlarmManager::class.java)
        val notificationId = event.id.hashCode()

        val intent = Intent(appContext, AlarmReceiver::class.java)
            .putExtra(NotificationsUtilInfo.NOTIFICATION_ID, notificationId)
            .putExtra(NotificationsUtilInfo.NOTIFICATION_TITLE, "UF SHPE Event!")
            .putExtra(NotificationsUtilInfo.NOTIFICATION_MESSAGE, "${event.summary} is starting soon!")
            .putExtra(NotificationsUtilInfo.NOTIFICATION_NAME, event.summary)

        // print out all the extras:
        Log.d("HomeViewModel", "Notification ID: ${event.id}")
        Log.d("HomeViewModel", "Notification Title: ${event.summary}")
        Log.d("HomeViewModel", "Notification Message: ${event.description}")

        val pendingIntent = PendingIntent.getBroadcast(
            appContext,
            notificationId,
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
        if (triggerAtMillis != null && triggerAtMillis > System.currentTimeMillis()) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                triggerAtMillis,
                pendingIntent
            )
        }
    }

    fun cancelNotification(event: HomeViewModel.Event) {
        val alarmManager = appContext.getSystemService(AlarmManager::class.java)
        val notificationManager = NotificationManagerCompat.from(appContext)

        val notificationId = event.id.hashCode()

        Log.d("HomeViewModel", "Notification ID: $notificationId")

        // Create the exact same intent used to schedule the notification
        val intent = Intent(appContext, AlarmReceiver::class.java)
            .putExtra(NotificationsUtilInfo.NOTIFICATION_ID, notificationId)
            .putExtra(NotificationsUtilInfo.NOTIFICATION_TITLE, "UF SHPE Event!")
            .putExtra(NotificationsUtilInfo.NOTIFICATION_MESSAGE, "${event.summary} is starting soon!")

        // Recreate the exact same PendingIntent used to schedule the notification
        val pendingIntent = PendingIntent.getBroadcast(
            appContext,
            notificationId,  // The request code must be the same
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        alarmManager.cancel(pendingIntent)
        notificationManager.cancel(notificationId)
        pendingIntent.cancel()

        Log.d("HomeViewModel", "Notification, alarm, pending intent cancelled ${event.summary}")
    }
}

// AlarmReceiver is a BroadcastReceiver that will be triggered when the alarm goes off
class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(appContext: Context, intent: Intent) {
        val notificationId = intent.getIntExtra(NotificationsUtilInfo.NOTIFICATION_ID, 0)
        val title = intent.getStringExtra(NotificationsUtilInfo.NOTIFICATION_TITLE) ?: "Shpe UF Event"
        val message = intent.getStringExtra(NotificationsUtilInfo.NOTIFICATION_MESSAGE) ?: "You have an upcoming event"
        val groupKey = NotificationsUtilInfo.NOTIFICATION_GROUP
        val name = intent.getStringExtra(NotificationsUtilInfo.NOTIFICATION_NAME)

        val tapIntent = Intent(appContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra(NotificationsUtilInfo.NOTIFICATION_ID, notificationId)
            putExtra(NotificationsUtilInfo.NOTIFICATION_TITLE, title)
            putExtra(NotificationsUtilInfo.NOTIFICATION_MESSAGE, message)
            putExtra(NotificationsUtilInfo.NOTIFICATION_NAME, name) ?: null
        }

        val tapPendingIntent = PendingIntent.getActivity(
            appContext,
            notificationId,
            tapIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        // Individual notifications
        val notificationBuilder = NotificationCompat.Builder(appContext, NotificationsUtilInfo.CHANNEL_ID)
            .setSmallIcon(R.drawable.shpe_logo_full_color)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setGroup(groupKey)
            .setContentIntent(tapPendingIntent)
            .setAutoCancel(true)
            .build()

        // Summary Notification, usually for 4 or more notifications
        val summaryNotificationBuilder = NotificationCompat.Builder(appContext, NotificationsUtilInfo.CHANNEL_ID)
            .setContentTitle("Upcoming Events")
            .setContentText("You have upcoming events.")
            .setSmallIcon(R.drawable.shpe_logo_full_color)
            .setStyle(NotificationCompat.InboxStyle()
                .setBigContentTitle("Upcoming Events")
                .setSummaryText("You have upcoming events.")
            )
            .setGroup(groupKey)
            .setGroupSummary(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(false)

        val notificationManager = NotificationManagerCompat.from(appContext)

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU || ActivityCompat.checkSelfPermission(appContext, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            notificationManager.notify(notificationId, notificationBuilder)
            notificationManager.notify(
                NotificationsUtilInfo.SUMMARY_ID,
                summaryNotificationBuilder.build()
            )
        } else {
            Toast.makeText(appContext, "Please enable notifications for this app in your settings", Toast.LENGTH_LONG).show()
            openAppSettings(appContext)
        }
    }
}

object NotificationsUtilInfo  {
    const val NOTIFICATION_ID = "notification_id"
    const val NOTIFICATION_TITLE = "notification_title"
    const val NOTIFICATION_MESSAGE = "notification_message"
    const val CHANNEL_ID = "SHPEUFEventsChannel" // Make sure this matches your Notification Channel ID
    const val NOTIFICATION_GROUP = "SHPEUFEventsGroup"
    const val SUMMARY_ID = 0
    const val NOTIFICATION_NAME = "notification_name"
}