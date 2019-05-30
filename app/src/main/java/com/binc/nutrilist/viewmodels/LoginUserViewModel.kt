package com.binc.nutrilist.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.binc.nutrilist.bean.UserBean
import com.google.firebase.auth.FirebaseAuth

class LoginUserViewModel: ViewModel() {
    private var user: MutableLiveData<UserBean> = MutableLiveData()
    private lateinit var auth: FirebaseAuth

    fun getUser(): LiveData<UserBean> {
        return user
    }

    fun loginUser(email: String, password: String): LiveData<UserBean> {
        auth = FirebaseAuth.getInstance()

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                var userBean: UserBean = UserBean(auth.currentUser, task.exception?.message)
                user.value = userBean
            }
        return user
    }
}