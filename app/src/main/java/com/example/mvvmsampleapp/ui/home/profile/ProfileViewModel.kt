package com.example.mvvmsampleapp.ui.home.profile

import androidx.lifecycle.ViewModel;
import com.example.mvvmsampleapp.reprosities.UserRepository

class ProfileViewModel(val repository: UserRepository) : ViewModel() {
    var user=repository.getUser()

}
