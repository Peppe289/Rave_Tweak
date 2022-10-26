package com.rave.tweak

import android.os.Bundle
//import android.widget.Switch
//import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.rave.tweak.databinding.ActivityMainBinding
import android.widget.Button
import android.widget.TextView
import com.jaredrummler.ktsh.Shell


/*
// for dynamic managment. run in background. update later.
credit: https://stackoverflow.com/a/34573169

android studio convert this code in kotlin:

class YourService : Service() {
    @Nullable
    fun onBind(intent: Intent?): IBinder? {
        return null
    }

    fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        // do your jobs here
        startForeground()
        return super.onStartCommand(intent, flags, startId)
    }

    private fun startForeground() {
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0,
            notificationIntent, 0
        )
        startForeground(
            NOTIF_ID, NotificationCompat.Builder(
                this,
                NOTIF_CHANNEL_ID
            ) // don't forget create a notification channel first
                .setOngoing(true)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(getString(R.string.app_name))
                .setContentText("Service is running background")
                .setContentIntent(pendingIntent)
                .build()
        )
    }

    companion object {
        private const val NOTIF_ID = 1
        private const val NOTIF_CHANNEL_ID = "Channel_Id"
    }
}
*/

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    object StaticDef {
        const val IOERROR = (-1)
        const val primaryCluster = "/sys/devices/system/cpu/cpufreq/policy0/"
        const val vfs_cache_pressure = "/proc/sys/vm/vfs_cache_pressure"
    }

    fun write_node (patch: String, input: String): Int {
        try {
            val command = StringBuilder().append("echo ").append(input).append(" > ").append(patch).toString()
            Shell("su").run(command)
        } catch (e: NumberFormatException) {
            return StaticDef.IOERROR
        }

        return 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // request root permission
        Runtime.getRuntime().exec("su")

        // take kernel version with uname -r
        val shell = Shell("sh")
        val inputString = shell.run("uname -r").stdout()

        // upload all governor name in built kernel
        val governorList = shell.run("cat " +
                "/sys/devices/system/cpu/cpufreq/policy0/scaling_available_governors")
                                            .stdout().split(" ").toTypedArray()

        // upload available cluster
        //var cluster = ""

        /*
         * Here button for tweak.
         * Respectively for:
         * - Performance;
         * - Balanced;
         * - Powersave.
         */
        val performance = findViewById<Button>(R.id.performance)
        val balanced = findViewById<Button>(R.id.balanced)
        val powersave = findViewById<Button>(R.id.powersave)

        performance?.setOnClickListener() {
            // use better governor for performance.
            // if chose performance governor maybe u can see throttling!!!
            var governor = "none"
            val VFSCachePressureValue = "60"


            // chose governor:
            for (i in governorList) {
                if (i == "performance") {
                    governor = i
                    // prefer performance
                    break
                } else if (i == "schedutil")
                    governor = i
                else if (i == "interactive")
                    governor = i
            }

            if (governor != "none") // if find governor, set it
                write_node(StringBuilder().append(StaticDef.primaryCluster)
                                            .append("scaling_governor").toString(), governor)

            write_node(StaticDef.vfs_cache_pressure,VFSCachePressureValue)
        }

        balanced?.setOnClickListener() {

        }

        powersave?.setOnClickListener() {

        }

        // this textView is for kernel name
        val textView: TextView = findViewById<TextView>(R.id.textView)
        // show kernel name
        textView.text = inputString

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
    }
}