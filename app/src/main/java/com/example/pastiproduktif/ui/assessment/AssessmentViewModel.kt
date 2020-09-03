package com.example.pastiproduktif.ui.assessment


import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pastiproduktif.db.ActivityRepository
import com.example.pastiproduktif.db.AssessmentDatabase
import com.example.pastiproduktif.entity.Assessment
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class AssessmentViewModel @Inject constructor(private var assessmentRepository: ActivityRepository, application: Application): ViewModel() {

    init {
        val assessmentDao = AssessmentDatabase.getDatabase(application).dao()
        assessmentRepository = ActivityRepository(assessmentDao)
        assessmentRepository.getAllAssessment()
    }

    fun insert(assessment: Assessment) {
        return assessmentRepository.insert(assessment)
    }

    fun delete(assessment: Assessment) {
        assessmentRepository.delete(assessment)
    }

    fun deleteById(id: Int){
        assessmentRepository.deleteById(id)
    }

    fun getAllAssessment(): LiveData<List<Assessment>> {
        return assessmentRepository.getAllAssessment()
    }

    fun setDate(): String {
        val dateFormat = SimpleDateFormat("EEE, dd-MMM-yyyy", Locale.getDefault())
        val date = Date()

        return dateFormat.format(date)
    }
}
