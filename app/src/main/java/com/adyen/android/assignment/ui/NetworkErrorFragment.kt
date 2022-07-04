package com.adyen.android.assignment.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.adyen.android.assignment.databinding.FragmentNetworkErrorBinding
import dagger.hilt.android.AndroidEntryPoint
import android.content.Intent
import android.provider.Settings

@AndroidEntryPoint
class NetworkErrorFragment : Fragment() {

    private lateinit var binding: FragmentNetworkErrorBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =  FragmentNetworkErrorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnOpenNetwork.setOnClickListener { openNetworkSettings() }
    }

    private fun openNetworkSettings() {
        val intent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
        startActivity(intent)
    }
}