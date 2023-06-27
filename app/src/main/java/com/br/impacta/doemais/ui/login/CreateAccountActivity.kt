package com.br.impacta.doemais.ui.login

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.br.impacta.doemais.R
import com.br.impacta.doemais.databinding.ActivityCreateAccountBinding
import com.br.impacta.doemais.ui.home.HomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase


class CreateAccountActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateAccountBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mAuthListener: AuthStateListener
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)
        binding = ActivityCreateAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        val b: Bundle? = intent.extras

        if (b != null) {
            binding.etName.setText(b.get("name") as String)
            binding.etEmail.setText(b.get("email") as String)
        }

        binding.button.setOnClickListener {
            createUser(binding.etEmail.text.toString(), binding.etPwd.text.toString())
        }


    }



    private fun createUser(email: String, pwd: String){
        auth.createUserWithEmailAndPassword(email, pwd)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    updateUser(user)
                    intentHome()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun updateUser(user: FirebaseUser?) {
        val profileUpdates = userProfileChangeRequest {
            displayName = binding.etName.text.toString()
        }

        user!!.updateProfile(profileUpdates)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User profile updated.")
                }
            }
    }

    private fun intentHome() {
        val intent = Intent(this@CreateAccountActivity, HomeActivity::class.java)
        startActivity(intent)
    }





}