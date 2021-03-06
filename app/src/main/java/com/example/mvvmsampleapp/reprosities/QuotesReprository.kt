package com.example.mvvmsampleapp.reprosities
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mvvmsampleapp.data.db.AppDatabase
import com.example.mvvmsampleapp.data.db.entities.Quote
import com.example.mvvmsampleapp.data.network.MyApi
import com.example.mvvmsampleapp.network.SafeApiRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.simplifiedcoding.mvvmsampleapp.util.Coroutines

class QuotesReprository(var api: MyApi, var db: AppDatabase
): SafeApiRequest(){
    private val quotes = MutableLiveData<List<Quote>>()

    init {
        quotes.observeForever {
            saveQuotes(it)
        }
    }

    suspend fun getQuotes(): LiveData<List<Quote>> {
        return withContext(Dispatchers.IO) {
            fetchQuotes()
            db.getQuoteDao().getQuotes()
        }
    }

    private suspend fun fetchQuotes() {

        if(isFetchNeeded()){
            val response = apiRequest { api.getQuotes() }
            quotes.postValue(response.quotes)
        }

    }
    private fun isFetchNeeded(): Boolean {
        return true
    }


    private fun saveQuotes(quotes: List<Quote>) {
        Coroutines.io {
            db.getQuoteDao().saveAllQuotes(quotes)
        }
    }

}