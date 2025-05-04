package com.example.tictactoe

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var currentPlayer = "X"
    private lateinit var buttons: Array<Button>
    private lateinit var messageText: TextView
    private lateinit var playAgainButton: Button
    private var gameActive = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        messageText = findViewById(R.id.messageText)
        playAgainButton = findViewById(R.id.playAgainButton)

        buttons = Array(9) { i ->
            val id = resources.getIdentifier("button$i", "id", packageName)
            findViewById<Button>(id).apply {
                setOnClickListener { onButtonClick(this, i) }
            }
        }

        playAgainButton.setOnClickListener {
            resetGame()
        }
    }

    private fun onButtonClick(button: Button, index: Int) {
        if (button.text.isNotEmpty() || !gameActive) return

        button.text = currentPlayer

        // 🎨 שינוי צבע וגודל טקסט לפי שחקן
        button.textSize = 48f
        if (currentPlayer == "X") {
            button.setTextColor(resources.getColor(android.R.color.holo_red_dark))
        } else {
            button.setTextColor(resources.getColor(android.R.color.holo_blue_dark))
        }

        if (checkForWin()) {
            gameActive = false
            messageText.text = "$currentPlayer Wins!"
            messageText.visibility = View.VISIBLE
            playAgainButton.visibility = View.VISIBLE
        } else if (buttons.all { it.text.isNotEmpty() }) {
            gameActive = false
            messageText.text = "It's a Draw!"
            messageText.visibility = View.VISIBLE
            playAgainButton.visibility = View.VISIBLE
        } else {
            currentPlayer = if (currentPlayer == "X") "O" else "X"
        }
    }

    private fun checkForWin(): Boolean {
        val winPositions = arrayOf(
            intArrayOf(0, 1, 2), intArrayOf(3, 4, 5), intArrayOf(6, 7, 8), // שורות
            intArrayOf(0, 3, 6), intArrayOf(1, 4, 7), intArrayOf(2, 5, 8), // עמודות
            intArrayOf(0, 4, 8), intArrayOf(2, 4, 6)                      // אלכסונים
        )

        return winPositions.any { (a, b, c) ->
            buttons[a].text == currentPlayer &&
                    buttons[a].text == buttons[b].text &&
                    buttons[a].text == buttons[c].text
        }
    }

    private fun resetGame() {
        for (button in buttons) {
            button.text = ""
            button.setTextColor(resources.getColor(android.R.color.black)) // 🧼 איפוס צבע
        }
        currentPlayer = "X"
        gameActive = true
        messageText.visibility = View.INVISIBLE
        playAgainButton.visibility = View.GONE
    }
}
