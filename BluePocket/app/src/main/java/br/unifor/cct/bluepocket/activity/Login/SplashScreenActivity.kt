package br.unifor.cct.bluepocket.activity.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import br.unifor.cct.bluepocket.R

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)


       // DataBaseUtil.getInstance(this)

        val handler = Handler()
        handler.postDelayed({
            val it = Intent(this,
                LoginActivity::class.java)
            startActivity(it)
            finish()
        },2000)
    }
}