package com.spek.demo.presenter

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.reset
import com.nhaarman.mockito_kotlin.spy
import com.nhaarman.mockito_kotlin.verify
import com.spek.demo.domain.game.OddEvenGame
import com.spek.demo.domain.game.Player
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.context
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith

@RunWith(JUnitPlatform::class)
class GamePresenterSpek : Spek({
    describe("a GamePresenter") {
        val view: GamePresenter.View = mock()
        val game: OddEvenGame = spy()

        val gamePresenter = GamePresenter(game, view)

        val evenPlayerNumber = 3
        val oddPlayerNumber = 2

        afterEach {
            reset(view)
            reset(game)

            game.clear()
        }

        context("when even player picks a $evenPlayerNumber") {
            beforeEach { gamePresenter.onEvenPlayerChoiceConfirmation(evenPlayerNumber) }

            it("should record even player number as $evenPlayerNumber") {
                verify(game).recordChoice(Player.EVEN, evenPlayerNumber)
            }

            it("should disable even player ability to change his pick") {
                verify(view).setEvenPlayerPickSectionEnabled(false)
            }

            context("when a winner is requested") {
                beforeEach { gamePresenter.onWinnerRequested() }

                it("should  show the confirmation dialog") {
                    verify(view).alertChoiceIsMissing()
                }
            }

            context("when the odd player picks a $oddPlayerNumber") {
                beforeEach { gamePresenter.onOddPlayerChoiceConfirmation(oddPlayerNumber) }

                it("should record odd player number as $oddPlayerNumber") {
                    verify(game).recordChoice(Player.ODD, oddPlayerNumber)
                }

                it("should disable odd player ability to change his pick") {
                    verify(view).setOddPlayerPickSectionEnabled(false)
                }

                context("when the winner is requested") {
                    beforeEach { gamePresenter.onWinnerRequested() }

                    it("should retrieve the game result") {
                        verify(game).winner
                    }

                    it("displays the correct winner") {
                        verify(view).displayWinner(Player.ODD)
                    }

                    it("should hide the button that is able to request the winner") {
                        verify(view).setGetResultsButtonVisibility(false)
                    }
                }
            }
        }
    }
})