package com.example.appmobilius

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appmobilius.model.Player

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Ajuste para barras de sistema (Edge-to-Edge)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 1. Captura dos componentes via findViewById (Padrão do Professor)
        val etName = findViewById<EditText>(R.id.etName)
        val btnStart = findViewById<Button>(R.id.btnStart)

        // 2. Evento de clique no botão
        btnStart.setOnClickListener {
            val name = etName.text.toString()

            // Validação simples de campo vazio
            if (name.isEmpty()) {
                Toast.makeText(this, "Por favor, digite seu nome!", Toast.LENGTH_SHORT).show()
            } else {
                // Criamos o objeto do jogador
                val player = Player(name)

                // Navegação entre telas com Intent
                val intent = Intent(this, QuizActivity::class.java)
                intent.putExtra("PLAYER_DATA", player) // Passando o objeto serializado
                startActivity(intent)
            }
        }
    }
}
