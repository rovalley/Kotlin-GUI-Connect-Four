/*
NAME
MainView

DESCRIPTION
This module provides a class to play a game of connect four

Created on February 24, 2024

@author: Ryan O'Valley
*/

package com.example.demo.view

import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*
import kotlin.random.Random

class MainView : View("Connect Four") {
    // create and assign scores variables
    val redScore = SimpleIntegerProperty(0)
    val blueScore = SimpleIntegerProperty(0)

    // create status variable and assign it to blue turn
    val status = SimpleStringProperty("Blue's Turn")

    // create player turn variable and assign it to B
    var playerTurn = "B"

    // create game over variable and assign it to false
    var gameOver = false

    // create game board
    val board = mutableListOf(
        mutableListOf(
            SimpleStringProperty(" "),
            SimpleStringProperty(" "),
            SimpleStringProperty(" "),
            SimpleStringProperty(" "),
            SimpleStringProperty(" "),
            SimpleStringProperty(" "),
            SimpleStringProperty(" ")
        ),
        mutableListOf(
            SimpleStringProperty(" "),
            SimpleStringProperty(" "),
            SimpleStringProperty(" "),
            SimpleStringProperty(" "),
            SimpleStringProperty(" "),
            SimpleStringProperty(" "),
            SimpleStringProperty(" ")
        ),
        mutableListOf(
            SimpleStringProperty(" "),
            SimpleStringProperty(" "),
            SimpleStringProperty(" "),
            SimpleStringProperty(" "),
            SimpleStringProperty(" "),
            SimpleStringProperty(" "),
            SimpleStringProperty(" ")
        ),
        mutableListOf(
            SimpleStringProperty(" "),
            SimpleStringProperty(" "),
            SimpleStringProperty(" "),
            SimpleStringProperty(" "),
            SimpleStringProperty(" "),
            SimpleStringProperty(" "),
            SimpleStringProperty(" ")
        ),
        mutableListOf(
            SimpleStringProperty(" "),
            SimpleStringProperty(" "),
            SimpleStringProperty(" "),
            SimpleStringProperty(" "),
            SimpleStringProperty(" "),
            SimpleStringProperty(" "),
            SimpleStringProperty(" ")
        ),
        mutableListOf(
            SimpleStringProperty(" "),
            SimpleStringProperty(" "),
            SimpleStringProperty(" "),
            SimpleStringProperty(" "),
            SimpleStringProperty(" "),
            SimpleStringProperty(" "),
            SimpleStringProperty(" ")
        )
    )

    // function for AI's turn
    fun aiTurn() {
        // continue until an open column is found
        while (true) {
            // create column variable assign to a random value in range
            var col = Random.nextInt(0, 6)
            // if the top row is empty at that column
            if (board[0][col].get() == " ") {
                // drop token at that column
                dropToken(col)
                break
            }
        }
    }

    // function to drop a token
    fun dropToken(col: Int) {
        // if game over equals false
        if (gameOver == false) {
            // set status to empty
            status.set("")
            // if the top row at that column is not a space
            if (board[0][col].get() != " ") {
                // set status to the message
                status.set("This column is full")
            } else {
                // loop through rows backwards
                for (row in 5 downTo 0) {
                    // if row and column is a space
                    if (board[row][col].get() == " ") {
                        // set player turn
                        board[row][col].set(playerTurn)
                        // if there is no winner
                        if (!checkWinner(playerTurn)) {
                            // if board is full
                            if (isBoardFull()) {
                                // set game over to true
                                gameOver = true
                                status.set("Tie Game")
                            } else {
                                // set player turn to B
                                if (playerTurn == "B") {
                                    // set player turn to R
                                    playerTurn = "R"
                                    // call AI turn function
                                    aiTurn()
                                } else {
                                    // set player turn to B
                                    playerTurn = "B"
                                    // set status to the message
                                    status.set("Blue's Turn")
                                }
                            }
                        }
                        break
                    }
                }
            }
        } else {
            // set status to the message
            status.set("Click play again")
        }
    }

    // function to check horizontal connect four
    fun checkHorFour(color: String): Boolean {
        // loop through the rows
        for (row in 0..5) {
            // loop through the columns
            for (col in 0..3) {
                // check horizontal and see if there is four in a row
                if (board[row][col].get() == color && board[row][col + 1].get() == color && board[row][col + 2].get() == color && board[row][col + 3].get() == color) {
                    return true
                }
            }
        }
        return false
    }

    // function to check for vertical connect four
    fun checkVertFour(color: String): Boolean {
        // loop through the rows
        for (row in 0..2) {
            // loop through the columns
            for (col in 0..6) {
                // check vertical and see if there is four in a row
                if (board[row][col].get() == color && board[row + 1][col].get() == color && board[row + 2][col].get() == color && board[row + 3][col].get() == color) {
                    return true
                }
            }
        }
        return false
    }

    // function to check for diagonal connect four
    fun checkDiagFour(color: String): Boolean {
        // loop through the rows
        for (row in 0..2) {
            // loop through the columns
            for (col in 0..3) {
                // check left to right diagonal and see if there is four in a row
                if (board[row][col].get() == color && board[row + 1][col + 1].get() == color && board[row + 2][col + 2].get() == color && board[row + 3][col + 3].get() == color) {
                    return true
                }
            }
        }
        // loop through the rows
        for (row in 0..2) {
            // loop through the columns
            for (col in 3..6) {
                // check right to left diagonal and see if there is four in a row
                if (board[row][col].get() == color && board[row + 1][col - 1].get() == color && board[row + 2][col - 2].get() == color && board[row + 3][col - 3].get() == color) {
                    return true
                }
            }
        }
        return false
    }

    // function to check if there is a winner
    fun checkWinner(color: String): Boolean {
        // if horizontal or vertical or diagonal connect four is met
        if (checkHorFour(color) || checkVertFour(color) || checkDiagFour(color)) {
            // if color equals B
            if (color == "B") {
                // increment blue score
                blueScore += 1
                // set status message
                status.set("Blue has won!")
            } else {
                // increment red score
                redScore += 1
                // set status message
                status.set("Red has won!")
            }
            // set game over to true
            gameOver = true
            return true
        }
        return false
    }

    // function to play game again
    fun playAgain() {
        // loop through rows
        for (row in 0..5) {
            // loop through columns
            for (col in 0..6) {
                // set board rows and column to a space
                board[row][col].set(" ")
            }
        }
        // set game over to false
        gameOver = false
        // if player turn equals B
        if (playerTurn == "B") {
            // set status message
            status.set("Blue's Turn")
        } else {
            // call AI function
            aiTurn()
        }
    }

    // function to check if game board is full
    fun isBoardFull(): Boolean {
        // loop through columns
        for (col in 0..6) {
            // if top row at the columns is a space return false
            if (board[0][col].get() == " ") {
                return false
            }
        }
        return true
    }

    // assign pane types, box type, actions, buttons, text, labels and binds
    override val root = borderpane {
        top = hbox {
            label("Blue: ")
            label {
                bind(blueScore)
            }
            label("Red: ")
            label {
                bind(redScore)
            }
        }
        bottom = label {
            bind(status)
        }
        right = button {
            text = "Play Again"
            action {
                playAgain()
            }
        }
        center = gridpane {
            row {
                button {
                    text = "Drop"
                    action {
                        dropToken(0)
                    }
                }
                button {
                    text = "Drop"
                    action {
                        dropToken(1)
                    }
                }
                button {
                    text = "Drop"
                    action {
                        dropToken(2)
                    }
                }
                button {
                    text = "Drop"
                    action {
                        dropToken(3)
                    }
                }
                button {
                    text = "Drop"
                    action {
                        dropToken(4)
                    }
                }
                button {
                    text = "Drop"
                    action {
                        dropToken(5)
                    }
                }
                button {
                    text = "Drop"
                    action {
                        dropToken(6)
                    }
                }
            }
            row {
                label {
                    bind(board[0][0])
                }
                label {
                    bind(board[0][1])
                }
                label {
                    bind(board[0][2])
                }
                label {
                    bind(board[0][3])
                }
                label {
                    bind(board[0][4])
                }
                label {
                    bind(board[0][5])
                }
                label {
                    bind(board[0][6])
                }
            }
            row {
                label {
                    bind(board[1][0])
                }
                label {
                    bind(board[1][1])
                }
                label {
                    bind(board[1][2])
                }
                label {
                    bind(board[1][3])
                }
                label {
                    bind(board[1][4])
                }
                label {
                    bind(board[1][5])
                }
                label {
                    bind(board[1][6])
                }
            }
            row {
                label {
                    bind(board[2][0])
                }
                label {
                    bind(board[2][1])
                }
                label {
                    bind(board[2][2])
                }
                label {
                    bind(board[2][3])
                }
                label {
                    bind(board[2][4])
                }
                label {
                    bind(board[2][5])
                }
                label {
                    bind(board[2][6])
                }
            }
            row {
                label {
                    bind(board[3][0])
                }
                label {
                    bind(board[3][1])
                }
                label {
                    bind(board[3][2])
                }
                label {
                    bind(board[3][3])
                }
                label {
                    bind(board[3][4])
                }
                label {
                    bind(board[3][5])
                }
                label {
                    bind(board[3][6])
                }
            }
            row {
                label {
                    bind(board[4][0])
                }
                label {
                    bind(board[4][1])
                }
                label {
                    bind(board[4][2])
                }
                label {
                    bind(board[4][3])
                }
                label {
                    bind(board[4][4])
                }
                label {
                    bind(board[4][5])
                }
                label {
                    bind(board[4][6])
                }
            }
            row {
                label {
                    bind(board[5][0])
                }
                label {
                    bind(board[5][1])
                }
                label {
                    bind(board[5][2])
                }
                label {
                    bind(board[5][3])
                }
                label {
                    bind(board[5][4])
                }
                label {
                    bind(board[5][5])
                }
                label {
                    bind(board[5][6])
                }
            }
        }
    }
}