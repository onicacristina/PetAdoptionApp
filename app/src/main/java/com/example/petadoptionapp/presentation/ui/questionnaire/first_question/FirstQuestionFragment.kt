package com.example.petadoptionapp.presentation.ui.questionnaire.first_question

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import androidx.fragment.app.viewModels
import com.example.petadoptionapp.R
import com.example.petadoptionapp.databinding.FragmentFirstQuestionBinding
import com.example.petadoptionapp.presentation.base.BaseViewBindingFragment
import com.example.petadoptionapp.presentation.utils.Constants
import com.example.petadoptionapp.presentation.utils.TimeUtils
import com.example.petadoptionapp.presentation.utils.extensions.setOnDebounceClickListener
import com.example.petadoptionapp.presentation.utils.extensions.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class FirstQuestionFragment :
    BaseViewBindingFragment<FragmentFirstQuestionBinding>(R.layout.fragment_first_question), DatePickerDialog.OnDateSetListener {
    override val viewBinding: FragmentFirstQuestionBinding by viewBinding(
        FragmentFirstQuestionBinding::bind
    )
    override val viewModel: FirstQuestionViewModel by viewModels()
    private val calendar = Calendar.getInstance()
    private val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.US)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initListeners()
        initObservers()
    }

    private fun initViews() {
        viewBinding.toolbar.tvTitle.text = getString(R.string.questionnaire)
        viewBinding.question.tvTitle.text = getString(R.string.question_of, Constants.FIRST_QUESTION, Constants.NUMBER_OF_QUESTIONS)
    }

    private fun initListeners() {
        viewBinding.toolbar.ivBack.setOnDebounceClickListener {
            navController.popBackStack()
        }
        viewBinding.btnContinue.setOnDebounceClickListener {
//            navController.navigate()
        }
        viewBinding.btnCalendar.setOnDebounceClickListener {
            openDatePickerDialog()
        }
    }

    private fun openDatePickerDialog() {
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            this,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
//        datePickerDialog.datePicker.minDate = calendar.timeInMillis
        datePickerDialog.show()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        calendar.set(year, month, dayOfMonth)
        displayFormattedDate(calendar.timeInMillis)
    }

    private fun displayFormattedDate(timestamp: Long) {
//        viewBinding.etDateOfBirth.setText(TimeUtils)
        viewBinding.etDateOfBirth.setText(formatter.format(timestamp))
    }

        private fun initObservers() {

    }

}