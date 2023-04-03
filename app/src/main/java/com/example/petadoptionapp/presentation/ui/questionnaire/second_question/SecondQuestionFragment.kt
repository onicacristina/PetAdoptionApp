package com.example.petadoptionapp.presentation.ui.questionnaire.second_question

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.petadoptionapp.R
import com.example.petadoptionapp.databinding.FragmentSecondQuestionBinding
import com.example.petadoptionapp.presentation.base.BaseViewBindingFragment
import com.example.petadoptionapp.presentation.utils.Constants
import com.example.petadoptionapp.presentation.utils.extensions.setOnDebounceClickListener
import com.example.petadoptionapp.presentation.utils.extensions.viewBinding

class SecondQuestionFragment :
    BaseViewBindingFragment<FragmentSecondQuestionBinding>(R.layout.fragment_second_question) {
    override val viewBinding: FragmentSecondQuestionBinding by viewBinding(
        FragmentSecondQuestionBinding::bind
    )
    override val viewModel: SecondQuestionViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initListeners()
        initObservers()
    }

    private fun initViews() {
//        viewBinding.cat.ivIcon.setImageResource(R.drawable.ic_cat_type)
        viewBinding.cat.tvText.text = getString(EQuestionTwoPetTypes.CAT.type)
        viewBinding.dog.ivIcon.setImageResource(EQuestionTwoPetTypes.DOG.iconResource)
        viewBinding.dog.tvText.text = getString(EQuestionTwoPetTypes.DOG.type)
        viewBinding.other.ivIcon.setImageResource(EQuestionTwoPetTypes.OTHER.iconResource)
        viewBinding.other.tvText.text = getString(EQuestionTwoPetTypes.OTHER.type)
        initToolbar()
        initQuestionStep()
    }

    private fun initToolbar(){
        viewBinding.toolbar.tvTitle.text = getString(R.string.questionnaire)
    }

    private fun initQuestionStep(){
        viewBinding.question.tvTitle.text = getString(R.string.question_of, Constants.SECOND_QUESTION, Constants.NUMBER_OF_QUESTIONS)
    }

    private fun initListeners() {
        viewBinding.cat.container.setOnDebounceClickListener {

        }
        viewBinding.dog.container.setOnDebounceClickListener {

        }
        viewBinding.other.container.setOnDebounceClickListener {

        }
    }

    private fun initObservers() {

    }
}