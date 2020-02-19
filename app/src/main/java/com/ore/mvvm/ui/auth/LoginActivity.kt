package com.ore.mvvm.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ore.mvvm.R
import com.ore.mvvm.data.db.AppDatabase
import com.ore.mvvm.data.db.entities.User
import com.ore.mvvm.data.network.MyApi
import com.ore.mvvm.data.network.NetworkConnectionInterceptor
import com.ore.mvvm.data.repositories.UserRepository
import com.ore.mvvm.databinding.ActivityLoginBinding
import com.ore.mvvm.ui.home.HomeActivity
import com.ore.mvvm.utils.hide
import com.ore.mvvm.utils.show
import com.ore.mvvm.utils.snackbar
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), AuthListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val networkConnectionInterceptor = NetworkConnectionInterceptor(this)
        // NetworkConnectionInterceptor needs to be passed into MyApi function
        val api = MyApi(networkConnectionInterceptor)
        val database = AppDatabase(this)
        val repository = UserRepository(api, database)
        val factory = AuthViewModelFactory(repository)

        val binding: ActivityLoginBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_login)
        val viewModel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)
        binding.viewmodel = viewModel

        viewModel.authListener = this

        viewModel.getLoggedInUser().observe(this, Observer { user ->
            if (user != null) {
                Intent(this, HomeActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }
        })

    }

    override fun onStarted() {
        progress_bar.show()
    }

    override fun onSuccess(user: User) {
        progress_bar.hide()
//        root_layout.snackbar("${user.name} is logged in")
    }

    override fun onFailure(message: String) {
        progress_bar.hide()
        root_layout.snackbar(message)
    }
}
