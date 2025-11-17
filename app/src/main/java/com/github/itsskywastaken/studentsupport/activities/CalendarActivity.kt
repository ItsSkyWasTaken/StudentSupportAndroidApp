package com.github.itsskywastaken.studentsupport.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.github.itsskywastaken.studentsupport.R
import com.github.itsskywastaken.studentsupport.data.Assignment
import com.github.itsskywastaken.studentsupport.data.Course
import com.github.itsskywastaken.studentsupport.data.Event

class CalendarActivity : AppCompatActivity() {

    lateinit var classes: ArrayList<Course>
    lateinit var events: ArrayList<Event>
    lateinit var assignments: ArrayList<Assignment>

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.calendar)

        classes = intent.getParcelableArrayListExtra("classes") ?: ArrayList()
        events = intent.getParcelableArrayListExtra("events") ?: ArrayList()
        assignments = intent.getParcelableArrayListExtra("assignments") ?: ArrayList()

        updateClasses()
        updateEvents()
        updateAssignments()

        findViewById<Button>(R.id.BACK).setOnClickListener { _ ->
            val intent = Intent()
            intent.putParcelableArrayListExtra("classes", classes)
            intent.putParcelableArrayListExtra("events", events)
            intent.putParcelableArrayListExtra("assignments", assignments)
            setResult(RESULT_OK, intent)
            finish()
        }

        val newClass = findViewById<Button>(R.id.ADD_CLASS)
        val newEvent = findViewById<Button>(R.id.ADD_EVENT)
        val newAssignment = findViewById<Button>(R.id.ADD_ASSIGNMENT)

        newClass.setOnClickListener { _ ->
            val intent = Intent(this, AddCourse::class.java)
            startActivityForResult(intent, 10)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.MAIN)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_CANCELED) return

        if(requestCode == 10) {
            val course = data?.getParcelableExtra<Course>("course")
            course?.let {
                classes.add(course)
                updateClasses()
            }
        }
    }

    fun updateClasses() {
        print("updating")
        val classList = findViewById<LinearLayout>(R.id.CLASSES)
        classList.removeAllViews()
        if(classes.isEmpty()) {
            val empty = TextView(this)
            empty.text = "Nothing to see here..."
            empty.textSize = 18f
            empty.setTypeface(empty.typeface, Typeface.ITALIC)
            empty.gravity = Gravity.CENTER
            classList.addView(empty)
        } else {
            classes.forEach { course ->
                val item = LinearLayout(this)
                item.orientation = LinearLayout.VERTICAL

                val courseName = TextView(this)
                courseName.text = course.name
                courseName.textSize = 16f
                courseName.setTypeface(courseName.typeface, Typeface.BOLD)
                item.addView(courseName)

                val dates = TextView(this)
                dates.text = course.meetings
                dates.textSize = 12f
                dates.setTypeface(dates.typeface, Typeface.ITALIC)
                item.addView(dates)

                classList.addView(item)
            }
        }
    }

    fun updateEvents() {
        val eventList = findViewById<LinearLayout>(R.id.EVENTS)
        eventList.removeAllViews()
        if(events.isEmpty()) {
            val empty = TextView(this)
            empty.text = "Nothing to see here..."
            empty.textSize = 18f
            empty.setTypeface(empty.typeface, Typeface.ITALIC)
            empty.gravity = Gravity.CENTER
            eventList.addView(empty)
        } else {
            events.forEach { course ->

            }
        }
    }

    fun updateAssignments() {
        val assignmentList = findViewById<LinearLayout>(R.id.ASSIGNMENTS)
        assignmentList.removeAllViews()
        if(assignments.isEmpty()) {
            val empty = TextView(this)
            empty.text = "Nothing to see here..."
            empty.textSize = 18f
            empty.setTypeface(empty.typeface, Typeface.ITALIC)
            empty.gravity = Gravity.CENTER
            assignmentList.addView(empty)
        } else {
            assignments.forEach { course ->

            }
        }
    }
}