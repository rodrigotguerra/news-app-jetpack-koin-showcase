package com.rodrigotguerra.newsapp.views

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.rodrigotguerra.newsapp.R
import com.rodrigotguerra.newsapp.databinding.FragmentArticleDetailsBinding
import com.rodrigotguerra.newsapp.viewmodels.ArticleDetailsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ArticleDetailsFragment : Fragment() {

    private val viewModel: ArticleDetailsViewModel by viewModel()
    private var articleId = 0
    private var isArticleFavorite = false
    private lateinit var dataBinding: FragmentArticleDetailsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_article_details, container, false)
        setHasOptionsMenu(true)
        return dataBinding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_favorite) {
            item.isChecked = isArticleFavorite
            viewModel.favoriteAnArticle()
            Toast.makeText(context, "Fav clicked", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            articleId = ArticleDetailsFragmentArgs.fromBundle(it).articleId
        }

        viewModel.fetch(articleId)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.articleLiveData.observe(viewLifecycleOwner, Observer { article ->
            article?.let {
                dataBinding.article = article
            }
        })
        viewModel.isFavorite.observe(viewLifecycleOwner, Observer { isFavorite ->
            isArticleFavorite = isFavorite
        })
    }

}