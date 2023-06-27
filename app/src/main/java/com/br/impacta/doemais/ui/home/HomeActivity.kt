package com.br.impacta.doemais.ui.home


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.br.impacta.doemais.ui.main.MainActivity
import com.br.impacta.doemais.R
import com.br.impacta.doemais.commom.BaseSingleton
import com.br.impacta.doemais.databinding.ActivityHomeBinding
import com.br.impacta.doemais.room.AppDatabase
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private var account: GoogleSignInAccount? = null
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var db: AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupGoogleSignIn()
        setUp ()

        account = GoogleSignIn.getLastSignedInAccount(this)
    }

    override fun onResume() {
        super.onResume()
        createDAO()
    }
    private fun createDAO() {
        db = AppDatabase.getDatabase(this)

    }


    private fun setUp () {

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_campanha_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.FirstFragment, R.id.SecondFragment))
        //setupActionBarWithNavController(navController)

        NavigationUI.setupWithNavController(binding.bottomNavigationView,navController)


//        binding.bottomNavigationView.setOnItemSelectedListener {
//            when (it.itemId) {
//                R.id.home -> {
//                    val firtFragment = FirstFragment()
//                    show(firtFragment)
//                    true
//                }
//                R.id.pedidos -> {
//                    val secondFragment = SecondFragment()
//                    show(secondFragment)
//                    true
//                }
//                else -> {true}
//            }
//        }
    }

    private fun setupGoogleSignIn() {
        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestProfile()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        binding.button2.setOnClickListener {
            signout()
        }
    }

    private fun signout() {
        Firebase.auth.signOut()
        db.userDao().deleteAll()
        val intent = Intent(this@HomeActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun isUserSignedIn(): Boolean {

        val account = GoogleSignIn.getLastSignedInAccount(this)
        return account != null

    }



    private fun show(fragment: Fragment) {

        //val drawerLayout = drawer_layout as DrawerLayout
        val fragmentManager = supportFragmentManager

        fragmentManager
            .beginTransaction()
            .replace(R.id.nav_host_campanha_fragment, fragment)
            .commit()

        //drawerLayout.closeDrawer(GravityCompat.START)
    }

    override fun onBackPressed() {
        if (BaseSingleton.isbackButton()) {
            try {
                findNavController(R.id.nav_host_andamento_fragment).popBackStack()
            } catch (e : java.lang.Exception) {
                findNavController(R.id.nav_host_finalizado_fragment).popBackStack()
            }
            BaseSingleton.setBackButton(false)
        } else {
            super.onBackPressed()
        }
    }
}