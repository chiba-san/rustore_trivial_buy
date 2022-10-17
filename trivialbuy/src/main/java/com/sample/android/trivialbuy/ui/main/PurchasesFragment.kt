package com.sample.android.trivialbuy.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.rustore.sdk.billingclient.utils.resolveForBilling
import com.sample.android.trivialbuy.databinding.FragmentPurchasesBinding
import com.sample.android.trivialbuy.ui.main.adapters.PurchasesAdapter
import ru.rustore.sdk.core.exception.RuStoreException

class PurchasesFragment : Fragment() {

    companion object {
        fun newInstance() = PurchasesFragment()
    }

    private lateinit var adapter: PurchasesAdapter
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentPurchasesBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentPurchasesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = PurchasesAdapter { viewModel.confirmPurchase(it) }
        binding.purchasesList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.purchasesList.adapter = adapter

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        viewModel.purchases
            .onEach {
                adapter.updateAdapter(it)
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.resultText
            .onEach {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.ruStoreException
            .onEach {
                if (it is RuStoreException) {
                    it.resolveForBilling(requireContext())
                }
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        binding.swipeRefreshView.setOnRefreshListener {
            viewModel.loadPurchases()
            binding.swipeRefreshView.isRefreshing = false
        }

        viewModel.loadPurchases()
    }
}
