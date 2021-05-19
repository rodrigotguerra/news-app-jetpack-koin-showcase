package com.rodrigotguerra.newsapp.views

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.rodrigotguerra.newsapp.R
import com.rodrigotguerra.newsapp.adapters.FavoritesAdapter
import com.rodrigotguerra.newsapp.adapters.NewsAdapter
import com.rodrigotguerra.newsapp.databinding.FragmentFavoritesBinding
import com.rodrigotguerra.newsapp.databinding.FragmentNewsBinding
import com.rodrigotguerra.newsapp.viewmodels.FavoritesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    private val viewModel: FavoritesViewModel by viewModel()

    private val newsAdapter = FavoritesAdapter(ArrayList())
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFavoritesBinding.bind(view)

        binding.rvFavoritesList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = newsAdapter
        }

        viewModel.getFavoritesFromDB()

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.favoritesList.observe(viewLifecycleOwner, Observer { favorites ->
            favorites?.let {
                binding.rvFavoritesList.visibility = View.VISIBLE
                newsAdapter.bindData(it)
            }
        })
        viewModel.noFavoritesFound.observe(viewLifecycleOwner, Observer { isEmpty ->
            isEmpty?.let {
                binding.tvNoFavoritesFound.visibility = if (it) View.VISIBLE else View.GONE
            }
        })
        viewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            isLoading?.let {
                binding.pbLoadingFavorites.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    binding.rvFavoritesList.visibility = View.GONE
                    binding.tvNoFavoritesFound.visibility = View.GONE
                }
            }
        })
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}