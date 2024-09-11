package com.example.appcontact.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Contact( @PrimaryKey(autoGenerate = true) val uid: Int? = null , @ColumnInfo("name") val name: String, @ColumnInfo("phone_number") val phoneNumber: String, @ColumnInfo("image_source") val image: String? = null)
