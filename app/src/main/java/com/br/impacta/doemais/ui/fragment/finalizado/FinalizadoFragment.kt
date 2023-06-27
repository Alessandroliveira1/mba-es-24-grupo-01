package com.br.impacta.doemais.ui.fragment.finalizado

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.br.impacta.doemais.R
import com.br.impacta.doemais.commom.BaseSingleton
import com.br.impacta.doemais.databinding.FragmentAndamentoBinding
import com.br.impacta.doemais.databinding.FragmentFinalizadoBinding
import com.br.impacta.doemais.ui.campanha.CampanhaViewModel
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FinalizadoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FinalizadoFragment : Fragment() {
    private var _binding: FragmentFinalizadoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val viewModel: FinalizadoViewModel by activityViewModels()

        _binding = FragmentFinalizadoBinding.inflate(inflater, container, false)

        val navHostFragment = childFragmentManager.findFragmentById(R.id.nav_host_finalizado_fragment) as NavHostFragment
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
                    bundle.putString("timeline", index.key.timeline)
                    bundle.putString("perfil", index.key.perfil)
                    //bundle.putString("title", index.key.title)

                    if (index.key.id == BaseSingleton.getIdAuth()) {
                        navHostFragment.navController.navigate(R.id.action_LIstaFinalizado2Fragment_to_detailDonorFinalizadoFragment, bundle)
                    } else {
                        navHostFragment.navController.navigate(R.id.action_LIstaFinalizado2Fragment_to_detailDonorFinalizadoFragment, bundle)
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