package com.example.pastiproduktif.di

import androidx.lifecycle.ViewModel
import com.example.pastiproduktif.ui.assessment.AssessmentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class ViewModelModule {

    // Method #1
    @Binds
    @IntoMap
    @ViewModelKey(AssessmentViewModel::class)
    abstract fun bindMainViewModel(notesViewModel: AssessmentViewModel): ViewModel
}
