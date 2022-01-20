/*
 *     Owl: an android app for Owlbot Dictionary API
 *     AboutModal.kt Created by Yamin Siahmargooei at 2022/1/20
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

package io.github.yamin8000.owl.ui.modal

import android.os.Bundle
import android.text.util.Linkify
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.util.LinkifyCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import io.github.yamin8000.owl.databinding.AboutModalBinding

class AboutModal : BottomSheetDialogFragment() {

    private val binding: AboutModalBinding by lazy(LazyThreadSafetyMode.NONE) {
        AboutModalBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        enableLinksInTextview(binding.aboutAppText)
        enableLinksInTextview(binding.appLicenseText)
    }

    private fun enableLinksInTextview(textView: TextView) {
        LinkifyCompat.addLinks(textView, Linkify.ALL)
    }
}