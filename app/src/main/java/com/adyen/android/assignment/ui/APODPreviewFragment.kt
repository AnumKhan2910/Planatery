package com.adyen.android.assignment.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.adyen.android.assignment.databinding.FragmentApodListBinding
import com.adyen.android.assignment.databinding.FragmentApodPreviewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class APODPreviewFragment: Fragment() {

    private val args: APODPreviewFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        return FragmentApodPreviewBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            data = args.pictureData
        }.root
    }
}