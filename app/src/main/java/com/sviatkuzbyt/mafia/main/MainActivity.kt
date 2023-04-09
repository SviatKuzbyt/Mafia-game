package com.sviatkuzbyt.mafia.main

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sviatkuzbyt.mafia.R
import com.sviatkuzbyt.mafia.game.activity.GameActivity

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val newGameButton = findViewById<Button>(R.id.newGameButton)
        newGameButton.setOnClickListener {
            startActivity(Intent(this, GameActivity::class.java))
        }

        val menuArray = arrayOf(
            MainMenuData(R.drawable.continue_ic, R.string.continue_game, null),
            MainMenuData(R.drawable.rules_ic, R.string.rules, null),
            MainMenuData(R.drawable.settings_ic, R.string.settings, null)
        )

        val mainMenuRecycler = findViewById<RecyclerView>(R.id.mainMenuRecycler)
        mainMenuRecycler.layoutManager = LinearLayoutManager(this)
        mainMenuRecycler.adapter = MainMenuAdapter(menuArray, this)
    }
}