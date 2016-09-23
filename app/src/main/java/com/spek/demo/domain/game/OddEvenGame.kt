package com.spek.demo.domain.game

open class OddEvenGame() {

    private var evenPlayerNumber: Int? = null
    private var oddPlayerNumber: Int? = null

    open fun recordChoice(player: Player, number: Int) {
        when (player) {
            Player.EVEN -> evenPlayerNumber = number
            Player.ODD -> oddPlayerNumber = number
        }
    }

    open val winner: Result
        get() {
            return ifNotNull(evenPlayerNumber, oddPlayerNumber) {
                evenNumber, oddNumber ->

                val isEven = ((evenNumber + oddNumber) % 2 == 0)
                if (isEven) Result.EVEN_PLAYER else Result.ODD_PLAYER
            } ?: Result.NO_RESULT
        }

    open fun clear() {
        evenPlayerNumber = null
        oddPlayerNumber = null
    }
}