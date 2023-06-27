package com.br.impacta.doemais.ui.fragment.andamento

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.br.impacta.doemais.databinding.FragmentListaEmAndamentoBinding
import com.br.impacta.doemais.ui.Pedido.PedidoViewModel
import com.br.impacta.doemais.ui.campanha.CampanhaViewModel
import com.br.impacta.doemais.ui.location.ReposLoadStateAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [ListaEmAndamentoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListaEmAndamentoFragment : Fragment() {

    private var _binding: FragmentListaEmAndamentoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentListaEmAndamentoBinding.inflate(inflater, container, false)

        val viewModel: AndamentoViewModel by activityViewModels()

        val items = viewModel.products
        val articleAdapter = AndamentoAdapter { location, fisrtClick ->

            viewModel.setLocation(location, fisrtClick)
        }

        binding.bindAdapter(articleAdapter)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                items.collectLatest {
                    articleAdapter.submitData(it)
                }
            }
        }


        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun FragmentListaEmAndamentoBinding.bindAdapter(andamentoAdapter: AndamentoAdapter) {
        recyclerViewPedido.adapter = andamentoAdapter

        recyclerViewPedido.layoutManager = LinearLayoutManager(recyclerViewPedido.context)
        val decoration =
            DividerItemDecoration(recyclerViewPedido.context, DividerItemDecoration.VERTICAL)
        recyclerViewPedido.addItemDecoration(decoration)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}