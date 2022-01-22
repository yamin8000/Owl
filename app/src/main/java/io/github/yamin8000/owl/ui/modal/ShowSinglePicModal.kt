/*
 *     Owl: an android app for Owlbot Dictionary API
 *     ShowSinglePicModal.kt Created by Yamin Siahmargooei at 2022/1/22
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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import io.github.yamin8000.owl.R
import io.github.yamin8000.owl.databinding.ShowPicModalBinding
import io.github.yamin8000.owl.util.Constants.IMAGE_URL
import io.github.yamin8000.owl.util.Utility
import io.github.yamin8000.owl.util.Utility.handleCrash

class ShowSinglePicModal() : BottomSheetDialogFragment() {

    private val binding: ShowPicModalBinding by lazy(LazyThreadSafetyMode.NONE) {
        ShowPicModalBinding.inflate(layoutInflater)
    }

    private var imageUri: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        bundle: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageUri = arguments?.getString(IMAGE_URL)

        try {
            imageUri?.let { loadImage(it) }
        } catch (exception: Exception) {
            handleCrash(exception)
        }
    }

    private fun loadImage(imageUri: String) {
        binding.modalImage.load(imageUri) {
            placeholder(Utility.getShimmer())
            error(R.drawable.ic_image_error)
            crossfade(true)
        }
    }
}