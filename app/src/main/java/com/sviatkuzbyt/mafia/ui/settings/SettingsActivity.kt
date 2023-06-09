package com.sviatkuzbyt.mafia.ui.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import com.sviatkuzbyt.mafia.R
import com.sviatkuzbyt.mafia.ui.elements.alertwindow.RemoveDataWindow

class SettingsActivity : AppCompatActivity() {

    private val viewModel by viewModels<SettingsViewModel>()
    private lateinit var removeDataWindow: RemoveDataWindow

    private val checkBoxAutoPlayer: CheckBox by lazy { findViewById(R.id.checkBoxAutoPlayer) }
    private val checkBoxAutoWin: CheckBox by lazy { findViewById(R.id.checkBoxAutoWin) }
    private val buttonClearData: Button by lazy { findViewById(R.id.buttonClearData) }
    private val closeButtonSettings: Button by lazy { findViewById(R.id.closeButtonSettings) }
    private val textAbout: TextView by lazy { findViewById(R.id.textAbout) }
    private val spinnerRolesLang: Spinner by lazy { findViewById(R.id.spinnerRolesLang) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        checkBoxAutoPlayer.setOnClickListener{
            viewModel.setAutoPlayer(checkBoxAutoPlayer.isChecked)
        }
        viewModel.autoPlayer.observe(this){
            checkBoxAutoPlayer.isChecked = it
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

        viewModel.message.observe(this){
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }

        closeButtonSettings.setOnClickListener { finish() }
        textAbout.movementMethod = LinkMovementMethod.getInstance()

        viewModel.roleLanguage.observe(this){
            spinnerRolesLang.setSelection(it, false)
        }

        spinnerRolesLang.setSelection(0, false)
        spinnerRolesLang.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long){
                viewModel.setRoleLanguage(p2)
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
    }

    private fun initRemoveDataWindow(){
        if(!::removeDataWindow.isInitialized)
            removeDataWindow = RemoveDataWindow(this, viewModel)
    }
}