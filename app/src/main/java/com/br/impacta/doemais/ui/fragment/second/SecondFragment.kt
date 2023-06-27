package com.br.impacta.doemais.ui.fragment.second

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.*
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.br.impacta.doemais.R
import com.br.impacta.doemais.databinding.FragmentSecondBinding
import com.br.impacta.doemais.ui.fragment.andamento.AndamentoFragment
import com.br.impacta.doemais.ui.fragment.finalizado.FinalizadoFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
@AndroidEntryPoint
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)

        val adapter = ViewPagerAdapter(this)
        adapter.addFragment(AndamentoFragment())
        adapter.addFragment(FinalizadoFragment())


        binding.viewPager.adapter = adapter


        TabLayoutMediator(binding.tabLayout, binding.viewPager
        ) { tab: TabLayout.Tab, position: Int ->
            when (position) {
                0 -> tab.text = "Em Andamento"
                1 -> tab.text = "Finalizado"
            }
        }.attach()


        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_orderFormFragment)
        }
    }

    private fun buildAdapter(): PagerAdapter? {
        return FragmentAdapter(childFragmentManager)
    }


    class ViewPagerAdapter(fragment: Fragment) :
        FragmentStateAdapter(fragment) {
        private val arrayList: ArrayList<Fragment> = ArrayList()

        fun getItem(position: Int): Fragment {
            return arrayList[position]
        }

        fun addFragment(fragment: Fragment) {
            arrayList.add(fragment)
        }

        override fun getItemCount(): Int {
            return arrayList.size
        }

        override fun createFragment(position: Int): Fragment {
            return arrayList[position]
        }}

    class FragmentAdapter(fragmentManager: FragmentManager ) :  FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){
        override fun getCount(): Int {
            return 1
        }

        override fun getItem(position: Int): Fragment {
            return when(position){
                0 -> AndamentoFragment()
                else -> FinalizadoFragment()
            }
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}