package com.binc.nutrilist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import com.binc.nutrilist.utils.Util
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SigninActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var email: String
    private lateinit var password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        auth = FirebaseAuth.getInstance()
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            //move to next activity
            Log.i("test", ""+ user.email)
            val mainActivity = Intent(this@SigninActivity, MainActivity::class.java)
            startActivity(mainActivity)
            finish()
        }
    }

    fun login(view: View) {
        //validate email
        email = findViewById<EditText>(R.id.email).text.toString()
        val isEmailCorrect = Util.validateEmail(email)
        //validate password
        password = findViewById<EditText>(R.id.password).text.toString()
        val ispwdCorrect = Util.validatePassword(password)
        if (isEmailCorrect && ispwdCorrect) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        updateUI(user)
                    } else {
                        Log.e("test", "" + task.exception?.message)
                        Snackbar.make(view, R.string.authentication_failed, Snackbar.LENGTH_SHORT).show()
                        updateUI(null)
                    }

                }
        } else {
            Snackbar.make(view, R.string.signup_validation_err, Snackbar.LENGTH_SHORT).show()
        }
    }

    fun startSignupActivity(view: View) {
        val signupActivity = Intent(this@SigninActivity, SignupActivity::class.java)
        startActivity(signupActivity)
    }
}
