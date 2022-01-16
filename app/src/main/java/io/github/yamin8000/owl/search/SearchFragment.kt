/*
 *     Owl: an android app for Owlbot Dictionary API
 *     SearchFragment.kt Created by Yamin Siahmargooei at 2022/1/16
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

package io.github.yamin8000.owl.search

import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.orhanobut.logger.Logger
import io.github.yamin8000.owl.R
import io.github.yamin8000.owl.databinding.FragmentSearchBinding
import io.github.yamin8000.owl.network.APIs
import io.github.yamin8000.owl.network.Web
import io.github.yamin8000.owl.network.Web.async
import io.github.yamin8000.owl.search.list.DefinitionListAdapter
import io.github.yamin8000.owl.ui.BaseFragment
import io.github.yamin8000.owl.util.Utility.copyToClipBoard
import io.github.yamin8000.owl.util.Utility.handleCrash
import io.github.yamin8000.owl.util.ViewUtility.handleViewDataNullity

class SearchFragment : BaseFragment<FragmentSearchBinding>({ FragmentSearchBinding.inflate(it) }) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {
            copyToClipboardLongClickListener()

            binding.searchInput.setStartIconOnClickListener {
                val input = binding.searchEdit.text.toString()

                Web.getAPI<APIs.WordAPI>().searchWord(input).async(this, {
                    if (it != null) {
                        binding.wordText.text = it.word
                        binding.pronunciationText.handleViewDataNullity(it.pronunciation)

                        val adapter = DefinitionListAdapter()
                        binding.recyclerView.adapter = adapter
                        it.definitions.forEachIndexed { index, definition ->
                            adapter.addItem(definition)
                            adapter.notifyItemInserted(index)
                        }
                    } else Logger.d("null")
                }, {
                    Logger.d(it.stackTraceToString())
                })
            }
        } catch (e: Exception) {
            handleCrash(e)
        }
    }

    private fun copyToClipboardLongClickListener() {
        binding.pronunciationText.setOnLongClickListener {
            context?.let {
                val clipboardManager = it.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                copyToClipBoard(binding.pronunciationText.text.toString(), clipboardManager)
                Toast.makeText(it, getString(R.string.text_copied), Toast.LENGTH_SHORT).show()
            }
            true
        }
    }
}