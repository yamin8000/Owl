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
import androidx.navigation.fragment.findNavController
import com.orhanobut.logger.Logger
import io.github.yamin8000.owl.R
import io.github.yamin8000.owl.databinding.FragmentSearchBinding
import io.github.yamin8000.owl.model.Word
import io.github.yamin8000.owl.network.APIs
import io.github.yamin8000.owl.network.Web
import io.github.yamin8000.owl.network.Web.asyncResponse
import io.github.yamin8000.owl.search.list.DefinitionListAdapter
import io.github.yamin8000.owl.ui.BaseFragment
import io.github.yamin8000.owl.util.Utility.copyToClipBoard
import io.github.yamin8000.owl.util.Utility.handleCrash
import io.github.yamin8000.owl.util.ViewUtility.gone
import io.github.yamin8000.owl.util.ViewUtility.handleViewDataNullity
import io.github.yamin8000.owl.util.ViewUtility.visible
import retrofit2.Response

class SearchFragment : BaseFragment<FragmentSearchBinding>({ FragmentSearchBinding.inflate(it) }) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {
            copyToClipboardLongClickListener()
            binding.searchInput.setStartIconOnClickListener { searchWord() }
            toolbarMenuHandler()
        } catch (e: Exception) {
            handleCrash(e)
        }
    }

    private fun toolbarMenuHandler() {
        binding.searchFragmentToolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.search_menu_about -> findNavController().navigate(R.id.action_searchFragment_to_aboutModal)
                R.id.search_menu_settings -> {
                    context?.let {
                        Toast.makeText(it, "به زودی", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
            true
        }
    }

    private fun searchWord() {
        val input = binding.searchEdit.text.toString()
        createSearchWordRequest(input)

        binding.searchProgress.visible()
    }

    private fun createSearchWordRequest(input: String) {
        Web.getAPI<APIs.WordAPI>().searchWord(input).asyncResponse(this, {
            handleReceivedResponse(it)
        }, { throwable -> handleException(throwable) })
    }

    private fun handleReceivedResponse(it: Response<Word>) {
        val body = it.body()
        if (body != null) handleOkResponseBody(body)
        else handleNullResponseBody(it.code())
    }

    private fun handleException(throwable: Throwable) {
        Logger.d(throwable.stackTraceToString())
        context?.let {
            Toast.makeText(
                it,
                getString(R.string.general_net_error),
                Toast.LENGTH_LONG
            ).show()
        }
        binding.searchProgress.gone()
    }

    private fun handleOkResponseBody(body: Word) {
        binding.basicInfoCard.visible()
        binding.wordText.handleViewDataNullity(body.word)
        binding.pronunciationText.handleViewDataNullity(body.pronunciation)

        val adapter = DefinitionListAdapter()
        binding.recyclerView.adapter = adapter
        body.definitions.forEachIndexed { index, definition ->
            adapter.addItem(definition)
            adapter.notifyItemInserted(index)
        }
        binding.searchProgress.gone()
    }

    private fun handleNullResponseBody(code: Int) {
        val message = when (code) {
            401 -> getString(R.string.api_authorization_error)
            404 -> getString(R.string.definition_not_found)
            429 -> getString(R.string.api_throttled)
            else -> getString(R.string.general_net_error)
        }
        context?.let { Toast.makeText(it, message, Toast.LENGTH_LONG).show() }
        binding.searchProgress.gone()
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