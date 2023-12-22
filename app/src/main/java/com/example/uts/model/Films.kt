package com.example.uts.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Duration
import java.time.ZoneId
@Entity(tableName = "films")
data class Films (
    @PrimaryKey
    var id: String,
    val title:String,
    val genre :String,
    val type :String,
    val desc:String,
    val image:String,
    val year:Int,
    val age:Int,
    val duration: Int,
    val rating:Double
) {
    constructor() : this("","", "", "", "", "", 0, 0, 0, 0.0)
}
