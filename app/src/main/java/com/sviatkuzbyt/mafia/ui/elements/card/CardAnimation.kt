package com.sviatkuzbyt.mafia.ui.elements.card

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import com.sviatkuzbyt.mafia.data.game.elements.CardRole
import com.sviatkuzbyt.mafia.databinding.FragmentRolesBinding

class CardAnimation(private val binding: FragmentRolesBinding, screenWidth: Float) {

    private lateinit var targetRole: CardRole
    private val animRightToCenter = showAnimation(screenWidth)
    private val animLeftToCenter = showAnimation(-screenWidth)
    private val animCenterToLeft = hideAnimation(-screenWidth)
    private val animCenterToRight = hideAnimation(screenWidth)

    private fun showAnimation(startX: Float): ObjectAnimator {
        return ObjectAnimator.ofFloat(
            binding.roleContainer,
            View.TRANSLATION_X,
            startX,
            0f
        ).apply {
            duration = 250
            changeData()
        }
    }

    private fun hideAnimation(endX: Float): ObjectAnimator {
        return ObjectAnimator.ofFloat(
            binding.roleContainer,
            View.TRANSLATION_X,
            0f,
            endX,
        ).apply {
            duration = 250
        }
    }

    private fun ObjectAnimator.changeData() {
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