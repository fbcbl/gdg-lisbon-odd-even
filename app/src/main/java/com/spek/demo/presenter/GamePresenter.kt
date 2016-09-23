package com.spek.demo.presenter

import com.spek.demo.domain.game.OddEvenGame
import com.spek.demo.domain.game.Player
import com.spek.demo.domain.game.Result

class GamePresenter(
        private val game: OddEvenGame,
        private val view: View) {

    fun onEvenPlayerChoiceConfirmation(number: Int) {
        game.recordChoice(Player.EVEN, number)
        view.setEvenPlayerPickSectionEnabled(false)
    }

    fun onOddPlayerChoiceConfirmation(number: Int) {
        game.recordChoice(Player.ODD, number)
        view.setOddPlayerPickSectionEnabled(false)
    }

    fun reset() {
        with(view) {
            clearPicks()
            setEvenPlayerPickSectionEnabled(true)
            setOddPlayerPickSectionEnabled(true)
            setGetResultsButtonVisibility(true)
            clearWinner()
        }

        game.clear()
    }

    fun onWinnerRequested() {
        when (game.winner) {
            Result.ODD_PLAYER -> {
                with(view) {
                    displayWinner(Player.ODD)
                    setGetResultsButtonVisibility(false)
                }
            }
            Result.EVEN_PLAYER -> with(view) {
                displayWinner(Player.EVEN)
                setGetResultsButtonVisibility(false)
            }
            else -> view.alertChoiceIsMissing()
        }
    }

    interface View {
        fun displayWinner(player: Player)

        fun setOddPlayerPickSectionEnabled(isEnabled: Boolean)
        fun setEvenPlayerPickSectionEnabled(isVisible: Boolean)
        fun setGetResultsButtonVisibility(isVisible: Boolean)

        fun clearPicks()
        fun clearWinner()

        fun alertChoiceIsMissing()
    }
}