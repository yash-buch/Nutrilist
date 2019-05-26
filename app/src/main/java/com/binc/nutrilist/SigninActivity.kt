package com.binc.nutrilist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class SigninActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
    }

    fun startSignupActivity(view: View) {
        val signupActivity = Intent(this@SigninActivity, SignupActivity::class.java)
        startActivity(signupActivity)
    }
}
