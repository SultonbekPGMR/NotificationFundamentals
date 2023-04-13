package com.sultonbek1547.notificationfundamentals

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.RemoteViews
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.sultonbek1547.notificationfundamentals.databinding.ActivityMainBinding

class CustomNotificationActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var notificationManager: NotificationManagerCompat

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        notificationManager = NotificationManagerCompat.from(this)

        binding.btnShow.setOnClickListener {
            notificationManager.notify(1, notifyBuilder())
        }

    }

    private fun notifyBuilder(): Notification {
        val notificationCollapsed = RemoteViews(packageName, R.layout.notification_collapsed)
        val notificationExpanded = RemoteViews(packageName, R.layout.notification_expanded)

        val clickIntent = Intent(this, NotificationReceiver::class.java)
        val clickPendingIntent = PendingIntent.getBroadcast(this, 0, clickIntent, FLAG_IMMUTABLE)

        notificationCollapsed.setTextViewText(R.id.textViewCollapse, "This is Collapse!")

        notificationExpanded.setImageViewResource(
            R.id.image_view_expanded,
            R.drawable.ic_launcher_background
        )

        notificationExpanded.setOnClickPendingIntent(R.id.image_view_expanded, clickPendingIntent)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Channel name"
            val description = "Description"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("exampleChannel", name, importance).apply {
                setDescription(description)
            }
            // register channel with system
            notificationManager.createNotificationChannel(channel)

        }
        return NotificationCompat.Builder(this, "exampleChannel")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setCustomContentView(notificationCollapsed)
            .setCustomBigContentView(notificationExpanded)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle()).build()

    }
}