package com.sviatkuzbyt.mafia.ui.elements

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import com.sviatkuzbyt.mafia.data.game.elements.CardRole
import com.sviatkuzbyt.mafia.databinding.FragmentRolesBinding

class CardAnimation(private val binding: FragmentRolesBinding, screenWidth: Float) {
    private lateinit var targetRole: CardRole

    private val animCenterToLeft = ObjectAnimator.ofFloat(
        binding.roleContainer,
        View.TRANSLATION_X,
        0f,
        -screenWidth,
    ).apply {
        duration = 250
    }
    private val animRightToCenter = ObjectAnimator.ofFloat(
        binding.roleContainer,
        View.TRANSLATION_X,
        screenWidth,
        0f
    ).apply {
        duration = 250
        changeData()
    }
    private val animCenterToRight = ObjectAnimator.ofFloat(
        binding.roleContainer,
        View.TRANSLATION_X,
        0f,
        screenWidth,
    ).apply {
        duration = 250
    }
    private val animLeftToCenter = ObjectAnimator.ofFloat(
        binding.roleContainer,
        View.TRANSLATION_X,
        -screenWidth,
        0f
    ).apply {
        duration = 250
        changeData()
    }

    private fun Animator.changeData(){
        addListener(object: AnimatorListenerAdapter(){
            override fun onAnimationStart(animation: Animator) {
                super.onAnimationStart(animation)
                binding.playerTextRole.text = targetRole.player
                binding.roleText.text = targetRole.role
                binding.roleImage.setImageDrawable(targetRole.image)

            }
        })
    }

    fun playAnimation(data: CardRole, isNextAnimation: Boolean){
        targetRole = data
        AnimatorSet().apply {
            if (isNextAnimation)
                play(animCenterToLeft).before(animRightToCenter)
            else
                play(animCenterToRight).before(animLeftToCenter)
            start()
        }
    }
}