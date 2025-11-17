package com.github.itsskywastaken.studentsupport.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.github.itsskywastaken.studentsupport.R
import com.github.itsskywastaken.studentsupport.data.Assignment
import com.github.itsskywastaken.studentsupport.data.Course
import com.github.itsskywastaken.studentsupport.data.Event

class MainActivity : AppCompatActivity() {

    lateinit var classes: ArrayList<Course>
    lateinit var events: ArrayList<Event>
    lateinit var assignments: ArrayList<Assignment>

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        classes = intent.getParcelableArrayListExtra("classes") ?: ArrayList()
        events = intent.getParcelableArrayListExtra("events") ?: ArrayList()
        assignments = intent.getParcelableArrayListExtra("assignments") ?: ArrayList()

        val name = intent.getStringExtra("name")

        val parentLayout = findViewById<ConstraintLayout>(R.id.MAIN)
        val header = parentLayout.findViewById<TextView>(R.id.HEADER)
        header.text = "Hello, ${if((name ?: "") == "") "User" else name}!"

        val navBar = findViewById<LinearLayout>(R.id.NAV_BAR)

        val calendar = findViewById<Button>(R.id.CALENDAR)
        calendar.setOnClickListener { _ ->
            val intent = Intent(this, CalendarActivity::class.java)
            intent.putParcelableArrayListExtra("classes", classes)
            intent.putParcelableArrayListExtra("events", events)
            intent.putParcelableArrayListExtra("assignments", assignments)
            startActivityForResult(intent, 0)
        }

        val support = navBar.findViewById<Button>(R.id.SUPPORT_CHAT)
        support.setOnClickListener { _ ->
            val intent = Intent(this, StudentSupportActivity::class.java)
            startActivity(intent)
        }

        val map = navBar.findViewById<Button>(R.id.MAP)
        map.setOnClickListener { _ ->
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }

        val notifications = navBar.findViewById<Button>(R.id.NOTIFICATIONS)
        notifications.setOnClickListener { _ ->
            val intent = Intent(this, NotificationActivity::class.java)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.MAIN)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode != 0) return
        if(resultCode != RESULT_OK) return

        classes = data?.getParcelableArrayListExtra("classes") ?: ArrayList()
        events = data?.getParcelableArrayListExtra("events") ?: ArrayList()
        assignments = data?.getParcelableArrayListExtra("assignments") ?: ArrayList()
    }
}