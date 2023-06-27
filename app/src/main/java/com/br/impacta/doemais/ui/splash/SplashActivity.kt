package com.br.impacta.doemais.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.br.impacta.doemais.ui.main.MainActivity
import com.br.impacta.doemais.R
import com.br.impacta.doemais.databinding.ActivitySplashBinding
import com.br.impacta.doemais.ui.home.HomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        val handle = Handler()
        handle.postDelayed(Runnable { showLogin() }, 2000)
    }


    private fun showLogin() {
        val currentUser = auth.currentUser
        if(currentUser != null){
            val intent = Intent(this@SplashActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}