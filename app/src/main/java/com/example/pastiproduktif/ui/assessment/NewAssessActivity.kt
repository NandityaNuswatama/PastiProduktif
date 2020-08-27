package com.example.pastiproduktif.ui.assessment

import android.content.res.TypedArray
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.pastiproduktif.R
import com.example.pastiproduktif.entity.Assessment
import com.example.pastiproduktif.util.ViewModelProviderFactory
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_new_assess.btn_save
import kotlinx.android.synthetic.main.activity_new_assess.edt_assessment
import kotlinx.android.synthetic.main.activity_new_assess.edt_time
import kotlinx.android.synthetic.main.activity_new_assess.edt_title
import kotlinx.android.synthetic.main.activity_new_assess.rg_rating_edit
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class NewAssessActivity : DaggerAppCompatActivity() {

    lateinit var assessmentViewModel: AssessmentViewModel

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    private lateinit var imgRating: TypedArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_assess)

        assessmentViewModel = ViewModelProvider(this, viewModelProviderFactory).get(AssessmentViewModel::class.java)

        btn_save.setOnClickListener {
            if (TextUtils.isEmpty(edt_title.text)){
                Toast.makeText(this, "Fill the tittle", Toast.LENGTH_SHORT).show()
            }else{
                saveToDatabase()
            }
            finish()
        }

    }

    private fun saveToDatabase(){
        val checkedId = rg_rating_edit.checkedRadioButtonId
        var index = 2
        when(checkedId){
            R.id.rb_1 -> index = 0
            R.id.rb_2 -> index = 1
            R.id.rb_3 -> index = 2
            R.id.rb_4 -> index = 3
            R.id.rb_5 -> index = 4
        }
        imgRating = resources.obtainTypedArray(R.array.rating)
        val assessment = Assessment(
            title = edt_title.text.toString(),
            description = edt_assessment.text.toString(),
            date = getCurrentDate(),
            time = edt_time.text.toString(),
            rating = imgRating.getResourceId(index, 2)
        )
            assessmentViewModel.insert(assessment)
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
        val date = Date()

        return dateFormat.format(date)
    }
}