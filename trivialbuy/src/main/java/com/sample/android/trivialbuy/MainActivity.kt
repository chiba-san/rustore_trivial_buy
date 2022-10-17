package com.sample.android.trivialbuy

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.sample.android.trivialbuy.databinding.ActivityMainBinding
import com.sample.android.trivialbuy.ui.main.ProductsFragment
import com.sample.android.trivialbuy.ui.main.PurchasesFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        binding.pager.adapter = DemoAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = when (position) {
                0 -> "Продукты"
                1 -> "Покупки"
                else -> throw IllegalStateException()
            }
        }.attach()
    }

    class DemoAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment {
            val fragment = when (position) {
                0 -> ProductsFragment.newInstance()
                1 -> PurchasesFragment.newInstance()
                else -> throw IllegalStateException()
            }
            return fragment
        }
    }
}
