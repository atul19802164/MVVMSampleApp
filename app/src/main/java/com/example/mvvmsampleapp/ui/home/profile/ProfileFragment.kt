package com.example.mvvmsampleapp.ui.home.profile

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.example.mvvmsampleapp.R
import com.example.mvvmsampleapp.data.db.AppDatabase
import com.example.mvvmsampleapp.data.network.MyApi
import com.example.mvvmsampleapp.databinding.ProfileFragmentBinding
import com.example.mvvmsampleapp.network.NetworkConnectionInterceptor
import com.example.mvvmsampleapp.reprosities.UserRepository
import com.example.mvvmsampleapp.ui.home.profile.ProfileViewModel
import com.example.mvvmsampleapp.ui.auth.ProfileViewModelFactory
class ProfileFragment : Fragment() {

    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var networkConnectionInterceptor= NetworkConnectionInterceptor(context!!)
        var api= MyApi(networkConnectionInterceptor)
        var db= AppDatabase(context!!)
        var reprosity= UserRepository(api,db)
        var factory= ProfileViewModelFactory(reprosity)
        var binding:ProfileFragmentBinding=DataBindingUtil.inflate(inflater,R.layout.profile_fragment,container,false)
        viewModel= ViewModelProviders.of(this,factory).get(ProfileViewModel::class.java)
        binding.viewmodel=viewModel
        binding.lifecycleOwner=this
        return binding.root
    }



}
