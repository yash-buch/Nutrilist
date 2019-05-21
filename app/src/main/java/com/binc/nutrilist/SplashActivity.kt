package com.binc.nutrilist

import android.annotation.TargetApi
import android.content.Intent
import android.graphics.drawable.Animatable2
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()
    }

    @TargetApi(Build.VERSION_CODES.M)
    override fun onResume() {
        super.onResume()
        val img = findViewById<ImageView>(R.id.imageView)
        val dr = img.drawable as AnimatedVectorDrawable
        dr.registerAnimationCallback(@RequiresApi(Build.VERSION_CODES.M)
        object : Animatable2.AnimationCallback() {
            override fun onAnimationEnd(drawable: Drawable?) {
                super.onAnimationEnd(drawable)
                val signinActivity = Intent(this@SplashActivity, SigninActivity::class.java)
                startActivity(signinActivity)
                finish()
            }
        })
        dr.start()
    }
}
