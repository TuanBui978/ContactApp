package com.example.appcontact.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Contact::class], version = 1)
abstract class ContactDataBase: RoomDatabase() {
    abstract fun contactDao() : ContactDao
}