package kr.co.connect.boostcamp.livewhere.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kr.co.connect.boostcamp.livewhere.R

class SearchFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return if (inflater != null) {
            inflater.inflate(R.layout.fragment_search, container, false)
        } else {
            return super.onCreateView(inflater, container, savedInstanceState)
        }
    }
}