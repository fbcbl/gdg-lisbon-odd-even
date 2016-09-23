package com.spek.demo.domain.game

import org.junit.After
import org.junit.Assert
import org.junit.Test

class DemoJunitTest {

    var oddEvenGame = OddEvenGame();

    @Test
    fun if_player_odd_chooses_odd_number_and_player_even_chooses_odd_number_then_the_winner_is_player_odd() {
        // Arrange
        oddEvenGame.recordChoice(Player.EVEN, 4)
        oddEvenGame.recordChoice(Player.ODD, 3)

        // Act
        val winner = oddEvenGame.winner

        // Assert
        Assert.assertEquals(Result.ODD_PLAYER, winner)
    }

    @After
    fun teardown() {
        oddEvenGame.clear()
    }
}