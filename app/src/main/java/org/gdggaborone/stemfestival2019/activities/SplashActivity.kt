package org.gdggaborone.stemfestival2019.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_splash.*
import org.gdggaborone.stemfestival2019.R
import java.util.*

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        copyright_txt.text = "Copyright \u00A9 ${Calendar.getInstance().get(Calendar.YEAR)} STEM Festival "


        Handler().postDelayed({
            //do your stuff here.
            startActivity(Intent(this, MainActivity::class.java))
        }, UI_ANIMATION_DELAY.toLong())


    }


    companion object {
        private const val UI_ANIMATION_DELAY = 300
    }
}

