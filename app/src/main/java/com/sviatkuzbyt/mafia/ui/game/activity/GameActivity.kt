package com.sviatkuzbyt.mafia.ui.game.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import com.sviatkuzbyt.mafia.R
import com.sviatkuzbyt.mafia.ui.game.elements.GameInterface

class GameActivity : AppCompatActivity() {

    private var currentFragment: GameInterface? = null
    val viewModel by viewModels<GameViewModel>()
    val closeButton: Button by lazy { findViewById(R.id.closeButton) }
    val helpButton: Button by lazy { findViewById(R.id.helpButton) }
    val textToolbar: TextView by lazy { findViewById(R.id.textToolbar) }
    val nextButton: Button by lazy { findViewById(R.id.nextButton) }
    val backButton: Button by lazy { findViewById(R.id.backButton) }
    @SuppressLint("CommitTransaction")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

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
            currentFragment?.mainButtonClick()
        }
    }
}