package me.ashutoshkk.carecompass.presentation.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.lifecycleScope
import me.ashutoshkk.carecompass.R
import me.ashutoshkk.carecompass.data.datastore.DataStoreManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        lateinit var intent : Intent

        lifecycleScope.launch(Dispatchers.IO) {
            val dataStoreManager = DataStoreManager(this@SplashActivity)
            dataStoreManager.getLogInInfo().collect{
                intent = if(it.logInState){
                    Intent(this@SplashActivity, MainActivity::class.java)
                } else{
                    Intent(this@SplashActivity, AuthenticationActivity::class.java)
                }
            }
        }

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(intent)
            finish()
        }, 2000)
    }
}