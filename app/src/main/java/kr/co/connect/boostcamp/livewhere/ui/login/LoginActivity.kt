package kr.co.connect.boostcamp.livewhere.ui.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import kr.co.connect.boostcamp.livewhere.R
import kr.co.connect.boostcamp.livewhere.databinding.ActivityLoginBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    companion object {
        private const val LOGIN_CONTAINER_ID = R.id.login_container
    }

    private val viewModel: LoginViewModel by viewModel()
    private lateinit var binding: ActivityLoginBinding
    private lateinit var currentFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, kr.co.connect.boostcamp.livewhere.R.layout.activity_detail)

        if (savedInstanceState == null) {
            addLoginFragment()
        }

        binding.apply {
            viewModel = this@LoginActivity.viewModel
            lifecycleOwner = this@LoginActivity
        }

    }

    private fun addLoginFragment() {
        val fragment = LoginFragment.newInstance()
        currentFragment = fragment
        supportFragmentManager
            .beginTransaction()
            .replace(LoginActivity.LOGIN_CONTAINER_ID, fragment)
            .commit()
    }
}