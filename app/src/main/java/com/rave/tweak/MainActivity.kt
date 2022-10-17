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

class MainActivity : AppCompatActivity() {
/*
    object StaticDef {
        const val kernelver = "/proc/version"
    } */

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // request root permission
        Runtime.getRuntime().exec("su")

        // take kernel version with uname -r
        val shell = Shell("sh")
        val inputString = shell.run("uname -r").stdout()

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