package com.example.pastiproduktif.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pastiproduktif.R
import com.example.pastiproduktif.entity.Assessment
import kotlinx.android.synthetic.main.activity_new_assess.view.*
import kotlinx.android.synthetic.main.item_list.view.*

class ActivityAssessmentAdapter(
    assessmentList: List<Assessment>,
    private val interaction: Interaction? = null
): RecyclerView.Adapter<ActivityAssessmentAdapter.AssessmentViewHolder>() {

    private var assessment = mutableListOf<Assessment>()

    init {
        assessment.addAll(assessmentList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ActivityAssessmentAdapter.AssessmentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return AssessmentViewHolder(view, interaction)
    }

    override fun getItemCount(): Int {
        return assessment.size
    }

    override fun onBindViewHolder(
        holder: ActivityAssessmentAdapter.AssessmentViewHolder,
        position: Int
    ) {
        holder.bind(item = assessment[position])
    }

    inner class AssessmentViewHolder(itemView: View, private val interaction: Interaction?): RecyclerView.ViewHolder(itemView) {
        fun bind(item: Assessment){
            with(itemView){
                tv_title.text = item.title
                tv_activity.text = item.description
                tv_date.text = item.date
                tv_time.text = item.time
                Glide.with(context)
                    .load(item.rating)
                    .into(img_rating)

                setOnClickListener {
                    interaction?.onItemSelected(adapterPosition, item)
                }
            }
        }
    }

    fun swap(assessmentList: List<Assessment>){
        val callback = Callback(this.assessment, assessmentList)
        val result = DiffUtil.calculateDiff(callback)

        this.assessment.clear()
        this.assessment.addAll(assessmentList)
        notifyDataSetChanged()
        result.dispatchUpdatesTo(this)
    }

    interface Interaction{
        fun onItemSelected(position: Int, item: Assessment)
    }
}