package com.sviatkuzbyt.mafia.ui.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import com.sviatkuzbyt.mafia.R
import com.sviatkuzbyt.mafia.ui.elements.alertwindow.RemoveDataWindow

class SettingsActivity : AppCompatActivity() {

    private val checkBoxAutoPlayer: CheckBox by lazy { findViewById(R.id.checkBoxAutoPlayer) }
    private val checkBoxAutoWin: CheckBox by lazy { findViewById(R.id.checkBoxAutoWin) }
    private val buttonClearData: Button by lazy { findViewById(R.id.buttonClearData) }
    private val viewModel by viewModels<SettingsViewModel>()
    private val closeButtonSettings: Button by lazy { findViewById(R.id.closeButtonSettings) }
    private val textAbout: TextView by lazy { findViewById(R.id.textAbout) }
    lateinit var removeDataWindow: RemoveDataWindow


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        viewModel.autoPlayer.observe(this){
            checkBoxAutoPlayer.isChecked = it
        }

        checkBoxAutoPlayer.setOnClickListener{
            viewModel.setAutoPlayer(checkBoxAutoPlayer.isChecked)
        }

        checkBoxAutoWin.setOnClickListener{
            viewModel.setAutoWin(checkBoxAutoWin.isChecked)
        }

        viewModel.autoWin.observe(this){
            checkBoxAutoWin.isChecked = it
        }

        buttonClearData.setOnClickListener {
            initRemoveDataWindow()
            removeDataWindow.showWindow()
        }

        viewModel.error.observe(this){
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }

        closeButtonSettings.setOnClickListener { finish() }
        textAbout.movementMethod = LinkMovementMethod.getInstance()

    }

    private fun initRemoveDataWindow(){
        if(!::removeDataWindow.isInitialized)
            removeDataWindow = RemoveDataWindow(this, viewModel)
    }
}