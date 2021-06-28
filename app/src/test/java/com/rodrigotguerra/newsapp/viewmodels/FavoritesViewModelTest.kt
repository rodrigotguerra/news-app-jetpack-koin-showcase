package com.rodrigotguerra.newsapp.viewmodels


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.rodrigotguerra.newsapp.NewsFakeObject
import com.rodrigotguerra.newsapp.getOrAwaitValue
import com.rodrigotguerra.newsapp.models.NewsData
import com.rodrigotguerra.newsapp.repo.CachedRepositoryInterface
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class FavoritesViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: FavoritesViewModel
    private val repository = mockk<CachedRepositoryInterface>()

    @Before
    fun setup() {
        viewModel = FavoritesViewModel(repository)
    }

    @Test
    fun `test posting value on object`() = runBlocking {
        coEvery { repository.getFavoriteArticles() } returns NewsFakeObject.newsResponse.newsList
        viewModel.getFavoritesFromDB()
        assertThat(viewModel.favoritesList.getOrAwaitValue()).hasSize(4)
    }

    @Test
    fun `test posting value error`() = runBlocking {
        coEvery { repository.getFavoriteArticles() } returns listOf<NewsData>()
        viewModel.getFavoritesFromDB()
        assertThat(viewModel.noFavoritesFound.getOrAwaitValue()).isTrue()
    }
}