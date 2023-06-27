package com.br.impacta.doemais.ui.fragment.orderform


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.br.impacta.doemais.data.doacao.Doacao
import com.br.impacta.doemais.databinding.FragmentOrderFormBinding
import com.br.impacta.doemais.room.DAODoacao
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass.
 * Use the [OrderFormFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class OrderFormFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentOrderFormBinding? = null
    private lateinit var database: DatabaseReference
    private var _latitude: String = ""
    private var _longitude: String = ""
   // private lateinit var db: AppDatabase

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentOrderFormBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel: OrderFormViewModel by viewModels()

        database = Firebase.database.reference
        binding.btnSend.setOnClickListener {
            Toast.makeText(requireActivity(), "Formulário enviado!!", Toast.LENGTH_SHORT).show()

            createDAO()

            insertData(view,viewModel)

            findNavController().popBackStack()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun insertData(view: View, viewModel: OrderFormViewModel) {

        val radioId = _binding?.radioGroup?.checkedRadioButtonId
        val radioBtn: RadioButton? = radioId?.let { view.findViewById(it) }

        when(radioBtn?.text.toString()){
            "Igreja Batista" -> {
                _latitude = (-23.6463982).toString()
                _longitude = (-46.7324468).toString()
            }
            "Escola P. Lima" -> {
                _latitude = (-23.6496569).toString()
                _longitude = (-46.7189312).toString()
            }
            "Abrigo ZS" -> {
                _latitude = (-23.6549419).toString()
                _longitude = (-46.7477647).toString()
            }
        }

        val doacaoModel = Doacao(
            radioBtn?.text.toString(),
            _binding?.editTextDescription?.text.toString(),
            Firebase.auth.currentUser?.displayName?: "",
            _binding?.editTextType?.text.toString(),
            id = Firebase.auth.uid ?: "",
            id2 = Firebase.auth.uid ?: "",
            perfil = "carecente",
            latitude = _latitude,
            longitude = _longitude)

        val db = DAODoacao()
        db.add(doacaoModel)

//        viewModel.db.child("users")
//            .child(Firebase.auth.uid ?: "")
//            .child(database.push().key.toString())
//            .setValue(pedidoModel)
    }

    private fun createDAO() {
        //db = AppDatabase.getDatabase(requireActivity())
    }

}