package com.adyen.android.assignment.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.*
import androidx.navigation.Navigation
import com.adyen.android.assignment.R
import com.adyen.android.assignment.databinding.FragmentApodListBinding
import com.adyen.android.assignment.utils.StringResourceManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class APODListFragment: Fragment() {

    private val listViewModel: APODListViewModel by activityViewModels()

    @Inject
    lateinit var stringResourceManager: StringResourceManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        return FragmentApodListBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = listViewModel
            adapter = APODDataAdapter {  }
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = stringResourceManager.getString(R.string.astronomical_pictures)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.more_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.sortBy) {
            showSortFragment()
        }
        return true
    }

    private fun showSortFragment() {
        view?.let {
            Navigation.findNavController(it)
                .navigate(
                    APODListFragmentDirections
                        .actionAPODListFragmentToSortOptionsBottomSheetFragment()
                )
        }
    }
}