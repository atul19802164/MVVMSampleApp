package com.example.mvvmsampleapp.ui.home.quotes
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.ViewHolder
import com.example.mvvmsampleapp.R
import com.example.mvvmsampleapp.data.db.AppDatabase
import com.example.mvvmsampleapp.data.db.entities.Quote
import com.example.mvvmsampleapp.data.network.MyApi
import com.example.mvvmsampleapp.network.NetworkConnectionInterceptor
import com.example.mvvmsampleapp.reprosities.QuotesReprository
import com.example.mvvmsampleapp.ui.auth.QuotesViewModelFactory
import com.example.mvvmsampleapp.util.hide
import com.example.mvvmsampleapp.util.show
import com.example.mvvmsampleapp.util.toast
import com.xwray.groupie.GroupAdapter
import kotlinx.android.synthetic.main.quotes_fragment.*
import net.simplifiedcoding.mvvmsampleapp.util.Coroutines
import java.util.Collections.addAll

class QuotesFragment : Fragment() {

    companion object {
        fun newInstance() = QuotesFragment()
    }

    private lateinit var viewModel: QuotesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.quotes_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var networkConnectionInterceptor= NetworkConnectionInterceptor(context!!)
        var api= MyApi(networkConnectionInterceptor)
        var db= AppDatabase(context!!)
        var reprosity= QuotesReprository(api,db)
        var factory= QuotesViewModelFactory(reprosity)
        viewModel = ViewModelProviders.of(this,factory).get(QuotesViewModel::class.java)
bindUI()
    }

    private fun bindUI() = Coroutines.main {
        progress_bar.show()
        viewModel.quotes.await().observe(this, Observer {
            progress_bar.hide()
            initRecyclerView(it.toQuoteItem())
        })
    }

    private fun initRecyclerView(quoteItem: List<QuoteItem>) {

        val mAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(quoteItem)
        }

        recyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = mAdapter
        }

    }


    private fun List<Quote>.toQuoteItem() : List<QuoteItem>{
        return this.map {
            QuoteItem(it)
        }
    }

}


