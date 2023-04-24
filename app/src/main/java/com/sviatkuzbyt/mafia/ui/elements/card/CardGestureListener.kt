package com.sviatkuzbyt.mafia.ui.elements.card

import android.view.GestureDetector
import android.view.MotionEvent
import com.sviatkuzbyt.mafia.ui.game.roles.RolesViewModel

class CardGestureListener(private val viewModel: RolesViewModel) : GestureDetector.SimpleOnGestureListener() {
    private val SWIPE_THRESHOLD = 100
    private val SWIPE_VELOCITY_THRESHOLD = 100

    override fun onFling(
        e1: MotionEvent,
        e2: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        val diffX = e2.x - e1.x
        val diffY = e2.y - e1.y

        if (kotlin.math.abs(diffX) > kotlin.math.abs(diffY) &&
            kotlin.math.abs(diffX) > SWIPE_THRESHOLD &&
            kotlin.math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD
        ) {
            if (diffX > 0) viewModel.previousPlayer()
            else viewModel.nextPlayer()
            return true
        }
        return false
    }
}