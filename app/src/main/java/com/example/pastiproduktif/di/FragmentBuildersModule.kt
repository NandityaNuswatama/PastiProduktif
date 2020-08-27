package com.example.pastiproduktif.di

import com.example.pastiproduktif.ui.assessment.AssessmentFragment
import com.example.pastiproduktif.ui.assessment.EditAssessFragment
import com.example.pastiproduktif.ui.assessment.NewAssessActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


// declare all the fragments here , dependency of fragments are provided by this module

@Module
abstract class FragmentBuildersModule {

    // Method #1
    @ContributesAndroidInjector
    abstract fun contributeListFragment(): AssessmentFragment

    @ContributesAndroidInjector
    abstract fun contributeEditAssessFragment(): EditAssessFragment
}