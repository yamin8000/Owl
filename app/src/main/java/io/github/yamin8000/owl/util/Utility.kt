/*
 *     Owl: an android app for Owlbot Dictionary API
 *     Utility.kt Created by Yamin Siahmargooei at 2022/1/16
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

package io.github.yamin8000.owl.util

import android.content.ClipData
import android.content.ClipboardManager
import android.graphics.drawable.Drawable
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import com.orhanobut.logger.Logger
import io.github.yamin8000.owl.R
import io.github.yamin8000.owl.util.Constants.STACKTRACE

object Utility {

    fun Fragment.toast(text: String, length: Int = Toast.LENGTH_SHORT) {
        this.context.let {
            Toast.makeText(it, text, length).show()
        }
    }

    /**
     * Handle soft crashes, that are suppressed using try-catch
     *
     * @param exception exception that caught
     */
    fun Fragment.handleCrash(exception: Exception) {
        val stackTraceToString = exception.stackTraceToString()
        //log it to logcat
        Logger.d(stackTraceToString)
        //navigate user to a special crash screen
        val bundle = bundleOf(STACKTRACE to stackTraceToString)
        this.findNavController().navigate(R.id.crashFragment, bundle)
    }

    /**
     * create a shimmer image
     *
     * @return shimmer drawable to load into imageview
     */
    fun getShimmer(): Drawable {
        val shimmer =
            Shimmer.AlphaHighlightBuilder() // The attributes for a ShimmerDrawable is set by this builder
                .setDuration(1800) // how long the shimmering animation takes to do one full sweep
                .setBaseAlpha(0.9f) //the alpha of the underlying children
                .setHighlightAlpha(0.8f) // the shimmer alpha amount
                .setDirection(Shimmer.Direction.LEFT_TO_RIGHT).setAutoStart(true).build()

        // This is the placeholder for the imageView
        return ShimmerDrawable().apply {
            setShimmer(shimmer)
        }
    }

    fun copyToClipBoard(
        text: String,
        clipboardManager: ClipboardManager
    ) {
        val clip = ClipData.newPlainText(text, text)
        clipboardManager.setPrimaryClip(clip)
    }

    /**
     * Hide keyboard inside fragment
     *
     * since this is not my code and looks shady
     *
     * I don't know about any errors that can happen
     *
     * so it's wrapped inside try/catch
     *
     */
    fun Fragment.hideKeyboard() {
        try {
            val activity = this.activity
            if (activity != null) {
                val imm =
                    activity.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
                var view = activity.currentFocus
                if (view == null) view = View(activity)
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
        } catch (exception: Exception) {
            handleCrash(exception)
        }
    }
}