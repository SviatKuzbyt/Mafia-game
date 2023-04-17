package com.sviatkuzbyt.mafia.ui.game.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.sviatkuzbyt.mafia.R
import com.sviatkuzbyt.mafia.ui.elements.ExitGameWindow
import com.sviatkuzbyt.mafia.ui.elements.GameInterface
import com.sviatkuzbyt.mafia.ui.help.item.HelpActivity

class GameActivity : AppCompatActivity() {

    private var currentFragment: GameInterface? = null
    lateinit var viewModel: GameViewModel
    lateinit var exitGameWindow: ExitGameWindow
    val closeButton: Button by lazy { findViewById(R.id.closeButton) }
    val helpButton: Button by lazy { findViewById(R.id.helpButton) }
    val textToolbar: TextView by lazy { findViewById(R.id.textToolbar) }
    val nextButton: Button by lazy { findViewById(R.id.nextButton) }
    val backButton: Button by lazy { findViewById(R.id.backButton) }
    @SuppressLint("CommitTransaction")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val isLoadSaveGame = intent.getBooleanExtra("loadSaveGame", false)
        viewModel = ViewModelProvider(this,
            GameViewModelFactory(this.application, isLoadSaveGame)
        )[GameViewModel::class.java]

        viewModel.toolBarLabel.observe(this){
            textToolbar.text = it
        }

        viewModel.isVisibleBackButton.observe(this){
            backButton.visibility =
                if(it) View.VISIBLE
                else View.GONE
        }

        viewModel.currentFragment.observe(this){
            supportFragmentManager.commit {
                replace(R.id.FragmentContainerView, it)
                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                supportFragmentManager.beginTransaction()
                currentFragment = it as? GameInterface
            }
        }

        viewModel.textOnNextButton.observe(this){
            nextButton.text = it
        }

        nextButton.setOnClickListener {
            currentFragment?.nextButtonClick()
        }

        backButton.setOnClickListener {
            currentFragment?.backButtonClick()
        }

        viewModel.closeActivity.observe(this){
            if (it) finish()
        }

        viewModel.error.observe(this){
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }

        exitGameWindow = ExitGameWindow(this)

        closeButton.setOnClickListener { exitGame() }

        onBackPressedDispatcher.addCallback(this) {
            exitGame()
        }

        helpButton.setOnClickListener {
        val helpId = viewModel.idForHelpButton
            if(helpId == null)
                Toast.makeText(this, getString(R.string.no_help), Toast.LENGTH_SHORT).show()
            else{
                val intent = Intent(this, HelpActivity::class.java)
                intent.putExtra("id", helpId)
                startActivity(intent)
            }
        }
    }

    private fun exitGame(){
        if(viewModel.exitWindowText != null)
            exitGameWindow.showWindow(viewModel.exitWindowText!!)
        else finish()
    }
}