package com.github.itsskywastaken.studentsupport.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.github.itsskywastaken.studentsupport.R

class LoginActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        val parentLayout = findViewById<ConstraintLayout>(R.id.MAIN)
        val continueButton = parentLayout.findViewById<Button>(R.id.CONTINUE)
        val nameField = parentLayout.findViewById<EditText>(R.id.NAME_FIELD)

        continueButton.setOnClickListener { _ ->
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("name", nameField.getText().toString())
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.MAIN)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}