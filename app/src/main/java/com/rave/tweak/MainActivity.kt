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

        /*val root = */
        Runtime.getRuntime().exec("su")

        //val inputString = File("/proc/version").reader().use { it.readText() }
        val shell = Shell("sh")
        val inputString = shell.run("uname -r").stdout()
        var balancedTweak = findViewById<Button>(R.id.button_id)
/*
        balancedTweak?.setOnClickListener()
        {

        } */

        val textView: TextView = findViewById<TextView>(R.id.textView)
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