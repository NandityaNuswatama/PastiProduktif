package com.example.pastiproduktif.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.pastiproduktif.entity.Assessment

@Dao
interface AssessmentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(assessment: Assessment): Long

    @Query("SELECT * from assessment_table ORDER BY id DESC")
    fun getAll(): LiveData<List<Assessment>>

    @Delete
    fun delete(assessment: Assessment)

    @Update
    fun update(assessment: Assessment)

    @Query("DELETE FROM assessment_table WHERE id = :id")
    fun deleteById(id: Int)
}