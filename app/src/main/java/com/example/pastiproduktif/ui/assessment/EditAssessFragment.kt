package com.example.pastiproduktif.ui.assessment

import android.content.res.TypedArray
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.pastiproduktif.MainActivity
import com.example.pastiproduktif.R
import com.example.pastiproduktif.entity.Assessment
import com.example.pastiproduktif.util.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_new_assess.btn_save
import kotlinx.android.synthetic.main.activity_new_assess.edt_assessment
import kotlinx.android.synthetic.main.activity_new_assess.edt_title
import kotlinx.android.synthetic.main.fragment_edit_assess.*
import kotlinx.android.synthetic.main.fragment_edit_assess.edt_time
import timber.log.Timber
import javax.inject.Inject

class EditAssessFragment : DaggerFragment() {
    lateinit var assessmentViewModel: AssessmentViewModel

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    private lateinit var imgRating: TypedArray

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_assess, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).nav_view.visibility = View.GONE

        setupViewModel()
        prepareEditing()

        btn_save.setOnClickListener {
            val builder = AlertDialog.Builder(requireActivity())
            builder.setTitle("Update Activity")
            builder.setMessage("Are you sure to update this?")
            builder.apply {
                setPositiveButton("Update") { dialog, id ->
                    updateDatabase()
                    findNavController().navigate(R.id.action_editAssessFragment_to_navigation_notes)
                    Toast.makeText(activity, "Activity Updated", Toast.LENGTH_SHORT).show()
                }
                setNegativeButton("Cancel"){dialog, id -> dialog.cancel() }
            }
            builder.create().show()
        }

        btn_cancel.setOnClickListener {
            val builder = AlertDialog.Builder(requireActivity())
            builder.setTitle("Discard Changes")
            builder.setMessage("Are you sure to discard the change?")
            builder.apply {
                setPositiveButton("Discard") { dialog, id ->
                    findNavController().navigate(R.id.action_editAssessFragment_to_navigation_notes)
                    Toast.makeText(activity, "Update Discarded", Toast.LENGTH_SHORT).show()
                }
                setNegativeButton("Cancel"){dialog, id -> dialog.cancel() }
            }
            builder.create().show()
        }

        btn_delete.setOnClickListener {
            val builder = AlertDialog.Builder(requireActivity())
            builder.setTitle("Delete Activity")
            builder.setMessage("Are you sure to delete this?")
            builder.apply {
                setPositiveButton("Delete") { dialog, id ->
                    deleteFromDatabase()
                    findNavController().navigate(R.id.action_editAssessFragment_to_navigation_notes)
                    Toast.makeText(activity, "Activity Deleted", Toast.LENGTH_SHORT).show()
                }
                setNegativeButton("Cancel"){dialog, id -> dialog.cancel() }
            }
            builder.create().show()
        }
    }

    private fun prepareEditing() {
        imgRating = resources.obtainTypedArray(R.array.rating)
        // Getting the note from the bundle
        //Save args plugin is used as i believe bundle is not good for sending large data
        arguments?.let{
            val args = EditAssessFragmentArgs.fromBundle(it)
            val assess = args.assessment
            edt_title.setText(assess?.title.toString())
            edt_assessment.setText(assess?.description.toString())
            edt_time.setText(assess?.time.toString())
            if (assess != null) {
                imgRating.getIndex(assess.rating)
            }

            if (assess != null) {
                Timber.i(assess.rating.toString())
            }
        }
    }

    private fun setupViewModel() {
        assessmentViewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(AssessmentViewModel::class.java)
    }

    private fun updateDatabase(){
        imgRating = resources.obtainTypedArray(R.array.rating)
        val checkedId = rg_rating_edit.checkedRadioButtonId
        var index = 2
        when(checkedId){
            R.id.rb_1 -> index = 0
            R.id.rb_2 -> index = 1
            R.id.rb_3 -> index = 2
            R.id.rb_4 -> index = 3
            R.id.rb_5 -> index = 4
        }
        arguments?.let {
            val args = EditAssessFragmentArgs.fromBundle(it)
            val assess = args.assessment
            assessmentViewModel.deleteById(id = assess!!.id)
            Timber.i(assess.id.toString())
            val assessment = Assessment(
                id = assess.id,
                title = edt_title.text.toString(),
                description = edt_assessment.text.toString(),
                date = assess.date,
                time = edt_time.text.toString(),
                rating = imgRating.getResourceId(index, 2)

            )
            assessmentViewModel.insert(assessment)
            Timber.i(assessment.id.toString())
        }
    }

    private fun deleteFromDatabase(){
        arguments?.let{
            val args = EditAssessFragmentArgs.fromBundle(it)
            val assess = args.assessment
            assessmentViewModel.deleteById(id = assess!!.id)
            Timber.i(assess.id.toString())
        }
    }
}