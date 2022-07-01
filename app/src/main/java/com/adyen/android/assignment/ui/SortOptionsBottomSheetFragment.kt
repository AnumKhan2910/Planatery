package com.adyen.android.assignment.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.activityViewModels
import com.adyen.android.assignment.databinding.FragmentSortOptionsBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SortOptionsBottomSheetFragment: BottomSheetDialogFragment() {

    private val listViewModel: APODListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        listViewModel.resetDismissValue()
        return FragmentSortOptionsBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = listViewModel
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listViewModel.actionDismissDialog.observe(
            this, {
                if (it) { dismiss() }
            }
        )
    }

}