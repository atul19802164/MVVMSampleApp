package com.example.mvvmsampleapp.network.responses

import com.example.mvvmsampleapp.data.db.entities.Quote

data class QuoteResponse (val isSuccessful:Boolean,
                          var quotes:List<Quote>)