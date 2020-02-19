package com.ore.mvvm.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ore.mvvm.R
import com.ore.mvvm.data.db.entities.User
import com.ore.mvvm.databinding.ActivityLoginBinding
import com.ore.mvvm.ui.home.HomeActivity
import com.ore.mvvm.utils.hide
import com.ore.mvvm.utils.show
import com.ore.mvvm.utils.snackbar
import kotlinx.android.synthetic.main.activity_login.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

// AuthListener is implemented along with AppCompat to add callback functions onStarted, onSuccess & onFailure
// AuthListener is also used in the AuthViewModel so that the data entered is able to be validated for callback functions to work
class LoginActivity : AppCompatActivity(), AuthListener, KodeinAware {

    // implementing dependency injection from MyApplication class
    override val kodein by kodein()
    private val factory: AuthViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get automatically-generated Binding Instance to set activity content view
        val binding: ActivityLoginBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_login)
        // Creates instance of ViewModel to connect our AuthViewModel to our Login Activity
        val viewModel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)
        // sets the viewModel as our binding viewModel and data with the UI
        binding.viewmodel = viewModel
        // defines AuthListener to our viewModel
        viewModel.authListener = this

        // Observes logged in User,
        viewModel.getLoggedInUser().observe(this, Observer { user ->
            // If there's a user logged in,
            if (user != null) {
                Intent(this, HomeActivity::class.java).also {
                    // Direct them to HomePage, and pop off login page from backs tack
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }
        })

    }

    // AuthListener functions onStarted, onSuccess and onFailure are overridden here
    // Helps determine what to display to the user based on probable results/data from AuthViewModel
    // Extension functions for making Toasts and Snackbar messages are in ViewUtils File in Utils Package
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
