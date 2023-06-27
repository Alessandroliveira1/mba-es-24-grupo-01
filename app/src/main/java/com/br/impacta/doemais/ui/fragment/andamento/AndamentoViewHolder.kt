/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.br.impacta.doemais.ui.fragment.andamento

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.br.impacta.doemais.commom.ImageEnum
import com.br.impacta.doemais.R
import com.br.impacta.doemais.commom.BaseSingleton.getIdAuth
import com.br.impacta.doemais.data.doacao.Doacao
import com.br.impacta.doemais.databinding.LocationViewholderBinding


class AndamentoViewHolder(
    private val binding: LocationViewholderBinding
) : RecyclerView.ViewHolder(binding.root) {

    private var lastSelectItem: Any? = null
    private var firstItem: Any? = null

    @SuppressLint("ClickableViewAccessibility")
    fun bind(location: Doacao, position: Int, onClick: (Doacao, Boolean) -> Unit) {
        binding.apply {
           // binding.imageView.setBackgroundResource(getImage(location.image))
            binding.tvName.text =  if (getIdAuth() == location.id) "Você" else location.name
            binding.tvLocation.text = location.collectPoint
            binding.tvDonation.text = location.type

            if (position == 0){
                firstItem = binding.root
                setSelectItem(binding.root, location, onClick, false)
            }

            binding.root.setOnClickListener {
                setSelectItem(it, location, onClick)
            } }
    }

    private fun setSelectItem(view: View, location: Doacao, onClick: (Doacao, Boolean) -> Unit, firstClick: Boolean = true) {

        onClick(location, firstClick)
    }

    private fun getImage(value: Int): Int {
       return when(value){
            ImageEnum.FOOD.value -> R.drawable.food
            ImageEnum.CLOTHER.value -> R.drawable.clother
            ImageEnum.VOLUNTARY.value -> R.drawable.voluntary
            ImageEnum.SUPPORT.value -> R.drawable.support
           else -> {R.drawable.ic_launcher_background}
       }
    }
}
