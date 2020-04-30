
package com.example.mvvmsampleapp.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.mvvmsampleapp.R
import com.example.mvvmsampleapp.data.db.AppDatabase
import com.example.mvvmsampleapp.data.db.entities.User
import com.example.mvvmsampleapp.data.network.MyApi
import com.example.mvvmsampleapp.databinding.ActivityLoginBinding
import com.example.mvvmsampleapp.network.NetworkConnectionInterceptor
import com.example.mvvmsampleapp.reprosities.UserRepository
import com.example.mvvmsampleapp.ui.home.HomeActivity
import com.example.mvvmsampleapp.util.hide
import com.example.mvvmsampleapp.util.show
import com.example.mvvmsampleapp.util.snackbar
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.progress_bar

class LoginActivity : AppCompatActivity(),AuthListener {
    override fun onStarted() {
progress_bar.show()
    }
    override fun onSuccess(user:User) {
        progress_bar.hide()
    }

    override fun onFailure(message: String) {
        progress_bar.hide()
        root_layout.snackbar(message)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var networkConnectionInterceptor=NetworkConnectionInterceptor(this)
        var api= MyApi(networkConnectionInterceptor)
        var db=AppDatabase(this)
        var reprosity=UserRepository(api,db)
        var factory=AuthViewModelFactory(reprosity)
        val binding: ActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        val viewModel = ViewModelProviders.of(this,factory).get(AuthViewModel::class.java)
        binding.viewmodel = viewModel
        viewModel.authListener = this
        viewModel.getLoggedInUser().observe(this,Observer{
            if(it!=null){
               var intent= Intent(this@LoginActivity, HomeActivity::class.java)
                    intent.flags=Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
            }


        })
    }


}
