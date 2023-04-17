package com.sviatkuzbyt.mafia.ui.game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.sviatkuzbyt.mafia.R
import com.sviatkuzbyt.mafia.data.game.elements.loadImage

class PlayerRoleActivity : AppCompatActivity() {
    private val close: Button by lazy { findViewById(R.id.closeButton) }
    private val playerTextRole: TextView by lazy { findViewById(R.id.playerTextRole) }
    private val roleText: TextView by lazy { findViewById(R.id.roleText) }
    private val roleImage: ImageView by lazy { findViewById(R.id.roleImage) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_role)

        roleText.text = intent.getStringExtra("roleText")
        playerTextRole.text = intent.getStringExtra("player")

        val roleType = intent.getIntExtra("roleType", 0)
        val image = loadImage(roleType, this)
        roleImage.setImageDrawable(image)

        close.setOnClickListener { finish() }
    }
}