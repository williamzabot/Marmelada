package br.com.digitalhouse.marmeladamovie.presenter.features.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import br.com.digitalhouse.marmeladamovie.R
import br.com.digitalhouse.marmeladamovie.presenter.features.home.HomeActivity
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val goToHome = Intent(this, HomeActivity::class.java)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(goToHome)
            finish()
        }, 3000)
    }
}