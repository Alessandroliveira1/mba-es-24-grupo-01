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

package com.br.impacta.doemais.ui.Pedido

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.br.impacta.doemais.data.doacao.Doacao
import com.br.impacta.doemais.databinding.LocationViewholderBinding

class PedidoAdapter(
    private val onClick: (Doacao, Boolean) -> Unit
) : PagingDataAdapter<Doacao, PedidoViewHolder>(ARTICLE_DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PedidoViewHolder =
        PedidoViewHolder(
            LocationViewholderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        )

    override fun onBindViewHolder(holder: PedidoViewHolder, position: Int) {
        val location = getItem(position)
        if (location != null) {
            holder.bind(location, position, onClick)
        }
    }

    companion object {
        private val ARTICLE_DIFF_CALLBACK = object : DiffUtil.ItemCallback<Doacao>() {
            override fun areItemsTheSame(oldItem: Doacao, newItem: Doacao): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Doacao, newItem: Doacao): Boolean =
                oldItem.id == newItem.id && oldItem.description == newItem.description
        }
    }
}
