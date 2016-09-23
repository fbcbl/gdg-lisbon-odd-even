package com.spek.demo.domain.game

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.context
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import kotlin.test.assertEquals

@RunWith(JUnitPlatform::class)
class GameSpek : Spek({
    describe("playing odd-even") {

        val oddEvenGame = OddEvenGame()

        afterEach {
            oddEvenGame.clear()
        }

        context("odd player picks an odd number") {
            beforeEach { oddEvenGame.recordChoice(Player.ODD, 3) }

            context("even player picks an even number") {
                beforeEach { oddEvenGame.recordChoice(Player.EVEN, 4) }

                it("should declare the odd player as the winner") {
                    assertEquals(Result.ODD_PLAYER, oddEvenGame.winner)
                }
            }

            context("even player picks an odd number") {
                beforeEach { oddEvenGame.recordChoice(Player.EVEN, 3) }

                it("should declare the even player as the winner") {
                    assertEquals(Result.EVEN_PLAYER, oddEvenGame.winner)
                }
            }

            context("odd player doesn't pick a number") {
                it("should not have a winner") {
                    assertEquals(Result.NO_RESULT, oddEvenGame.winner)
                }
            }
        }

        context("odd player picks an even number") {
            beforeEach { oddEvenGame.recordChoice(Player.ODD, 6) }

            context("even player picks an even number") {
                beforeEach { oddEvenGame.recordChoice(Player.EVEN, 2) }

                it("should declare the even player as the winner") {
                    assertEquals(Result.EVEN_PLAYER, oddEvenGame.winner)
                }
            }

            context("even player picks an odd number") {
                beforeEach { oddEvenGame.recordChoice(Player.EVEN, 3) }

                it("should declare the odd player as the winner") {
                    assertEquals(Result.ODD_PLAYER, oddEvenGame.winner)
                }
            }

            context("even player doesn't pick a number") {
                it("should not have a winner") {
                    assertEquals(Result.NO_RESULT, oddEvenGame.winner)
                }
            }
        }
    }
})
