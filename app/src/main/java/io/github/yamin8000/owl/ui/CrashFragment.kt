/*
 *     Owl: an android app for Owlbot Dictionary API
 *     CrashFragment.kt Created by Yamin Siahmargooei at 2022/1/16
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

package io.github.yamin8000.owl.ui

import android.os.Bundle
import android.view.View
import io.github.yamin8000.owl.databinding.FragmentCrashBinding
import io.github.yamin8000.owl.util.Constants.STACKTRACE

class CrashFragment : BaseFragment<FragmentCrashBinding>({ FragmentCrashBinding.inflate(it) }) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.emptyAdapterText.setOnClickListener { activity?.finish() }
        binding.crashImage.setOnClickListener { activity?.finish() }
        binding.root.setOnClickListener { activity?.finish() }

        arguments?.let {
            val stacktrace = it.getString(STACKTRACE) ?: ""
            if (stacktrace.isNotBlank()) sendStacktraceToServer(stacktrace)
        }
    }

    private fun sendStacktraceToServer(stacktrace: String) {

    }
}