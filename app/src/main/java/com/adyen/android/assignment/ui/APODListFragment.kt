package com.adyen.android.assignment.ui

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.*
import androidx.navigation.Navigation
import com.adyen.android.assignment.data.network.AstronomyPicture
import com.adyen.android.assignment.databinding.FragmentApodListBinding
import com.adyen.android.assignment.utils.StringResourceManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

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
        fetchData()
        binding.btnReorder.setOnClickListener { showSortFragment() }
        listViewModel.showErrorScreen.observe(viewLifecycleOwner, {
            if (it) {
                navigateToErrorScreen()
                listViewModel.resetErrorValue()
            }
        })
    }

    private fun fetchData() {
        if (isNetworkAvailable(requireContext()).not()) {
            navigateToNetworkErrorScreen()
        } else {
            listViewModel.fetchData()
        }
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

    private fun navigateToNetworkErrorScreen() {
        view?.let {
            Navigation.findNavController(it)
                .navigate(
                    APODListFragmentDirections
                        .actionAPODListFragmentToNetworkErrorFragment()
                )
        }
    }

    private fun navigateToErrorScreen() {
        view?.let {
            Navigation.findNavController(it)
                .navigate(
                    APODListFragmentDirections
                        .actionAPODListFragmentToErrorFragment()
                )
        }
    }

    private fun isNetworkAvailable(context: Context) =
        (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).run {
            getNetworkCapabilities(activeNetwork)?.run {
                hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                    || hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                    || hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
            } ?: false
        }
}