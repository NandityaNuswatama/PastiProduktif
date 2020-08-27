package com.example.pastiproduktif.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.pastiproduktif.BaseApplication
import com.example.pastiproduktif.db.AssessmentDatabase
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "assessment_table")
class Assessment(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "title")var title: String? = null,
    @ColumnInfo(name = "description") var description: String? = null,
    @ColumnInfo(name = "date") var date: String? = null,
    @ColumnInfo(name = "time") var time: String? = null,
    @ColumnInfo(name = "rating") var rating: Int = 2
):Parcelable