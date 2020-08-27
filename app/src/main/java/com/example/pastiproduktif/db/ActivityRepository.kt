package com.example.pastiproduktif.db

import androidx.lifecycle.LiveData
import com.example.pastiproduktif.entity.Assessment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ActivityRepository(private val assessmentDao: AssessmentDao) {

    fun insert(assessment: Assessment){
        CoroutineScope(Dispatchers.IO).launch {
            assessmentDao.insert(assessment)
        }
    }

    fun delete(assessment: Assessment) {
        CoroutineScope(Dispatchers.IO).launch {
            assessmentDao.delete(assessment)
        }
    }

    fun deleteById(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            assessmentDao.deleteById(id)
        }
    }

    fun update(assessment: Assessment) {
        CoroutineScope(Dispatchers.IO).launch {
            assessmentDao.update(assessment)
        }
    }

    fun getAllAssessment(): LiveData<List<Assessment>>{
        return assessmentDao.getAll()
    }
}