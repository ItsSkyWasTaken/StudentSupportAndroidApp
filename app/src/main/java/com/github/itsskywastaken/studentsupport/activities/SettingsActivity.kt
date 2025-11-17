package com.github.itsskywastaken.studentsupport.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.github.itsskywastaken.studentsupport.R

class SettingsActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.coming_soon)

        val header = findViewById<TextView>(R.id.HEADER)
        header.text = "Settings"

        val addMeeting = findViewById<Button>(R.id.ADD_MEETING)
        val meetingList = findViewById<LinearLayout>(R.id.MEETING_LIST)
        addMeeting.setOnClickListener { _ ->

        }

        val back = findViewById<Button>(R.id.BACK)

        back.setOnClickListener { _ ->
            val intent = Intent()
            setResult(RESULT_OK, intent)
            finish()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.MAIN)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}