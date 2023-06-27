package com.br.impacta.doemais.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.br.impacta.doemais.R
import com.br.impacta.doemais.commom.Constants.Companion.TXT_CRSL_AJUDA
import com.br.impacta.doemais.commom.Constants.Companion.TXT_CRSL_DOE
import com.br.impacta.doemais.commom.Constants.Companion.TXT_CRSL_PECA
import com.br.impacta.doemais.databinding.ActivityMainBinding
import com.br.impacta.doemais.room.AppDatabase
import com.br.impacta.doemais.ui.home.HomeActivity
import com.br.impacta.doemais.ui.login.CreateAccountActivity
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemChangeListener
import com.denzcoskun.imageslider.models.SlideModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var name: String
    private lateinit var email: String
    private var account: GoogleSignInAccount? = null
    private lateinit var auth: FirebaseAuth
    private var sampleImages = intArrayOf(R.drawable.ic_launcher_foreground, com.google.android.material.R.drawable.abc_ab_share_pack_mtrl_alpha)
    private lateinit var txtCarousel: String
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createCarousel()
        auth = Firebase.auth
        setupGoogleSignIn()
        binding.btnCreateAccount.setOnClickListener {
            val intent = Intent(this@MainActivity, CreateAccountActivity::class.java)
            startActivity(intent)
        }
        createDAO()
        binding.btnSignIn.setOnClickListener {
            signinButton()
        }
    }

    private fun createDAO() {
        db = AppDatabase.getDatabase(this)
    }

    private fun createCarousel() {
        val imageList = ArrayList<SlideModel>() // Create image list

        // imageList.add(SlideModel("String Url" or R.drawable)
        // imageList.add(SlideModel("String Url" or R.drawable, "title") You can add title
        binding.tvCarousel.text = TXT_CRSL_DOE
        imageList.add(SlideModel("https://i.pinimg.com/736x/e3/a5/4c/e3a54c044529eaebcf03df2e53bc6559.jpg", scaleType = ScaleTypes.FIT))
        imageList.add(SlideModel("https://b1508966.smushcdn.com/1508966/wp-content/uploads/Sarah-Charitable-Giving-1080x675.jpg?lossy=0&strip=1&webp=1",scaleType = ScaleTypes.FIT))
        imageList.add(SlideModel("https://img.freepik.com/free-vector/people-volunteering-donating-money-items_53876-64647.jpg",scaleType = ScaleTypes.FIT))

        binding.imageSlider.setImageList(imageList)
        binding.imageSlider.setItemChangeListener(object : ItemChangeListener {
            override fun onItemChanged(position: Int) {

                when(position) {
                    0 -> txtCarousel = TXT_CRSL_DOE
                    1 -> txtCarousel = TXT_CRSL_PECA
                    2 -> txtCarousel = TXT_CRSL_AJUDA
                }

                binding.tvCarousel.text = txtCarousel


            }

        })


    }

    private fun signinButton() {
        if (binding.etEmail.text.toString() == "") {
            Toast.makeText(baseContext, "Preencha o campo de email",
                Toast.LENGTH_SHORT).show()
        }

        if (binding.etPwd.text.toString() == "") {
            Toast.makeText(baseContext, "Preencha o campo de senha",
                Toast.LENGTH_SHORT).show()
        }


        if(binding.etEmail.text.toString() != "" && binding.etPwd.text.toString() != "" ) {
            sign()
        }

    }

    private fun sign(){
        auth.signInWithEmailAndPassword(binding.etEmail.text.toString(), binding.etPwd.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    //Log.d(TAG, "signInWithEmail:success")
                    //val user = auth.currentUser
                    val intent = Intent(this@MainActivity, HomeActivity::class.java)
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    //Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Falha a autenticação.",
                        Toast.LENGTH_SHORT).show()
                    //updateUI(null)
                }
            }
    }


    private fun signIn() {
        val intent = mGoogleSignInClient.signInIntent
        startActivityForResult(intent, RC_SIGN_IN)
    }

    private fun setupGoogleSignIn() {
        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestProfile()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        binding.signInButton.setOnClickListener {
            signIn()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            handleSignData(data)

        }
    }

    private fun handleSignData(data: Intent?) {
        // The Task returned from this call is always completed, no need to attach
        // a listener.
        GoogleSignIn.getSignedInAccountFromIntent(data)
            .addOnCompleteListener {
                "isSuccessful ${it.isSuccessful}".print()
                if (it.isSuccessful){
                    intentHome(it)
                } else {
                    // authentication failed
                    "exception ${it.exception}".print()
                }
            }

    }

    private fun intentHome(googleSignInAccount: Task<GoogleSignInAccount>) {
        val intent = Intent(this@MainActivity, CreateAccountActivity::class.java)
        intent.putExtra("name", googleSignInAccount.result?.displayName)
        intent.putExtra("email", googleSignInAccount.result?.email)
        startActivity(intent)
    }

    fun Any.print(){
        Log.v(TAG_KOTLIN, " $this")
    }

    companion object{
        const val RC_SIGN_IN = 0
        const val TAG_KOTLIN = "TAG_KOTLIN"
    }


}