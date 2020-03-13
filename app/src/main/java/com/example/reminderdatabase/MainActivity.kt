package com.example.reminderdatabase

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

const val ADD_REMINDER_REQUEST_CODE = 100

class MainActivity : AppCompatActivity() {

  private lateinit var reminders: ArrayList<Reminder>
  private lateinit var reminderAdapter: ReminderAdapter


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setSupportActionBar(toolbar)


    reminders = arrayListOf(
      Reminder("iets")
    )

    reminderAdapter = ReminderAdapter(reminders)

    initViews()
    fab.setOnClickListener {

      startAddActivity()

    }
  }

  private fun startAddActivity() {
    val intent = Intent(this, AddReminderActivity::class.java)
    startActivityForResult(intent, ADD_REMINDER_REQUEST_CODE)
  }

  private fun initViews() {
    // Initialize the recycler view with a linear layout manager, adapter
    rvReminders.layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
    rvReminders.adapter = reminderAdapter
    rvReminders.addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))
    createItemTouchHelper().attachToRecyclerView(rvReminders)
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    // Inflate the menu; this adds items to the action bar if it is present.
    menuInflater.inflate(R.menu.menu_main, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    return when (item.itemId) {
      R.id.action_settings -> true
      else -> super.onOptionsItemSelected(item)
    }
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)

    if (resultCode == Activity.RESULT_OK) {
      when (requestCode) {
        ADD_REMINDER_REQUEST_CODE -> {
          data?.let {saveData ->
            val reminder = saveData.getParcelableExtra<Reminder>(EXTRA_REMINDER)
            reminder?.let {saveReminder ->
              reminders.add(saveReminder)
              reminderAdapter.notifyDataSetChanged()
            } ?: run {
              Log.e("saveReminder", "Something went wrong with adding a Reminder")
            }
          } ?: run {
            Log.e("saveData", "Something went wrong with saving the data from saveReminder");
          }

        }
      }
    }
  }

  /**
   * Create a touch helper to recognize when a user swipes an item from a recycler view.
   * An ItemTouchHelper enables touch behavior (like swipe and move) on each ViewHolder,
   * and uses callbacks to signal when a user is performing these actions.
   */
  private fun createItemTouchHelper(): ItemTouchHelper {

    // Callback which is used to create the ItemTouch helper. Only enables left swipe.
    // Use ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) to also enable right swipe.
    val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

      // Enables or Disables the ability to move items up and down.
      override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
      ): Boolean {
        return false
      }

      // Callback triggered when a user swiped an item.
      override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        reminders.removeAt(position)
        reminderAdapter.notifyDataSetChanged()
      }
    }
    return ItemTouchHelper(callback)
  }

}
