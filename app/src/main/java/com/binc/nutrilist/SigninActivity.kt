package com.binc.nutrilist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.binc.nutrilist.bean.UserBean
import com.binc.nutrilist.utils.Util
import com.binc.nutrilist.viewmodels.LoginUserViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SigninActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var loginUserViewModel: LoginUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        auth = FirebaseAuth.getInstance()
        loginUserViewModel = ViewModelProviders.of(this).get(LoginUserViewModel::class.java)
        loginUserViewModel.getUser().observe(this, Observer { user ->
            updateUI(user)
        })
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(UserBean(currentUser, null))
    }

    fun updateUI(user: UserBean) {
        if (user.user != null) {
            //move to next activity
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
            loginUserViewModel.loginUser(email, password).observe(this, Observer { userBean ->
                if(userBean.user == null)
                    userBean.exception?.let{ Snackbar.make(view, it, Snackbar.LENGTH_SHORT).show() }
                else
                    updateUI(userBean)
            })
        } else {
            Snackbar.make(view, R.string.signup_validation_err, Snackbar.LENGTH_SHORT).show()
        }
    }

    fun startSignupActivity(view: View) {
        val signupActivity = Intent(this@SigninActivity, SignupActivity::class.java)
        startActivity(signupActivity)
    }
}
