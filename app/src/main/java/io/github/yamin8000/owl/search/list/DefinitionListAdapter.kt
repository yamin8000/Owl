/*
 *     Owl: an android app for Owlbot Dictionary API
 *     DefinitionListAdapter.kt Created by Yamin Siahmargooei at 2022/1/16
 *     This file is part of Owl.
 *     Copyright (C) 2022  Yamin Siahmargooei
 *
 *     Owl is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Owl is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Owl.  If not, see <https://www.gnu.org/licenses/>.
 */

package io.github.yamin8000.owl.search.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.yamin8000.owl.databinding.DefinitionItemBinding
import io.github.yamin8000.owl.model.Definition

class DefinitionListAdapter(private val imageClickListener: (String) -> Unit) :
    RecyclerView.Adapter<DefinitionListHolder>() {

    private val list = mutableListOf<Definition>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefinitionListHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DefinitionItemBinding.inflate(inflater, parent, false)
        return DefinitionListHolder(binding, imageClickListener)
    }

    override fun onBindViewHolder(holder: DefinitionListHolder, position: Int) {
        holder.initView(list[position])
    }

    override fun getItemCount() = list.size

    fun addItem(definitionItem: Definition) {
        list.add(definitionItem)
    }
}