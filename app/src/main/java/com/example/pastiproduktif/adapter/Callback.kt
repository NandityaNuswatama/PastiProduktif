package com.example.pastiproduktif.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.pastiproduktif.entity.Assessment

class Callback(
    private val oldAssessment: List<Assessment>,
    private val newAssessment: List<Assessment>
) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldAssessment[oldItemPosition].id == newAssessment[newItemPosition].id
    }

    override fun getOldListSize(): Int {
        return oldAssessment.size
    }

    override fun getNewListSize(): Int {
        return newAssessment.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldAssessment[oldItemPosition].id == newAssessment[newItemPosition].id
                && oldAssessment[oldItemPosition].title == newAssessment[newItemPosition].title
    }
}