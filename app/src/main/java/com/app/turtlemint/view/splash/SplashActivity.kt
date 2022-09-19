package com.app.turtlemint.view.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.app.turtlemint.R
import com.app.turtlemint.databinding.ActivitySplashBinding
import com.app.turtlemint.view.issues.MainActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //show full screen
        hideSystemBars()

        //set splash animation
        setSplashAnimation()

        //call home screen once animation gets over
        val mainHandler = Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 2500)

    }

    private fun setSplashAnimation() {
        binding.lavSplash.setAnimation(R.raw.github)
        binding.lavSplash.speed = 1.0F // How fast does the animation play
        binding.lavSplash.progress = 1F // Starts the animation from 50% of the beginning
        binding.lavSplash.playAnimation()
    }

    private fun hideSystemBars() {
        val windowInsetsController =
            ViewCompat.getWindowInsetsController(window.decorView) ?: return
        // Configure the behavior of the hidden system bars
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        // Hide both the status bar and the navigation bar
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
    }
}