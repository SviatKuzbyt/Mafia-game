package com.sviatkuzbyt.mafia.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.datastore.preferences.preferencesDataStore
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sviatkuzbyt.mafia.R
import com.sviatkuzbyt.mafia.ui.game.activity.GameActivity

val Context.settingsStore by preferencesDataStore(name = "settings")
class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val newGameButton = findViewById<Button>(R.id.newGameButton)
        newGameButton.setOnClickListener {
            startActivity(Intent(this, GameActivity::class.java))
        }

        val mainMenuRecycler = findViewById<RecyclerView>(R.id.mainMenuRecycler)
        mainMenuRecycler.layoutManager = LinearLayoutManager(this)
        mainMenuRecycler.adapter = MainMenuAdapter(getMenuArray(this), this)

        ViewCompat.setOnApplyWindowInsetsListener(mainMenuRecycler) { view, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updateLayoutParams<ViewGroup.MarginLayoutParams>{
                bottomMargin = insets.bottom+32
            }
            WindowInsetsCompat.CONSUMED
        }
    }
}