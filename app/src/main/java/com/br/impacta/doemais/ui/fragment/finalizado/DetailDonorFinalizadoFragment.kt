package com.br.impacta.doemais.ui.fragment.finalizado

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.findNavController
import com.br.impacta.doemais.R
import com.br.impacta.doemais.commom.BaseSingleton
import com.br.impacta.doemais.commom.ImageEnum
import com.br.impacta.doemais.data.finalizado.Finalizado
import com.br.impacta.doemais.databinding.FragmentDetailDonorFinalizadoBinding
import com.br.impacta.doemais.room.DAOFinalizado
import com.br.impacta.doemais.room.DAOPedido

class DetailDonorFinalizadoFragment : Fragment() {
    private var name: String? = null
    private var location: String? = null
    private var img: Int? = null
    private var donation: String? = null
    private var description: CharSequence? = null
    private var title: String? = null
    private var key: String? = null
    private var id: String? = null
    private var id2: String? = null
    private var timeline: String? = null
    private var perfil: String? = null
    private lateinit var binding: FragmentDetailDonorFinalizadoBinding

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
            id2 = it.getString("id2")
            timeline = it.getString("timeline")
            perfil = it.getString("perfil")
        }
    }

    private fun getImage(value: Int): Int {
        return when(value){
            ImageEnum.FOOD.value -> R.drawable.perfil
            ImageEnum.CLOTHER.value -> R.drawable.clother
            ImageEnum.VOLUNTARY.value -> R.drawable.voluntary
            ImageEnum.SUPPORT.value -> R.drawable.support
            else -> {R.drawable.ic_launcher_background}
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailDonorFinalizadoBinding.inflate(inflater, container, false)

        binding.tvLocation.text = location
        binding.tvName.text = name
        binding.imageView.setBackgroundResource(getImage(img!!))
        binding.tvDonation.text = donation
        binding.tvDescription.text = description
        binding.tvTitle.text = title

        binding.button3.setOnClickListener {
            addDB()
            val db = DAOPedido()
            db.remove(key)
            BaseSingleton.setBackButton(false)
            findNavController().popBackStack()
        }

        return binding.root
    }

    private fun addDB() {
        val db = DAOFinalizado()

        val finalizado = Finalizado(
            collectPoint = location,
            description = description.toString(),
            name = name,
            type = donation,
            id = id,
            id2 = id2,
            perfil = perfil,
            timeline = "3")

        db.add(finalizado)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        BaseSingleton.setBackButton(true)

        when(timeline?.toInt()){
            1 -> {
                binding.checkBox1.isChecked = true
                binding.checkBox1.isEnabled = true
                binding.checkBox1.isClickable = false
            }
            2 -> {
                binding.checkBox1.isChecked = true
                binding.checkBox1.isEnabled = true
                binding.checkBox1.isClickable = false
                binding.checkBox2.isChecked = true
                binding.checkBox2.isEnabled = true
                binding.checkBox2.isClickable = false
                binding.button3.isEnabled = true
                binding.button3.isClickable = true
            }
            3 -> {
                binding.button3.visibility = View.GONE
                binding.checkBox1.isChecked = true
                binding.checkBox1.isEnabled = true
                binding.checkBox1.isClickable = false
                binding.checkBox2.isChecked = true
                binding.checkBox2.isEnabled = true
                binding.checkBox2.isClickable = false
                binding.checkBox3.isChecked = true
                binding.checkBox3.isEnabled = true
                binding.checkBox3.isClickable = false
            }
        }

    }

    override fun onPause() {
        super.onPause()
    }


}