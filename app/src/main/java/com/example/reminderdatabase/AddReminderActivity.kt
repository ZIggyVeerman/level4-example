package com.example.reminderdatabase

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add_reminder.*

import kotlinx.android.synthetic.main.content_add_reminder.*

const val EXTRA_REMINDER = "EXTRA_REMINDER"

class AddReminderActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_add_reminder)
    setSupportActionBar(toolbar)

    initViews()
  }

  private fun initViews() {
    fab.setOnClickListener { onSaveClick() }
  }

  private fun onSaveClick() {
    if (etAddReminder.text.toString().isNotBlank()) {
      val reminder = Reminder(etAddReminder.text.toString())
      val resultIntent = Intent()
      resultIntent.putExtra(EXTRA_REMINDER, reminder)
      setResult(Activity.RESULT_OK, resultIntent)
      finish()
    } else {
      Toast.makeText(this,"The reminder cannot be empty"
        , Toast.LENGTH_SHORT).show()
    }
  }
}
