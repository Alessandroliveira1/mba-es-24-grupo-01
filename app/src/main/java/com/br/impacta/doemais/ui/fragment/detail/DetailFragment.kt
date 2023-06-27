package com.br.impacta.doemais.ui.fragment.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.br.impacta.doemais.R
import com.br.impacta.doemais.commom.ImageEnum
import com.br.impacta.doemais.data.pedido.Pedido
import com.br.impacta.doemais.databinding.FragmentDetailBinding
import com.br.impacta.doemais.room.DAODoacao
import com.br.impacta.doemais.room.DAOPedido
import com.br.impacta.doemais.ui.campanha.CampanhaViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class DetailFragment : Fragment(), OnMapReadyCallback {
    // TODO: Rename and change types of parameters
    private var name: String? = null
    private var location: String? = null
    private var img: Int? = null
    private var donation: String? = null
    private var description: CharSequence? = null
    private var title: String? = null
    private var key: String? = null
    private var id: String? = null
    private lateinit var binding: FragmentDetailBinding
    private lateinit var viewModelHome: CampanhaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            name = it.getString("name")
            location = it.getString("location")
            img = it.getInt("img")
            donation = it.getString("donation")
            description = it.getCharSequence("description")
            title = it.getString("title")
            key = it.getString("key")
            id = it.getString("id")
        }
    }

    private fun getImage(value: Int): Int {
        return when(value){
            ImageEnum.FOOD.value -> R.drawable.perfil
            ImageEnum.CLOTHER.value -> R.drawable.clother
            ImageEnum.VOLUNTARY.value -> R.drawable.voluntary
            ImageEnum.SUPPORT.value -> R.drawable.support
            else -> {
                R.drawable.ic_launcher_background
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        val viewModel: CampanhaViewModel by activityViewModels()
        viewModel.liveClickConfirm.observe(viewLifecycleOwner) {
            insertData(viewModel)
        }

        viewModelHome = viewModel
        viewModelHome.setButtonView(true)
        binding.tvLocation.text = location
        binding.tvName.text = name
        binding.imageView.setBackgroundResource(getImage(img!!))
        binding.tvDonation.text = donation
        binding.tvDescription.text = description
        binding.tvTitle.text = title
        return binding.root
    }

    private fun insertData(viewModel: CampanhaViewModel) {

//        val radioId = _binding?.radioGroup?.checkedRadioButtonId
//        val radioBtn: RadioButton? = radioId?.let { view.findViewById(it) }

        val doacaoModel = Pedido(
            collectPoint = location,
            description = description.toString(),
            name = name, type = donation, id = id,
            id2 = Firebase.auth.uid ?: "",
            perfil = "doador",
            timeline = "1")

//        val db = DAOPedido()
//        db.remove(key)


        val db = DAOPedido()

        db.add(doacaoModel)

        val db2 = DAODoacao()
        db2.remove(key)

//
//        val hash = HashMap<String?, String?>()
//
//        hash["id2"] = Firebase.auth.uid ?: ""
//        val db2 = DAODoacao()
//
//        db2.update(key, hash)

        //db2.add(doacaoModel)
        findNavController().popBackStack()

    }


    override fun onMapReady(p0: GoogleMap) {
        TODO("Not yet implemented")
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModelHome.setButtonView(false)
    }

    override fun onDetach() {
        super.onDetach()
        viewModelHome.setButtonView(false)

    }

    override fun onPause() {
        super.onPause()
        findNavController().popBackStack()
    }


}