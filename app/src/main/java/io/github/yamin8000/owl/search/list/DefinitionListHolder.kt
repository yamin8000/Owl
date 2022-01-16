/*
 *     Owl: an android app for Owlbot Dictionary API
 *     DefinitionListHolder.kt Created by Yamin Siahmargooei at 2022/1/16
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

import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import coil.load
import io.github.yamin8000.owl.R
import io.github.yamin8000.owl.databinding.DefinitionItemBinding
import io.github.yamin8000.owl.model.Definition
import io.github.yamin8000.owl.util.Utility.copyToClipBoard
import io.github.yamin8000.owl.util.Utility.getShimmer
import io.github.yamin8000.owl.util.ViewUtility.gone
import io.github.yamin8000.owl.util.ViewUtility.handleViewDataNullity
import io.github.yamin8000.owl.util.ViewUtility.visible

class DefinitionListHolder(private val binding: DefinitionItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    init {
        createLongClickListeners()
    }

    private fun createLongClickListeners() {
        binding.root.context?.let { context ->
            val clipboardManager = context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            listOf(
                binding.emojiText,
                binding.typeText,
                binding.exampleText,
                binding.definitionText
            ).forEach { textView ->
                textView.setOnLongClickListener {
                    copyToClipBoard(textView.text.toString(), clipboardManager)
                    Toast.makeText(context,
                        context.getString(R.string.text_copied), Toast.LENGTH_SHORT).show()
                    true
                }
            }
        }
    }

    fun initView(definition: Definition) {
        binding.definitionText.text = definition.definition
        binding.emojiText.handleViewDataNullity(definition.emoji)
        binding.exampleText.handleViewDataNullity(definition.example)
        binding.typeText.handleViewDataNullity(definition.type)

        handleImage(definition.imageUrl)
    }

    private fun handleImage(imageUrl: String?) {
        if (imageUrl == null) binding.wordImage.gone()
        else loadImageView(imageUrl)
    }

    private fun loadImageView(imageUrl: String) {
        binding.wordImage.visible()
        binding.wordImage.load(imageUrl) {
            placeholder(getShimmer())
            error(R.drawable.ic_image_error)
            crossfade(true)
        }
    }
}