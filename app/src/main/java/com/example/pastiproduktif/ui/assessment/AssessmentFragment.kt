package com.example.pastiproduktif.ui.assessment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pastiproduktif.MainActivity
import com.example.pastiproduktif.R
import com.example.pastiproduktif.adapter.ActivityAssessmentAdapter
import com.example.pastiproduktif.entity.Assessment
import com.example.pastiproduktif.util.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_assessment.*
import kotlinx.android.synthetic.main.fragment_assessment.view.*
import javax.inject.Inject


class AssessmentFragment : DaggerFragment(), ActivityAssessmentAdapter.Interaction {
    private lateinit var assessmentAdapter: ActivityAssessmentAdapter
    private lateinit var assessmentViewModel: AssessmentViewModel

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    lateinit var allAssessment: List<Assessment>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        allAssessment = mutableListOf()
        return inflater.inflate(R.layout.fragment_assessment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).nav_view.visibility = View.VISIBLE

        fab.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_navigation_notes_to_newAssessActivity)
        }

        setupViewModel()
        initRecyclerView()
        observerLiveData()
    }

    private fun setupViewModel() {
        assessmentViewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(AssessmentViewModel::class.java)
    }

    private fun initRecyclerView() {
        rv_assessment.apply {
            assessmentAdapter = ActivityAssessmentAdapter(
                allAssessment, this@AssessmentFragment
            )
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@AssessmentFragment.context)
            adapter = assessmentAdapter
            adapter
            val swipe = ItemTouchHelper(initSwipeToDelete())
            swipe.attachToRecyclerView(rv_assessment)
        }
    }

    private fun initSwipeToDelete(): ItemTouchHelper.SimpleCallback {
        //Swipe recycler view items on RIGHT
        return object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val builder = activity?.let { AlertDialog.Builder(it) }
                builder?.setTitle("Delete Activity")
                builder?.setMessage("Are you sure to delete this?")
                builder?.apply {
                    setPositiveButton("Delete") { dialog, id ->
                        assessmentViewModel.delete(allAssessment.get(position))
                        Toast.makeText(activity, "Assessment Deleted", Toast.LENGTH_SHORT).show()
                    }
                    setNegativeButton("Cancel"){dialog, id ->
                        dialog.cancel()
                    }
                }
                builder?.create()?.show()
                val assessment = allAssessment[position]
            }
        }
    }

    private fun observerLiveData() {
        assessmentViewModel.getAllAssessment()
            .observe(viewLifecycleOwner, Observer { assessmentList ->
                assessmentList?.let {
                    allAssessment = it
                    assessmentAdapter.swap(it)
                }
            })
    }

    override fun onItemSelected(position: Int, item: Assessment) {
        val navDirection = AssessmentFragmentDirections.actionNavigationNotesToEditAssessFragment(item)
        findNavController().navigate(navDirection)
    }
}