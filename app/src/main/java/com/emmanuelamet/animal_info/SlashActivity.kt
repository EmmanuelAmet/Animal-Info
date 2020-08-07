package com.emmanuelamet.animal_info

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.emmanuelamet.animal_info.view.MainActivity
import kotlinx.android.synthetic.main.activity_slash.*

class SlashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slash)

        splash_logo.alpha = 0f
        splash_logo.animate().setDuration(3000).alpha(1f).withEndAction {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(
                android.R.anim.slide_in_left, android.R.anim.slide_out_right
            )
            finish()
        }
    }
}