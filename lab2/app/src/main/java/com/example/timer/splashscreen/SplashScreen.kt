package com.example.timer.splashscreen
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.timer.MainActivity
import com.example.timer.R

@Suppress("DEPRECATION")
class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )


        val backgroundImage: ImageView = findViewById(R.id.SplashScreenImage)
        val slideAnimation = AnimationUtils.loadAnimation(this, R.anim.runner)
        val backgroundText1: TextView = findViewById(R.id.textView4)
        val backgroundText2: TextView = findViewById(R.id.textView5)
        val slideAnimation1 = AnimationUtils.loadAnimation(this, R.anim.anim)
        val slideAnimation2 = AnimationUtils.loadAnimation(this, R.anim.anim2)
        backgroundImage.startAnimation(slideAnimation)
        backgroundText1.startAnimation(slideAnimation1)
        backgroundText2.startAnimation(slideAnimation2)

        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 1000)
    }
}