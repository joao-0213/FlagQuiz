package com.example.appmobilius

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.appmobilius.model.Player

class QuizActivity : AppCompatActivity() {

    // Lista de todas as bandeiras disponíveis (Mínimo 15 como o professor pediu)
    // ATENÇÃO: Você precisa adicionar as imagens na pasta res/drawable
    private val flags = listOf(
        R.drawable.flag_brazil, R.drawable.flag_france, R.drawable.flag_japan,
        R.drawable.flag_germany, R.drawable.flag_italy, R.drawable.flag_canada,
        R.drawable.flag_usa, R.drawable.flag_argentina, R.drawable.flag_spain,
        R.drawable.flag_portugal, R.drawable.flag_mexico, R.drawable.flag_chile,
        R.drawable.flag_china, R.drawable.flag_australia, R.drawable.flag_india
    )

    // Lista de nomes correspondentes (para validar a resposta)
    private val countryNames = mapOf(
        R.drawable.flag_brazil to "Brasil",
        R.drawable.flag_france to "França",
        R.drawable.flag_japan to "Japão",
        R.drawable.flag_germany to "Alemanha",
        R.drawable.flag_italy to "Itália",
        R.drawable.flag_canada to "Canadá",
        R.drawable.flag_usa to "EUA",
        R.drawable.flag_argentina to "Argentina",
        R.drawable.flag_spain to "Espanha",
        R.drawable.flag_portugal to "Portugal",
        R.drawable.flag_mexico to "México",
        R.drawable.flag_chile to "Chile",
        R.drawable.flag_china to "China",
        R.drawable.flag_australia to "Austrália",
        R.drawable.flag_india to "Índia"
    )

    private lateinit var selectedFlags: List<Int>
    private var currentIndex = 0
    private lateinit var currentPlayer: Player

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        // Recuperando o objeto Player (Com checagem de versão da API como o professor gosta)
        currentPlayer = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("PLAYER_DATA", Player::class.java)!!
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra("PLAYER_DATA")!!
        }

        // Embaralha as bandeiras e pega as 5 primeiras para garantir que não repita
        // Diferença: random() pega UM aleatório. shuffled().take(5) embaralha tudo e pega os 5 do topo.
        selectedFlags = flags.shuffled().take(5)

        loadQuestion()

        val btnSubmit = findViewById<Button>(R.id.btnSubmit)
        val etAnswer = findViewById<EditText>(R.id.etAnswer)
        val tvFeedback = findViewById<TextView>(R.id.tvFeedback)

        btnSubmit.setOnClickListener {
            if (btnSubmit.text == "Próxima") {
                currentIndex++
                if (currentIndex < 5) {
                    loadQuestion()
                    tvFeedback.visibility = View.GONE
                    etAnswer.text.clear()
                    btnSubmit.text = "Responder"
                } else {
                    // Fim do quiz, vai para a tela de resultado
                    val intent = Intent(this, ResultActivity::class.java)
                    intent.putExtra("PLAYER_DATA", currentPlayer)
                    startActivity(intent)
                    finish()
                }
            } else {
                checkAnswer()
            }
        }
    }

    private fun loadQuestion() {
        val imgFlag = findViewById<ImageView>(R.id.imgFlag)
        val tvCounter = findViewById<TextView>(R.id.tvCounter)
        
        // Lógica Obrigatória:
        val currentFlag = selectedFlags[currentIndex]
        imgFlag.setImageResource(currentFlag)
        
        tvCounter.text = "Pergunta: ${currentIndex + 1} de 5"
    }

    private fun checkAnswer() {
        val etAnswer = findViewById<EditText>(R.id.etAnswer)
        val tvFeedback = findViewById<TextView>(R.id.tvFeedback)
        val btnSubmit = findViewById<Button>(R.id.btnSubmit)

        val userAnswer = etAnswer.text.toString().trim()
        val correctAnswer = countryNames[selectedFlags[currentIndex]] ?: ""

        if (userAnswer.equals(correctAnswer, ignoreCase = true)) {
            tvFeedback.text = "Correto!"
            tvFeedback.setTextColor(Color.GREEN)
            currentPlayer.score += 20
        } else {
            tvFeedback.text = "Incorreto! Era $correctAnswer"
            tvFeedback.setTextColor(Color.RED)
        }

        tvFeedback.visibility = View.VISIBLE
        btnSubmit.text = "Próxima"
    }
}
