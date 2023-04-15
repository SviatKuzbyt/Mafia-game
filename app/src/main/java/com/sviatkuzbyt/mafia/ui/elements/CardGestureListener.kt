package com.sviatkuzbyt.mafia.ui.elements

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
        val diffX = e2.x.minus(e1.x )
        val diffY = e2.y.minus(e1.y)

        if (Math.abs(diffX) > Math.abs(diffY) &&
            Math.abs(diffX) > SWIPE_THRESHOLD &&
            Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD
        ) {
            if (diffX > 0) {
                viewModel.previousPlayer()
            } else {
                viewModel.nextPlayer()
            }
            return true
        }
        return false
    }
}