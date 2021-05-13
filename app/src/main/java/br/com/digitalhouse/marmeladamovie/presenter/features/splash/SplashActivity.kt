package br.com.digitalhouse.marmeladamovie.presenter.features.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import br.com.digitalhouse.marmeladamovie.R
import br.com.digitalhouse.marmeladamovie.presenter.features.home.HomeActivity
import br.com.digitalhouse.marmeladamovie.presenter.features.loginfeats.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val auth = FirebaseAuth.getInstance()
        val logged = auth.currentUser != null
        val intent = if (logged) {
            Intent(this, HomeActivity::class.java)
        } else {
            Intent(this, LoginActivity::class.java)
        }

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(intent)
            finish()
        }, 3000)
    }
}