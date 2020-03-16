package com.example.reminderdatabase.repositories

import android.content.Context
import com.example.reminderdatabase.Reminder
import com.example.reminderdatabase.ReminderRoomDatabase
import com.example.reminderdatabase.dao.ReminderDao

class ReminderRepository(context: Context) {
  private var reminderDao: ReminderDao?

  init {
    val reminderRoomDatabase = ReminderRoomDatabase.getReminderRoomDatabase(context)
    reminderDao = reminderRoomDatabase?.reminderDao()
  }

  suspend fun getAllReminders(): List<Reminder> {
    return reminderDao?.getAllReminders() ?: emptyList()
  }

  suspend fun insertReminder(reminder: Reminder) {
    reminderDao?.insertReminder(reminder)
  }

  suspend fun deleteReminder(reminder: Reminder) {
    reminderDao?.deleteReminder(reminder)
  }

  suspend fun updateReminder(reminder: Reminder) {
    reminderDao?.updateReminder(reminder)
  }
}
