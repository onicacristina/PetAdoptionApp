package com.example.petadoptionapp.presentation.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.petadoptionapp.R
import com.example.petadoptionapp.databinding.FragmentFavoriteBinding
import com.example.petadoptionapp.network.models.Animal
import com.example.petadoptionapp.presentation.base.BaseViewBindingFragment
import com.example.petadoptionapp.presentation.ui.favorite.adapter.FavoritesAdapter
import com.example.petadoptionapp.presentation.ui.favorite.adapter.FavoritesDiffUtils
import com.example.petadoptionapp.presentation.utils.Constants
import com.example.petadoptionapp.presentation.utils.extensions.setOnDebounceClickListener
import com.example.petadoptionapp.presentation.utils.extensions.viewBinding
import com.example.petadoptionapp.presentation.utils.showDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteFragment :
    BaseViewBindingFragment<FragmentFavoriteBinding>(R.layout.fragment_favorite) {

    override val viewBinding: FragmentFavoriteBinding by viewBinding(FragmentFavoriteBinding::bind)
    override val viewModel: FavoriteViewModel by viewModels()
    private lateinit var adapter: FavoritesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initListeners()
        initObservers()
    }

    private fun initViews() {
        initToolbar()
        setupRecyclerView()
    }

    private fun initToolbar() {
        viewBinding.toolbar.tvTitle.text = getString(R.string.favorites)
        viewBinding.toolbar.ivBack.isVisible = false
    }

    private fun initListeners() {
        viewBinding.noDataFound.btnFindPets.setOnDebounceClickListener {
            getMainActivity()?.initNavigation()
        }
        viewBinding.btnClearFavorites.setOnDebounceClickListener {
            showDeleteAllElementsFromListDialog()
        }
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                launch {
                    viewModel.state.collect { value ->
                        renderState(value)
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        val recyclerView = viewBinding.rvFavorites
        // setting grid layout manager to implement grid view
        // in this method '2' represents number of columns to be displayed in grid view
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = FavoritesAdapter(
            FavoritesDiffUtils(),
            onItemPetClickListener = {
                val bundle = Bundle()
                bundle.putString(Constants.ADOPTION_CENTER_ID, it.adoptionCenterId)
                bundle.putString(Constants.PET_ID, it.id)
                navController.navigate(R.id.toPetDetailsFragment, bundle)
            },
            onFavoriteButtonClickListener = {
                showDeleteDialog(it)
            }
        )
        recyclerView.adapter = adapter
    }

    private fun setList(data: List<Animal>) {
        adapter.submitList(data)
    }

    private fun renderState(state: FavoriteViewModel.State) {
        fun renderEmptyState() {
            viewBinding.rvFavorites.isVisible = false
            viewBinding.noDataFound.container.isVisible = true
            viewBinding.pbLoading.isVisible = false
            viewBinding.btnClearFavorites.isVisible = false
            setList(emptyList()) // Clear the list when in empty state
        }

        fun renderListState(data: List<Animal>) {
            viewBinding.rvFavorites.isVisible = true
            viewBinding.noDataFound.container.isVisible = false
            viewBinding.pbLoading.isVisible = false
            viewBinding.btnClearFavorites.isVisible = true
            setList(data)
        }

        fun renderLoadingState() {
            viewBinding.rvFavorites.isVisible = false
            viewBinding.noDataFound.container.isVisible = false
            viewBinding.pbLoading.isVisible = true
            viewBinding.btnClearFavorites.isVisible = false
            setList(emptyList()) // Clear the list when in loading state
        }

        when (state) {
            is FavoriteViewModel.State.Value -> renderListState(state.petsList)
            is FavoriteViewModel.State.Loading -> renderLoadingState()
            else -> renderEmptyState()
        }
    }

    private fun showDeleteDialog(animalResponse: Animal) {
        showDialog(
            requireContext(),
            getString(R.string.popup_delete_animal_title),
            getString(R.string.popup_delete_animal_message),
            R.drawable.btn_rounded_red,
            getString(R.string.delete),
            {
                viewModel.deleteFromFavoritesList(animalResponse)
            },
            getString(R.string.cancel),
            null,
        )
    }

    private fun showDeleteAllElementsFromListDialog() {
        showDialog(
            requireContext(),
            getString(R.string.popup_clear_list_title),
            getString(R.string.popup_clear_list_message),
            R.drawable.btn_rounded_red,
            getString(R.string.delete),
            {
                viewModel.clearFavoritesList()
            },
            getString(R.string.cancel),
            null,
        )
    }
}