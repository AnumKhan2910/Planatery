package com.adyen.android.assignment.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.adyen.android.assignment.databinding.FragmentApodListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class APODListFragment: Fragment() {

    private val listViewModel: APODListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentApodListBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = listViewModel
            adapter = APODDataAdapter {  }
        }.root
    }
}