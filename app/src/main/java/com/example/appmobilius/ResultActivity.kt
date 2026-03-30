package com.example.appmobilius

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.appmobilius.model.Player

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        // Recuperando o objeto Player (mesma lógica de checagem de API)
        val player = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("PLAYER_DATA", Player::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra("PLAYER_DATA")
        }

        val tvPlayerName = findViewById<TextView>(R.id.tvPlayerName)
        val tvFinalScore = findViewById<TextView>(R.id.tvFinalScore)
        val btnRestart = findViewById<Button>(R.id.btnRestart)

        player?.let {
            tvPlayerName.text = "Jogador: ${it.name}"
            tvFinalScore.text = "Pontuação: ${it.score}"
        }

        btnRestart.setOnClickListener {
            // Volta para a primeira tela e limpa a pilha de atividades
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}
