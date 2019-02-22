package kr.co.connect.boostcamp.livewhere.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kr.co.connect.boostcamp.livewhere.databinding.FragmentPolicyBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PolicyFragment : Fragment() {
    companion object {
        fun newInstance(): PolicyFragment {
            val args = Bundle()
            val fragment = PolicyFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private val homeViewModel: HomeViewModel by sharedViewModel()
    private lateinit var binding: FragmentPolicyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPolicyBinding.inflate(inflater, container, false)
        binding.homeViewModel = homeViewModel
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

    }
}