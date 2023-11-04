package me.ashutoshkk.carecompass.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import me.ashutoshkk.carecompass.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthenticationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)
    }
}