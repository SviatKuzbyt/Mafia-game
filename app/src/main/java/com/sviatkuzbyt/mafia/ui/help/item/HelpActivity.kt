package com.sviatkuzbyt.mafia.ui.help.item

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.sviatkuzbyt.mafia.R

class HelpActivity : AppCompatActivity() {

    val buttonCloseHelp: Button by lazy { findViewById(R.id.buttonCloseHelp) }
    val helpName: TextView by lazy { findViewById(R.id.helpName) }
    val helpText: TextView by lazy { findViewById(R.id.helpText) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)

        val helpId = intent.getIntExtra("id", 0)
        val viewModel =
            ViewModelProvider(this, HelpViewModelFactory(this.application, helpId)
            )[HelpViewModel::class.java]

        viewModel.helpData.observe(this){
            helpName.text = it.name
            helpText.text = it.text
        }

        buttonCloseHelp.setOnClickListener { finish() }
    }
}