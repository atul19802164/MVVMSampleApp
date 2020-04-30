package com.example.mvvmsampleapp.ui.auth
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.mvvmsampleapp.reprosities.UserRepository
import net.simplifiedcoding.mvvmsampleapp.util.ApiException
import net.simplifiedcoding.mvvmsampleapp.util.Coroutines
import net.simplifiedcoding.mvvmsampleapp.util.NoInternetException


class AuthViewModel(val repository: UserRepository
) : ViewModel() {

    var name: String? = null
    var email: String? = null
    var password: String? = null
    var passwordconfirm: String? = null
    var authListener:AuthListener?=null
    fun getLoggedInUser()=repository.getUser()
    fun onSignUpClick(view: View){
        view.context.startActivity(Intent(view.context,SignUpActivity::class.java))
    }
    fun onLogInClick(view: View){
        view.context.startActivity(Intent(view.context,SignUpActivity::class.java))
    }
    fun onLoginButtonClick(view: View) {
        authListener!!.onStarted()
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            authListener!!.onFailure("invalid email or password")
            return
        }
        Coroutines.main{
            try{
            var authResponse=repository!!.login(email!!,password!!)
             authResponse.user.let {
                 authListener!!.onSuccess(it!!)
                 repository.saveUser(it)
                              }

            }
            catch (e:ApiException){
                authListener!!.onFailure(e.message!!)
            }
            catch (e:NoInternetException){
                authListener!!.onFailure(e.message!!)
            }
        }

    }
    fun onSignUpButtonClick(view: View) {
        authListener!!.onStarted()
        if (name.isNullOrEmpty()||email.isNullOrEmpty() || password.isNullOrEmpty()
            ||passwordconfirm.isNullOrEmpty()) {
            authListener!!.onFailure("All fields are required")
        }
            if(passwordconfirm!=password) {
                authListener!!.onFailure("Password and confirm password not matching")
                return
            }
        Coroutines.main{
            try{
                var authResponse=repository!!.signup(name!!,email!!,password!!)
                authResponse.user.let {
                    authListener!!.onSuccess(it!!)
                    repository.saveUser(it)
                }

            }
            catch (e:ApiException){
                authListener!!.onFailure(e.message!!)
            }
            catch (e:NoInternetException){
                authListener!!.onFailure(e.message!!)
            }
        }

    }
}