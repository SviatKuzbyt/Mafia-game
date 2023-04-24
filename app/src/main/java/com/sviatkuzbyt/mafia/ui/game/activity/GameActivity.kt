package com.sviatkuzbyt.mafia.ui.game.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.sviatkuzbyt.mafia.R
import com.sviatkuzbyt.mafia.databinding.ActivityGameBinding
import com.sviatkuzbyt.mafia.ui.elements.alertwindow.ExitGameWindow
import com.sviatkuzbyt.mafia.ui.elements.GameInterface
import com.sviatkuzbyt.mafia.ui.help.item.HelpActivity

class GameActivity : AppCompatActivity() {

    private var currentFragment: GameInterface? = null
    private lateinit var viewModel: GameViewModel
    private lateinit var exitGameWindow: ExitGameWindow
    private val binding by lazy { ActivityGameBinding.inflate(layoutInflater) }
    private val backButton get() = binding.backButton
    private val closeButton get() = binding.closeButton
    private val helpButton get() = binding.helpButton
    private val nextButton get() = binding.nextButton
    private val textToolbar get() = binding.textToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val isLoadSaveGame = intent.getBooleanExtra("loadSaveGame", false)
        viewModel = ViewModelProvider(this, GameViewModelFactory(application, isLoadSaveGame))
            .get(GameViewModel::class.java)

        viewModel.toolBarLabel.observe(this) { textToolbar.text = it }

        viewModel.isVisibleBackButton.observe(this) {
            backButton.visibility = if (it) View.VISIBLE else View.GONE
        }

        viewModel.currentFragment.observe(this) {
            supportFragmentManager.commit {
                replace(binding.FragmentContainerView.id, it)
                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                currentFragment = it as? GameInterface
            }
        }

        viewModel.textOnNextButton.observe(this) { nextButton.text = it }

        nextButton.setOnClickListener { currentFragment?.nextButtonClick() }

        backButton.setOnClickListener { currentFragment?.backButtonClick() }

        viewModel.closeActivity.observe(this) { if (it) finish() }

        viewModel.error.observe(this) { Toast.makeText(this, it, Toast.LENGTH_LONG).show() }

        exitGameWindow = ExitGameWindow(this)

        closeButton.setOnClickListener { exitGame() }

        onBackPressedDispatcher.addCallback(this) { exitGame() }

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

    private fun exitGame() {
        viewModel.exitWindowText?.let { exitGameWindow.showWindow(it) } ?: finish()
    }
}