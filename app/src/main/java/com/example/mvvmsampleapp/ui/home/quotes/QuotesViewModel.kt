package com.example.mvvmsampleapp.ui.home.quotes

import androidx.lifecycle.ViewModel
import com.example.mvvmsampleapp.reprosities.QuotesReprository
import com.example.mvvmsampleapp.util.lazyDeferred

class QuotesViewModel(
    repository: QuotesReprository
) : ViewModel() {

    val quotes by lazyDeferred {
        repository.getQuotes()
    }
}
