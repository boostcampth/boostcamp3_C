package kr.co.connect.boostcamp.livewhere.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kr.co.connect.boostcamp.livewhere.databinding.FragmentLoginBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class LoginFragment: Fragment(){
    companion object {
        fun newInstance(): LoginFragment {
            val args = Bundle()
            val fragment = LoginFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private val viewModel: LoginViewModel by sharedViewModel()
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentLoginBinding.inflate(inflater,container,false).apply {
            viewModel = this@LoginFragment.viewModel
            lifecycleOwner = this@LoginFragment
        }

        return binding.root
    }

}