package com.binc.nutrilist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.binc.nutrilist.bean.UserBean
import com.binc.nutrilist.utils.Util
import com.binc.nutrilist.viewmodels.CreateUserViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class SignupActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var createUserViewModel: CreateUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        auth = FirebaseAuth.getInstance()
        createUserViewModel = ViewModelProviders.of(this).get(CreateUserViewModel::class.java)
        createUserViewModel.getUser().observe(this, Observer { user ->
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
            val mainActivity: Intent = Intent(this@SignupActivity, MainActivity::class.java)
            startActivity(mainActivity)
            finish()
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
            createUserViewModel.createUser(email, password).observe(this, Observer { userBean ->
                if(userBean.user == null)
                    userBean.exception?.let{ Snackbar.make(view, it, Snackbar.LENGTH_SHORT).show() }
                else
                    updateUI(userBean)
            })
        } else {
            Snackbar.make(view, R.string.signup_validation_err, Snackbar.LENGTH_SHORT).show()
        }
    }
}
