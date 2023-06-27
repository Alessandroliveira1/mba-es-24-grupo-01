package com.br.impacta.doemais.ui.fragment.andamento

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.br.impacta.doemais.R
import com.br.impacta.doemais.commom.BaseSingleton.getIdAuth
import com.br.impacta.doemais.databinding.FragmentAndamentoBinding
import com.br.impacta.doemais.databinding.FragmentSecondBinding
import com.br.impacta.doemais.ui.Pedido.PedidoAdapter
import com.br.impacta.doemais.ui.Pedido.PedidoViewModel
import com.br.impacta.doemais.ui.campanha.CampanhaViewModel
import com.br.impacta.doemais.ui.location.ReposLoadStateAdapter
import com.google.android.gms.maps.SupportMapFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [AndamentoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AndamentoFragment : Fragment() {

    private var _binding: FragmentAndamentoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val viewModel: AndamentoViewModel by activityViewModels()

        _binding = FragmentAndamentoBinding.inflate(inflater, container, false)

        val navHostFragment = childFragmentManager.findFragmentById(R.id.nav_host_andamento_fragment) as NavHostFragment
        viewModel.liveDataLocation.observe(viewLifecycleOwner) { hashmap ->
            hashmap.entries.forEach { index ->
                if (index.value) {
                    val bundle = Bundle()
                    bundle.putString("name", index.key.name)
                    bundle.putString("location", index.key.collectPoint)
                    bundle.putString("key", index.key.key)
                    bundle.putString("donation", index.key.type)
                    bundle.putCharSequence("description", index.key.description)
                    bundle.putString("id", index.key.id)
                    bundle.putString("id2", index.key.id2)
                    bundle.putString("timeline", index.key.timeline)
                    bundle.putString("perfil", index.key.perfil)
                    //bundle.putString("title", index.key.title)

                    if (index.key.id == getIdAuth()) {
                    navHostFragment.navController.navigate(R.id.action_listaEmAndamentoFragment_to_detailEmAndamentoFragment, bundle)
                    } else {
                        navHostFragment.navController.navigate(R.id.action_listaEmAndamentoFragment_to_detailDonorEmAndamentoFragment, bundle)
                    }
                    lifecycleScope.launch {

                    }
                }}
        }

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}