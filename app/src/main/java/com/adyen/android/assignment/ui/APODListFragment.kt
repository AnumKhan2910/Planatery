package com.adyen.android.assignment.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.*
import androidx.navigation.Navigation
import com.adyen.android.assignment.R
import com.adyen.android.assignment.api.model.AstronomyPicture
import com.adyen.android.assignment.databinding.FragmentApodListBinding
import com.adyen.android.assignment.utils.StringResourceManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class APODListFragment: Fragment() {

    private val listViewModel: APODListViewModel by activityViewModels()

    @Inject
    lateinit var stringResourceManager: StringResourceManager

    private lateinit var binding: FragmentApodListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentApodListBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = listViewModel
            adapter = APODDataAdapter {
                navigateToPreviewScreen(it)
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnReorder.setOnClickListener { showSortFragment() }
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

    private fun navigateToPreviewScreen(data: AstronomyPicture) {
        view?.let {
            Navigation.findNavController(it)
                .navigate(
                    APODListFragmentDirections
                        .actionAPODListFragmentToAPODPreviewFragment(data)
                )
        }
    }
}