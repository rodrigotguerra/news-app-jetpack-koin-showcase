package com.rodrigotguerra.newsapp.adapters

import android.view.View

interface NewsClickListener {

    fun onNewsClicked(view: View, articleId: Int)

}