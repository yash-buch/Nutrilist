package com.binc.nutrilist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import com.binc.nutrilist.utils.Util
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SignupActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var email: String
    private lateinit var password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
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
            Log.i("test", "login successful")
        }
    }

    fun signup(view: View) {
        //validate email
        email = findViewById<EditText>(R.id.emailsu).text.toString()
        val isEmailCorrect = Util.validateEmail(email)
        //validate password
        password = findViewById<EditText>(R.id.passwordsu).text.toString()
        val ispwdCorrect = Util.validatePassword(password)
        if (isEmailCorrect && ispwdCorrect) {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        updateUI(user)
                    } else {
                        Snackbar.make(view, R.string.authentication_failed, Snackbar.LENGTH_SHORT).show()
                        Log.e("test", "" + task.exception?.message)
                        updateUI(null)
                    }
                }
        } else {
            Snackbar.make(view, R.string.signup_validation_err, Snackbar.LENGTH_SHORT).show()
        }
    }
}
