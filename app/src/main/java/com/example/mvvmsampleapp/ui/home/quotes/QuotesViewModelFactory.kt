package com.example.mvvmsampleapp.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmsampleapp.reprosities.QuotesReprository
import com.example.mvvmsampleapp.reprosities.UserRepository
import com.example.mvvmsampleapp.ui.home.profile.ProfileViewModel
import com.example.mvvmsampleapp.ui.home.quotes.QuotesViewModel

@Suppress("UNCHECKED_CAST")
class QuotesViewModelFactory(
    private val repository: QuotesReprository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return QuotesViewModel(repository) as T
    }
}