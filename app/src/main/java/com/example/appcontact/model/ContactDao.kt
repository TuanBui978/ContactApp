package com.example.appcontact.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ContactDao {
    @Query("SELECT * FROM contact")
    suspend fun getListContact():List<Contact>

    @Query("SELECT * FROM contact WHERE uid in (:uid)")
    fun getContactById(uid: IntArray):List<Contact>

    @Query("SELECT * FROM contact WHERE name LIKE '%' || :name || '%'")
    suspend fun findByName(name: String): List<Contact>

    @Insert
    suspend fun insertAll(vararg users: Contact)

    @Delete
    fun delete(user: Contact)
}