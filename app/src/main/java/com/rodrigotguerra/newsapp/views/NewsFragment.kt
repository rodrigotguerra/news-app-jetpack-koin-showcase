package com.rodrigotguerra.newsapp.views

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.rodrigotguerra.newsapp.R
import com.rodrigotguerra.newsapp.adapters.NewsAdapter
import com.rodrigotguerra.newsapp.databinding.FragmentNewsBinding
import com.rodrigotguerra.newsapp.viewmodels.NewsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewsFragment : Fragment(R.layout.fragment_news) {

    private val viewModel: NewsViewModel by viewModel()
    private val newsAdapter = NewsAdapter(ArrayList())
    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentNewsBinding.bind(view)

        viewModel.refresh()

        binding.rvNewsList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = newsAdapter
        }

        binding.refreshLayout.setOnRefreshListener {
            viewModel.refreshByApiCall()
            binding.refreshLayout.isRefreshing = false
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.newsList.observe(viewLifecycleOwner, Observer { news ->
            news?.let {
                binding.rvNewsList.visibility = View.VISIBLE
                newsAdapter.bindData(it)
            }
        })
        viewModel.newsLoadError.observe(viewLifecycleOwner, Observer { isError ->
            isError?.let {
                binding.tvListError.visibility = if (it) View.VISIBLE else View.GONE
            }
        })
        viewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            isLoading?.let {
                binding.pbLoadingData.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    binding.rvNewsList.visibility = View.GONE
                    binding.tvListError.visibility = View.GONE
                }
            }
        })
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}