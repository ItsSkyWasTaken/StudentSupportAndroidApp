package com.github.itsskywastaken.studentsupport.activities

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.TypedValue
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.size
import com.github.itsskywastaken.studentsupport.R
import com.github.itsskywastaken.studentsupport.data.Course
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

class AddCourse : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.add_class)

        val dayVals = arrayOf("M", "Tu", "W", "Th", "F", "Sa", "Su")

        val back = findViewById<Button>(R.id.BACK)
        val confirm = findViewById<Button>(R.id.CONFIRM)

        back.setOnClickListener { _ ->
            val intent = Intent()
            setResult(RESULT_CANCELED, intent)
            finish()
        }

        val nameField = findViewById<EditText>(R.id.NAME_FIELD)
        val startDateField = findViewById<EditText>(R.id.START_DATE_FIELD)
        val endDateField = findViewById<EditText>(R.id.END_DATE_FIELD)

        startDateField.setOnClickListener {
            showDatePickerDialog(startDateField)
        }

        endDateField.setOnClickListener {
            showDatePickerDialog(endDateField)
        }

        val addMeeting = findViewById<Button>(R.id.ADD_MEETING)
        val meetingList = findViewById<LinearLayout>(R.id.MEETING_LIST)
        addMeeting.setOnClickListener { _ ->
            val margin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16f, resources.displayMetrics).toInt()
            val id = meetingList.size
            val meeting = LinearLayout(this)
            meeting.orientation = LinearLayout.VERTICAL

            val days = RadioGroup(this)
            days.orientation = RadioGroup.HORIZONTAL
            days.gravity = Gravity.CENTER

            for(value in dayVals) {
                val day = RadioButton(this)
                day.text = value
                day.id = id * 100 + dayVals.indexOf(value)
                days.addView(day)

                val dayParams = day.layoutParams as? RadioGroup.LayoutParams
                dayParams?.weight = 1f
                day.layoutParams = dayParams
            }

            meeting.addView(days)
            val daysParams = days.layoutParams
            daysParams.width = ViewGroup.LayoutParams.MATCH_PARENT
            daysParams.height = ViewGroup.LayoutParams.MATCH_PARENT
            days.layoutParams = daysParams

            val times = LinearLayout(this)
            times.orientation = LinearLayout.HORIZONTAL
            times.gravity = Gravity.CENTER

            val startTime = EditText(this)
            startTime.hint = "Start Time"
            startTime.inputType = InputType.TYPE_DATETIME_VARIATION_TIME
            startTime.id = id * 100 + 11

            val endTime = EditText(this)
            endTime.hint = "End Time"
            endTime.inputType = InputType.TYPE_DATETIME_VARIATION_TIME
            endTime.id = id * 100 + 12

            times.addView(startTime)
            times.addView(endTime)
            meeting.addView(times)

            val startParams = startTime.layoutParams as? LinearLayout.LayoutParams
            startParams?.weight = 1f
            startTime.layoutParams = startParams

            val endParams = endTime.layoutParams as? LinearLayout.LayoutParams
            endParams?.weight = 1f
            endTime.layoutParams = endParams

            meetingList.addView(meeting)

            val meetingLayoutParams = meeting.layoutParams as? ViewGroup.MarginLayoutParams
            meetingLayoutParams?.setMargins(margin, margin, margin, 0)
            meeting.layoutParams = meetingLayoutParams
        }

        confirm.setOnClickListener { _ ->
            val intent = Intent()

            val name = nameField.text.toString()
            val startDate = convertDate(startDateField.text.toString())
            val endDate = convertDate(endDateField.text.toString())

            val meetings = LongArray(meetingList.childCount) { 0 }
            for(index in 0 until meetingList.childCount) {
                val meeting = meetingList.getChildAt(index) as LinearLayout

                val days = meeting.getChildAt(0) as RadioGroup
                val selectedDay = dayVals.indexOf(findViewById<RadioButton>(days.checkedRadioButtonId).text)

                val times = meeting.getChildAt(1) as LinearLayout
                val startTime = times.getChildAt(0) as EditText
                val endTime = times.getChildAt(1) as EditText

                meetings[index] = convertMeeting(selectedDay, startTime.text.toString(), endTime.text.toString())
            }

            val course = Course(name, startDate, endDate, meetings)
            intent.putExtra("course", course)
            setResult(RESULT_OK, intent)
            finish()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.MAIN)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun convertDate(dateString: String): Long {
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")
        val date = LocalDate.parse(dateString, dateFormatter)
        return date.toEpochDay()
    }

    fun convertMeeting(day: Int, startTimeString: String, endTimeString: String): Long {
        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
        val startTime = LocalTime.parse(startTimeString, timeFormatter)
        val endTime = LocalTime.parse(endTimeString, timeFormatter)
        val start = day * 86400 + startTime.hour * 3600 + startTime.minute * 60
        val end = day * 86400 + endTime.hour * 3600 + endTime.minute * 60
        return start.toLong().shl(32).or(end.toLong().and(0xFFFFFFFF))
    }

    private fun showDatePickerDialog(field: EditText) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                calendar.set(selectedYear, selectedMonth, selectedDay)
                val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
                field.setText(dateFormat.format(calendar.time))
            },
            year, month, day
        )
        datePickerDialog.show()
    }
}