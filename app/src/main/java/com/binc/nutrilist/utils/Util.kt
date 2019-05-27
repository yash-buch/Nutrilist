package com.binc.nutrilist.utils

import android.widget.EditText

class Util {
    companion object {
        @JvmStatic fun validateEmail(email: String): Boolean {
            return email.contains("@")
        }

        @JvmStatic fun validatePassword(password: String): Boolean {
            return password.length > 4
        }
    }
}