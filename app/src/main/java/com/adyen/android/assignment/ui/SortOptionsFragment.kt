package com.adyen.android.assignment.ui

import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import androidx.constraintlayout.widget.Constraints
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.adyen.android.assignment.R
import com.adyen.android.assignment.databinding.FragmentSortOptionsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SortOptionsFragment: DialogFragment() {

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppTheme_Dialog_MyDialogTheme)
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(Constraints.LayoutParams.WRAP_CONTENT, Constraints.LayoutParams.WRAP_CONTENT)
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