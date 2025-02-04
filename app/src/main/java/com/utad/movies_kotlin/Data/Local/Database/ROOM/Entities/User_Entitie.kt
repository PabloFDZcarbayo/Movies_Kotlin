package com.utad.movies_kotlin.Data.Local.Database.ROOM.Entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(tableName = "Users", indices = [Index(value = ["username"], unique = true)])
data class User_Entitie (
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var username: String,
    var password: String,
    var email: String,
){
}