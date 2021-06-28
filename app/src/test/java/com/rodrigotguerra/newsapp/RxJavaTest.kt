package com.rodrigotguerra.newsapp

import com.rodrigotguerra.newsapp.models.NewsListResponseSchema
import com.rodrigotguerra.newsapp.repo.RemoteRepositoryInterface
import io.reactivex.Single.just
import io.reactivex.observers.TestObserver
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule


class RxJavaTest {

    @get:Rule
    val rule: MockitoRule = MockitoJUnit.rule()

    private val repository: RemoteRepositoryInterface = Mockito.mock(RemoteRepositoryInterface::class.java)

    @Test
    fun getNews_return_list_with_success_sync_with_blockingGet()  {
        Mockito.`when`(repository.getBrNewsFromApi()).thenReturn(just(NewsFakeObject.newsResponse))
        val newsResponse : NewsListResponseSchema = repository.getBrNewsFromApi().blockingGet()
        Assert.assertEquals(newsResponse.newsList.size, NewsFakeObject.newsResponse.newsList.size)
    }

    @Test
    fun getNews_return_mapped_list_success_with_testObserver() {
        Mockito.`when`(repository.getBrNewsFromApi()).thenReturn(just(NewsFakeObject.newsResponse))
        val newsResponse : TestObserver<NewsListResponseSchema> = repository.getBrNewsFromApi().test()
        newsResponse.awaitTerminalEvent()
        newsResponse.assertNoErrors().assertValueAt(0) { response ->
            response.newsList[2] == NewsFakeObject.newsResponse.newsList[2]
        }
    }



}