package com.example.petadoptionapp.presentation.ui.questionnaire

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.petadoptionapp.R
import com.example.petadoptionapp.databinding.FragmentStartQuestionnaireBinding
import com.example.petadoptionapp.presentation.base.BaseViewBindingFragment
import com.example.petadoptionapp.presentation.utils.extensions.setOnDebounceClickListener
import com.example.petadoptionapp.presentation.utils.extensions.viewBinding

class StartQuestionnaireFragment :
    BaseViewBindingFragment<FragmentStartQuestionnaireBinding>(R.layout.fragment_start_questionnaire) {
    override val viewBinding: FragmentStartQuestionnaireBinding by viewBinding(
        FragmentStartQuestionnaireBinding::bind
    )

    override val viewModel: StartQuestionnaireViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initListeners()
    }

    private fun initViews() {

    }

    private fun initListeners() {
        viewBinding.btnStartNow.setOnDebounceClickListener {
            navController.navigate(R.id.secondQuestionFragment)
            //TODO
        }
        viewBinding.tvAskMeLater.setOnDebounceClickListener {
            //TODO
        }
    }
}