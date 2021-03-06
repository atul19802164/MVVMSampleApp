package com.example.mvvmsampleapp.ui.home.quotes

import com.example.mvvmsampleapp.R
import com.example.mvvmsampleapp.data.db.entities.Quote
import com.xwray.groupie.databinding.BindableItem
import com.example.mvvmsampleapp.databinding.ItemQuoteBinding
class QuoteItem(private val quote:Quote):BindableItem<ItemQuoteBinding>(){
    override fun getLayout()= R.layout.item_quote
    override fun bind(viewBinding: ItemQuoteBinding, position: Int) {
   viewBinding.setQuote(quote)
    }

}