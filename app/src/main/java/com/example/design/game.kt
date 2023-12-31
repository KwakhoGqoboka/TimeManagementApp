package com.example.design

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView

class game : AppCompatActivity(), View.OnClickListener {

    private var playerOneActive = false
    private lateinit var playerOneScore: TextView
    private lateinit var playerTwoScore: TextView
    private lateinit var playerStatus: TextView
    private val buttons = Array<Button?>(9) { null }
    private lateinit var reset: Button
    private lateinit var playagain: Button
    private val gameState = IntArray(9) { 2 }
    private val winningPositions = arrayOf(
        intArrayOf(0, 1, 2), intArrayOf(3, 4, 5), intArrayOf(6, 7, 8),
        intArrayOf(0, 3, 6), intArrayOf(1, 4, 7), intArrayOf(2, 5, 8),
        intArrayOf(0, 4, 8), intArrayOf(2, 4, 6)
    )
    private var rounds = 0
    private var playerOneScoreCount = 0
    private var playerTwoScoreCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game)

        playerOneScore = findViewById(R.id.score_Player1)
        playerTwoScore = findViewById(R.id.score_Player2)
        playerStatus = findViewById(R.id.textStatus)
        reset = findViewById(R.id.btn_reset)
        playagain = findViewById(R.id.btn_play_again)

        buttons[0] = findViewById(R.id.btn0)
        buttons[1] = findViewById(R.id.btn1)
        buttons[2] = findViewById(R.id.btn2)
        buttons[3] = findViewById(R.id.btn3)
        buttons[4] = findViewById(R.id.btn4)
        buttons[5] = findViewById(R.id.btn5)
        buttons[6] = findViewById(R.id.btn6)
        buttons[7] = findViewById(R.id.btn7)
        buttons[8] = findViewById(R.id.btn8)

        for (i in buttons.indices) {
            buttons[i]?.setOnClickListener(this)
        }

        playerOneScoreCount = 0
        playerTwoScoreCount = 0
        playerOneActive = true
        rounds = 0
    }

    override fun onClick(view: View) {
        if ((view as Button).text.toString() != "") {
            return
        } else if (checkWinner()) {
            return
        }

        val buttonID = resources.getResourceEntryName(view.id)
        val gameStatePointer = buttonID.substring(buttonID.length - 1).toInt()

        val categoriesButton = findViewById<ImageButton>(R.id.imageButton)
        categoriesButton.setOnClickListener {
            val intent = Intent(this, AddCatergory::class.java)
            startActivity(intent)
        }

        if (playerOneActive) {
            view.text = "X"
            view.setTextColor(Color.parseColor("#ffc34a"))
            gameState[gameStatePointer] = 0
        } else {
            view.text = "O"
            view.setTextColor(Color.parseColor("#70fc3a"))
            gameState[gameStatePointer] = 1
        }

        rounds++

        if (checkWinner()) {
            if (playerOneActive) {
                playerOneScoreCount++
                updatePlayerScore()
                playerStatus.text = "Player-1 has won"
            } else {
                playerTwoScoreCount++
                updatePlayerScore()
                playerStatus.text = "Player-2 has won"
            }
        } else if (rounds == 9) {
            playerStatus.text = "No Winner"
        } else {
            playerOneActive = !playerOneActive
        }

        reset.setOnClickListener {
            playAgain()
            playerOneScoreCount = 0
            playerTwoScoreCount = 0
            updatePlayerScore()
        }

        playagain.setOnClickListener {
            playAgain()
        }
    }

    private fun checkWinner(): Boolean {
        var winnerResults = false
        for (winningPosition in winningPositions) {
            if (gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                gameState[winningPosition[0]] != 2
            ) {
                winnerResults = true
            }
        }
        return winnerResults
    }

    private fun playAgain() {
        rounds = 0
        playerOneActive = true
        for (i in buttons.indices) {
            gameState[i] = 2
            buttons[i]?.text = ""
        }
        playerStatus.text = "Status"
    }

    private fun updatePlayerScore() {
        playerOneScore.text = playerOneScoreCount.toString()
        playerTwoScore.text = playerTwoScoreCount.toString()
    }
}
