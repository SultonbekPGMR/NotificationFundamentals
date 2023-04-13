package com.sultonbek1547.notificationfundamentals

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.sultonbek1547.notificationfundamentals.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // default notification
        binding.apply {
            val notificationManager =
                getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            btnShow.setOnClickListener {
                val notification = NotificationCompat.Builder(this@MainActivity,"1")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentText("Context text")
                    .setContentTitle("Title")
                    .build()


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val name = "Channel name"
                    val description = "Description"
                    val importance = NotificationManager.IMPORTANCE_HIGH
                    val channel = NotificationChannel("1", name, importance).apply {
                        setDescription(description)
                    }

                    // register channel with system
                    notificationManager.createNotificationChannel(channel)

                }


                notificationManager.notify(1, notification)

            }

            btnHide.setOnClickListener {
                notificationManager.cancel(1)
            }

        }


    }
}